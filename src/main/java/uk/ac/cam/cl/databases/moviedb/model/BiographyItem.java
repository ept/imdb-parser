package uk.ac.cam.cl.databases.moviedb.model;

import uk.ac.cam.cl.databases.moviedb.MovieDB;

/**
 * A BiographyItem appears in various fields of a {@link Person} object, and
 * establishes a link to a movie on which that person worked. Thus, the movie
 * is one item that this person would list on their biography (or filmography).
 *
 * <p>If the person was an actor or actress on a movie, {@link Role} (a subclass
 * of {@link BiographyItem}) is used instead. If the person was a writer,
 * {@link ScriptWriter} (another subclass) is used. For all other ways that a
 * person can be credited on a movie, this class is used.
 *
 * <p>This object does not have the full information about the movie on which
 * the person worked -- it only has the ID ({@link #getMovieId()}) and the
 * title ({@link #getTitle()}). If you need to find more properties of the movie,
 * you can use {@link MovieDB#getMovieById(int)} to fetch the full {@link Movie}
 * object.
 *
 * <p>When you look at a {@link Movie} object, the people who worked on that movie
 * are given as lists of {@link CreditPerson} objects. So {@link CreditPerson} is
 * like {@link BiographyItem}, but in the opposite direction (from a movie to a
 * person).
 */
public class BiographyItem {
    private int movieId;
    private String title, info;

    /**
     * Gets the unique numerical ID of the movie on which the person worked.
     * See {@link Movie#getId()} for more information about IDs.
     */
    public int getMovieId() {
        return movieId;
    }

    /**
     * Sets the unique numerical ID of the movie on which the person worked.
     * See {@link Movie#getId()} for more information about IDs.
     */
    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    /**
     * Gets the title of the movie on which the person worked. See
     * {@link Movie#getTitle()} for more information about titles.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the movie on which the person worked. See
     * {@link Movie#getTitle()} for more information about titles.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets an <a href="https://contribute.imdb.com/updates/guide/attributes">informational
     * attribute</a> about the job that this person did on this movie, or about how they were
     * credited. This is usually a string in parantheses, for example <tt>"(project manager)"</tt>.
     */
    public String getInfo() {
        return info;
    }

    /**
     * Sets the <a href="https://contribute.imdb.com/updates/guide/attributes">informational
     * attribute</a> about the job that this person did on this movie, or about how they were
     * credited.
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * Generates a JSON string representing this BiographyItem.
     */
    @Override
    public String toString() {
        return Movie.JSON_CODEC.toJson(this);
    }
}
