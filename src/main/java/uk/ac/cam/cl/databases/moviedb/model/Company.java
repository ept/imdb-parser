package uk.ac.cam.cl.databases.moviedb.model;

public class Company {
    private String company, note;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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
