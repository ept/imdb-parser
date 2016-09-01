package uk.ac.cam.cl.databases.moviedb;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;
import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.je.CheckpointConfig;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.EnvironmentMutableConfig;
import com.sleepycat.je.OperationStatus;
import com.sleepycat.je.SecondaryConfig;
import com.sleepycat.je.SecondaryCursor;
import com.sleepycat.je.SecondaryDatabase;
import com.sleepycat.je.SecondaryKeyCreator;
import uk.ac.cam.cl.databases.moviedb.model.Movie;
import uk.ac.cam.cl.databases.moviedb.model.Person;

/**
 * An example database using a JSON document data model, with data derived from
 * <a href="http://www.imdb.com/interfaces">IMDb</a>. It stores two kinds of object:
 * {@link Movie} and {@link Person}.
 *
 * <p>The database is embedded in-process (not a network service), and implemented using
 * <a href="http://www.oracle.com/technetwork/database/berkeleydb/overview/index-093405.html">Berkeley
 * DB Java Edition</a>. The records in the database are <a href="http://json.org/">JSON</a>
 * strings, which are mapped to Java objects using <a href="https://github.com/google/gson">GSON</a>.
 *
 * <p>The database has two primary key indexes (looking up a person or a movie by ID), and
 * two secondary indexes (looking up a person by name, or looking up a movie by title).
 *
 * <p>For convenience, MovieDB allows its data files to be packaged as resources in a JAR
 * file on the classpath, and they are extracted to a directory when it is first run.
 * This allows us to distribute a single JAR file containing both library dependencies and
 * example data.
 *
 * <p>When you have finished using the database, you should close it using {@link #close()},
 * which clears up any locks and pending writes, and shuts down background threads. The
 * easiest way of doing this is with a
 * <a href="http://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html">try-with-resources
 * statement</a>, which takes advantage of the {@link AutoCloseable} interface:
 *
 * <pre>try (MovieDB database = new MovieDB(new File("document-db")) {
 *    ... // use the database
 *}</pre>
 */
public class MovieDB implements AutoCloseable {
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static final EntryBinding<Integer> intBinding = TupleBinding.getPrimitiveBinding(Integer.class);
    private Environment dbEnv;
    private Database moviesDB, peopleDB;
    private SecondaryDatabase titleIndex, nameIndex;

    /**
     * Opens the database, stored in the specified directory.
     *
     * @param dataDir The directory in which to store the data files.
     */
    public MovieDB(File dataDir) {
        EnvironmentConfig envConfig = new EnvironmentConfig();
        envConfig.setAllowCreate(true);
        envConfig.setTransactional(false);
        if (dbEnv != null) throw new IllegalStateException("Database is already open");
        dbEnv = new Environment(dataDir, envConfig);

        DatabaseConfig dbConfig = new DatabaseConfig();
        dbConfig.setAllowCreate(true);
        dbConfig.setDeferredWrite(true);
        moviesDB = dbEnv.openDatabase(null, "movies", dbConfig);
        peopleDB = dbEnv.openDatabase(null, "people", dbConfig);

        SecondaryConfig titleIndexConfig = new SecondaryConfig();
        titleIndexConfig.setAllowCreate(true);
        titleIndexConfig.setAllowPopulate(true);
        titleIndexConfig.setDeferredWrite(true);
        titleIndexConfig.setKeyCreator(new TitleExtractor());
        titleIndexConfig.setSortedDuplicates(true);
        titleIndex = dbEnv.openSecondaryDatabase(null, "movies-by-title", moviesDB, titleIndexConfig);

        SecondaryConfig nameIndexConfig = new SecondaryConfig();
        nameIndexConfig.setAllowCreate(true);
        nameIndexConfig.setAllowPopulate(true);
        nameIndexConfig.setDeferredWrite(true);
        nameIndexConfig.setKeyCreator(new NameExtractor());
        nameIndexConfig.setSortedDuplicates(true);
        nameIndex = dbEnv.openSecondaryDatabase(null, "people-by-name", peopleDB, nameIndexConfig);
    }

    /**
     * Inserts a movie into the database, given as a JSON string.
     * @param json The JSON document representing the movie.
     */
    public void putMovie(String json) {
        if (moviesDB == null) throw new IllegalStateException("Database is not open");
        Movie movie = Movie.fromJson(json);
        DatabaseEntry key = new DatabaseEntry();
        intBinding.objectToEntry(movie.getId(), key);
        DatabaseEntry value = new DatabaseEntry(json.getBytes(UTF8));
        moviesDB.put(null, key, value);
    }

    /**
     * Inserts a person into the database, given as a JSON string.
     * @param json The JSON document representing the person.
     */
    public void putPerson(String json) {
        if (peopleDB == null) throw new IllegalStateException("Database is not open");
        Person person = Person.fromJson(json);
        DatabaseEntry key = new DatabaseEntry();
        intBinding.objectToEntry(person.getId(), key);
        DatabaseEntry value = new DatabaseEntry(json.getBytes(UTF8));
        peopleDB.put(null, key, value);
    }

    /**
     * Looks up a movie by its unique identifier.
     * @param id The identifier of the movie to search for.
     * @return The requested movie, or null if there is none with the given id.
     */
    public Movie getMovieById(int id) {
        DatabaseEntry key = new DatabaseEntry(), value = new DatabaseEntry();
        intBinding.objectToEntry(id, key);
        if (moviesDB.get(null, key, value, null) == OperationStatus.SUCCESS) {
            return Movie.fromJson(new String(value.getData(), UTF8));
        } else {
            return null;
        }
    }

