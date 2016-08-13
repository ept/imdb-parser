package uk.ac.cam.cl.databases.moviedb.model;

/**
 * A Location object appears on a {@link Movie}, and describes a
 * <a href="https://contribute.imdb.com/updates/guide/locations">location
 * in which this movie was filmed</a>.
 */
public class Location {
    private String location, note;

    /**
     * Gets the name and place (such as city, region, country) of the location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Gets an informational attribute, for example stating which parts of the
     * movie are shot in this location.
     */
    public String getNote() {
        return note;
    }

    /**
     * Generates a JSON string representing this Location object.
     */
    @Override
    public String toString() {
        return Movie.JSON_CODEC.toJson(this);
    }
}
