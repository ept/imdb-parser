package uk.ac.cam.cl.databases.moviedb.model;

/**
 * A certificate (such as age rating) that was assigned to a {@link Movie}.
 * The certificate codes vary by country, and they are
 * <a href="https://contribute.imdb.com/updates/guide/certificates">documented
 * on the IMDb website</a>.
 */
public class Certificate {
    private String country, certificate, note;

    /**
     * Gets the country in which the certificate applies.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Gets the country-specific code for the certificate, for example
     * <tt>"PG"</tt> for the "Parental Guidance" category in the UK.
     */
    public String getCertificate() {
        return certificate;
    }

    /**
     * Gets an informational attribute about the certificate, for example
     * to describe which version of a movie the certificate applies to.
     */
    public String getNote() {
        return note;
    }

    /**
     * Generates a JSON string representing this Certificate.
     */
    @Override
    public String toString() {
        return Movie.JSON_CODEC.toJson(this);
    }
}
