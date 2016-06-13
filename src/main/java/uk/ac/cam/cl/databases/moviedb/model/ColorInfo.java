package uk.ac.cam.cl.databases.moviedb.model;

/**
 * <a href="https://contribute.imdb.com/updates/guide/color_info">Colour information</a>
 * about a {@link Movie} -- in particular, whether it is in colour or in black and white.
 */
public class ColorInfo {
    private String colorInfo, note;

    /**
     * Either <tt>"Color"</tt> or <tt>"Black and White"</tt>.
     */
    public String getColorInfo() {
        return colorInfo;
    }

    /**
     * Either <tt>"Color"</tt> or <tt>"Black and White"</tt>.
     */
    public void setColorInfo(String colorInfo) {
        this.colorInfo = colorInfo;
    }

    /**
     * Gets an informational attribute about the colour information, for example
     * a note about the process used, such as Technicolor.
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets an informational attribute about the colour information, for example
     * a note about the process used, such as Technicolor.
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Generates a JSON string representing this ColorInfo.
     */
    @Override
    public String toString() {
        return Movie.JSON_CODEC.toJson(this);
    }
}
