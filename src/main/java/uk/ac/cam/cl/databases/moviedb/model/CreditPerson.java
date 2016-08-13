package uk.ac.cam.cl.databases.moviedb.model;

import uk.ac.cam.cl.databases.moviedb.MovieDB;

/**
 * A CreditPerson appears in various fields of a {@link Movie} object, and
 * establishes links to all the people who worked on that movie, as part of the
 * cast (actors and actresses) or crew (directors, producers, composers, etc).
 *
 * <p>For actors and actresses on a movie, {@link CreditActor} (a subclass of
 * {@link CreditPerson}) is used instead. For writers of a movie,
 * {@link CreditWriter} (another subclass) is used. For all other people who
 * worked on the movie, this class is used.
 *
 * <p>This object does not have the full information about the person in question
 * -- it only has the ID ({@link #getPersonId()}) and the name ({@link #getName()})
 * of the person. If you need to find more properties of the person, you can use
 * {@link MovieDB#getPersonById(int)} to fetch the full {@link Person} object.
 *
 * <p>When you look at a {@link Person} object, the movies that person has worked on
 * are given as lists of {@link BiographyItem} objects. So {@link BiographyItem} is
 * like {@link CreditPerson}, but in the opposite direction (from a person to a movie).
 */
public class CreditPerson {
    private int personId;
    private String name, info;

    /**
     * Gets the unique numerical ID of the person who worked on this movie.
     * See {@link Person#getId()} for more information about IDs.
     */
    public int getPersonId() {
        return personId;
    }

    /**
     * Gets the name of the person who worked on this movie.
     * See {@link Person#getName()} for more information about names.
     */
    public String getName() {
        return name;
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
     * Generates a JSON string representing this CreditPerson.
     */
    @Override
    public String toString() {
        return Movie.JSON_CODEC.toJson(this);
    }
}
