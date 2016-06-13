package uk.ac.cam.cl.databases.moviedb.model;

/**
 * GeneralInfo appears in fields of a {@link Movie}, and is used to store
 * various information that consists of an information field and a note.
 */
public class GeneralInfo {
    private String info, note;

    /**
     * Gets the main value of this field.
     */
    public String getInfo() {
        return info;
    }

    /**
     * Sets the main value of this field.
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * Gets an informational attribute about this field.
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets an informational attribute about this field.
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Generates a JSON string representing this GeneralInfo object.
     */
    @Override
    public String toString() {
        return Movie.JSON_CODEC.toJson(this);
    }
}
