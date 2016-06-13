package uk.ac.cam.cl.databases.moviedb.model;

/**
 * A Language object appears on a {@link Movie}, and describes a
 * <a href="https://contribute.imdb.com/updates/guide/language">language that
 * is spoken during the movie</a>.
 */
public class Language {
    private String language, note;

    /**
     * Gets the name of the language, for example <tt>"English"</tt>.
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the name of the language, for example <tt>"English"</tt>.
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Gets an informational attribute, for example stating in which context
     * the language is used.
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets an informational attribute, for example stating in which context
     * the language is used.
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Generates a JSON string representing this Language object.
     */
    @Override
    public String toString() {
        return Movie.JSON_CODEC.toJson(this);
    }
}