    /**
     * Looks up a person by their unique identifier.
     * @param id The identifier of the person to search for.
     * @return The requested person, or null if there is none with the given id.
     */
    public Person getPersonById(int id) {
        DatabaseEntry key = new DatabaseEntry(), value = new DatabaseEntry();
        intBinding.objectToEntry(id, key);
        if (peopleDB.get(null, key, value, null) == OperationStatus.SUCCESS) {
            return Person.fromJson(new String(value.getData(), UTF8));
        } else {
            return null;
        }
    }

    /**
     * Scans over a set of movies whose title begins with the specified string.
     * See {@link Movie#getTitle()} for notes on how the title is formatted. The
     * search is case-sensitive.
     *
     * <p>You need to iterate all the way to the end, because the iterator maintains
     * a cursor in the database that is closed when the iterator has no more items
     * remaining. Stopping iteration early would leak cursors.
     *
     * @param titlePrefix The beginning of the title to search for.
     * @return Sequence of movies matching the search condition.
     */
    public Iterable<Movie> getByTitlePrefix(String titlePrefix) {
        return new Iterable<Movie>() {
            public Iterator<Movie> iterator() {
                return new SearchByPrefix<Movie>(titleIndex, titlePrefix, Movie::fromJson);
            }
        };
    }

    /**
     * Scans over a set of people whose name begins with the specified string.
     * See {@link Person#getName()} for notes on how the name is formatted. The
     * search is case-sensitive.
     *
     * <p>You need to iterate all the way to the end, because the iterator maintains
     * a cursor in the database that is closed when the iterator has no more items
     * remaining. Stopping iteration early would leak cursors.
     *
     * @param namePrefix The beginning of the name to search for.
     * @return Sequence of people matching the search condition.
     */
    public Iterable<Person> getByNamePrefix(String namePrefix) {
        return new Iterable<Person>() {
            public Iterator<Person> iterator() {
                return new SearchByPrefix<Person>(nameIndex, namePrefix, Person::fromJson);
            }
        };
    }

    /**
     * Closes the database cleanly and shuts down its background threads.
     * This method is called automatically if you use MovieDB in a
     * <a href="http://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html">try-with-resources
     * statement</a>.
     */
    @Override
    public void close() {
        if (dbEnv == null) return;

        // Shutdown procedure as recommended in the BDB JE docs.
        // http://docs.oracle.com/cd/E17277_02/html/java/com/sleepycat/je/Environment.html#close()
        // Stop the cleaner daemon threads.
        EnvironmentMutableConfig config = dbEnv.getMutableConfig();
        config.setConfigParam(EnvironmentConfig.ENV_RUN_CLEANER, "false");
        dbEnv.setMutableConfig(config);
        dbEnv.cleanLog();

        // Perform an extra checkpoint
        dbEnv.checkpoint(new CheckpointConfig().setForce(true));

        if (titleIndex != null) titleIndex.close();
        titleIndex = null;

        if (nameIndex != null) nameIndex.close();
        nameIndex = null;

        if (moviesDB != null) moviesDB.close();
        moviesDB = null;

        if (peopleDB != null) peopleDB.close();
        peopleDB = null;

        dbEnv.close();
        dbEnv = null;
    }

    /**
     * Extracts the title of a movie from a database record, so that we can
     * create a secondary index for it.
     */
    private static class TitleExtractor implements SecondaryKeyCreator {
        @Override
        public boolean createSecondaryKey(SecondaryDatabase secondary, DatabaseEntry key,
                                          DatabaseEntry value, DatabaseEntry result) {
            Movie movie = Movie.fromJson(new String(value.getData(), UTF8));
            result.setData(movie.getTitle().getBytes(UTF8));
            return true;
        }
    }

    /**
     * Extracts a person's name from a database record, so that we can
     * create a secondary index for it.
     */
    private static class NameExtractor implements SecondaryKeyCreator {
        @Override
        public boolean createSecondaryKey(SecondaryDatabase secondary, DatabaseEntry key,
                                          DatabaseEntry value, DatabaseEntry result) {
            Person person = Person.fromJson(new String(value.getData(), UTF8));
            result.setData(person.getName().getBytes(UTF8));
            return true;
        }
    }

    /**
     * Scans over a secondary index for keys starting with some prefix.
     */
    private static class SearchByPrefix<T> implements Iterator<T>, AutoCloseable {
        private final byte[] prefix;
        private final Function<String, T> parseJson;
        private SecondaryCursor cursor;
        private DatabaseEntry key = new DatabaseEntry(), result = new DatabaseEntry();
        private OperationStatus status;
        private T next;

        private SearchByPrefix(SecondaryDatabase index, String titlePrefix, Function<String, T> parseJson) {
            prefix = titlePrefix.getBytes(UTF8);
            key.setData(Arrays.copyOf(prefix, prefix.length));
            this.parseJson = parseJson;
            cursor = index.openCursor(null, null);
            status = cursor.getSearchKeyRange(key, result, null);
            loadRecord();
        }

        private void loadRecord() {
            if (cursor == null || status != OperationStatus.SUCCESS ||
                    !Arrays.equals(prefix, Arrays.copyOf(key.getData(), prefix.length))) {
                close();
            } else {
                next = parseJson.apply(new String(result.getData(), UTF8));
            }
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException("Iterator has finished");
            T toReturn = next;
            status = cursor.getNext(key, result, null);
            loadRecord();
            return toReturn;
        }

        @Override
        public void close() {
            if (cursor != null) cursor.close();
            cursor = null;
            next = null;
        }
    }
}
