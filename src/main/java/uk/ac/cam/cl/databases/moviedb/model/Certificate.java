package uk.ac.cam.cl.databases.moviedb.model;

public class Certificate {
    private String country, certificate, note;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
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
