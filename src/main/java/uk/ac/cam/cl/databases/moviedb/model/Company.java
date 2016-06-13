package uk.ac.cam.cl.databases.moviedb.model;

/**
 * A company appears on a {@link Movie} credits when it is listed as
 * production company ({@link Movie#getProdCompanies()}),
 * distributor ({@link Movie#getDistributors()}) or
 * special effects company ({@link Movie#getSfxCompanies()}).
 */
public class Company {
    private String company, note;

    /**
     * Gets the name of the company, followed by the country code in square brackets.
     */
    public String getCompany() {
        return company;
    }

    /**
     * Sets the name of the company, followed by the country code in square brackets.
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * Gets an informational attribute about the company's relationship to the movie.
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets an informational attribute about the company's relationship to the movie.
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Generates a JSON string representing this Company.
     */
    @Override
    public String toString() {
        return Movie.JSON_CODEC.toJson(this);
    }
}
