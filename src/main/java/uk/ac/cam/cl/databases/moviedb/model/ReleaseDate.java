package uk.ac.cam.cl.databases.moviedb.model;

/**
 * A ReleaseDate appears on a {@link Movie} object, and describes the
 * <a href="https://contribute.imdb.com/updates/guide/release_dates">release date</a> of the
 * movie in a particular country.
 * for this movie. The information is structured as a map, where the key is a country name
 * (for example, <tt>"USA"</tt>) and the value is a list of dates at which the movie was
 * released in that country. 
 */
public class ReleaseDate {
    private String releaseDate, note;

    /**
     * Gets the date on which the movie released. If the exact date is known, it is given in the
     * form <tt>"YYYY-MM-DD"</tt>. If only month and year are known, it is given in the form
     * <tt>"YYYY-MM"</tt>. If only year is known, it is given in the form <tt>"YYYY"</tt>.
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * Gets an informational attribute about the movie release. For example, there may be
     * multiple release dates for the same country due to a premiere or film festival,
     * or differences between regions. This note field is used to distinguish those dates.
     */
    public String getNote() {
        return note;
    }

    /**
     * Generates a JSON string representing this ReleaseDate object.
     */
    @Override
    public String toString() {
        return Movie.JSON_CODEC.toJson(this);
    }
}
