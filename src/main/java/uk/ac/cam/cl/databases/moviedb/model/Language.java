package uk.ac.cam.cl.databases.moviedb.model;

public class Language {
    private String language, note;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
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
