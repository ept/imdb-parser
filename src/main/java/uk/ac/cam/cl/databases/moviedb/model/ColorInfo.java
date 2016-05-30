package uk.ac.cam.cl.databases.moviedb.model;

public class ColorInfo {
    private String colorInfo, note;

    public String getColorInfo() {
        return colorInfo;
    }

    public void setColorInfo(String colorInfo) {
        this.colorInfo = colorInfo;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return Movie.JSON_CODEC.toJson(this);
    }
}
