package uk.ac.cam.cl.databases.moviedb.model;

public class GeneralInfo {
    private String info, note;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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
