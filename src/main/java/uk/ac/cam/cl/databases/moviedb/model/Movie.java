package uk.ac.cam.cl.databases.moviedb.model;

import java.util.List;
import java.util.Map;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import uk.ac.cam.cl.databases.moviedb.MovieDB;

/**
 * A Movie object represents everything we know about a movie in our database.
 * You can look up a movie by ID using {@link MovieDB#getMovieById(int)}, and search
 * for movies by title using {@link MovieDB#getByTitlePrefix(String)}.
 */
public class Movie {
    private int id;
    private String title;
    private int year;
    private List<CreditActor> actors;
    private List<CreditPerson> cinematographers;
    private List<CreditPerson> composers;
    private List<CreditPerson> costumeDesigners;
    private List<CreditPerson> directors;
    private List<CreditPerson> editors;
    private List<CreditPerson> producers;
    private List<CreditPerson> productionDesigners;
    private List<CreditWriter> writers;
    private List<Certificate> certificates;
    private List<ColorInfo> colorInfo;
    private List<String> genres;
    private List<String> keywords;
    private List<Language> languages;
    private List<Location> locations;
    private Map<String, List<ReleaseDate>> releaseDates;
    private List<RunningTime> runningTimes;

    static final Gson JSON_CODEC = new GsonBuilder()
            .setPrettyPrinting()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    /**
     * Parses a JSON string into a {@link Movie} object.
     * @param json JSON data representing a movie.
     */
    public static Movie fromJson(String json) {
        return JSON_CODEC.fromJson(json, Movie.class);
    }

    /**
     * Generates a JSON string representing this movie.
     */
    @Override
    public String toString() {
        return JSON_CODEC.toJson(this);
    }

    /**
     * Gets the unique numerical ID of the movie. This ID is only meaningful within
     * this example database; it does not correspond to the ID on the IMDb website.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the <a href="https://contribute.imdb.com/updates/guide/title_formats">title
     * of the movie</a>, which usually ends with a year in parentheses
     * (to distinguish movies of the same title created in different years).
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the year in which this movie was
     * <a href="https://contribute.imdb.com/updates/guide/title_formats">first publicly screened</a>.
     * Note that the release dates in different countries may vary -- see
     * {@link #getReleaseDates()}.
     */
    public int getYear() {
        return year;
    }

    /**
     * Gets the list of <a href="https://contribute.imdb.com/updates/guide/cast">actors who
     * appear in this movie</a>, in the order of position in the credits.
     */
    public List<CreditActor> getActors() {
        return actors;
    }

    /**
     * Gets the list of <a href="https://contribute.imdb.com/updates/guide/cinematographers">people
     * who are credited as cinematographer or director of photography</a> on this movie.
     */
    public List<CreditPerson> getCinematographers() {
        return cinematographers;
    }

    /**
     * Gets the list of <a href="https://contribute.imdb.com/updates/guide/composers">people who
     * are credited as composer of the main background score</a> of this movie (often just one
     * person).
     */
    public List<CreditPerson> getComposers() {
        return composers;
    }

    /**
     * Gets the list of <a href="https://contribute.imdb.com/updates/guide/costume_designers">people
     * who are credited as costume designers</a> on this movie.
     */
    public List<CreditPerson> getCostumeDesigners() {
        return costumeDesigners;
    }

    /**
     * Gets the list of <a href="https://contribute.imdb.com/updates/guide/directors">people who
     * directed this movie</a>.
     */
    public List<CreditPerson> getDirectors() {
        return directors;
    }

    /**
     * Gets the list of <a href="https://contribute.imdb.com/updates/guide/editors">people who
     * are credited as picture editors</a> of this movie.
     */
    public List<CreditPerson> getEditors() {
        return editors;
    }

    /**
     * Gets the list of <a href="https://contribute.imdb.com/updates/guide/producers">people
     * who are credited as producer</a> of this movie. This includes executive producer, line
     * producer, etc.
     */
    public List<CreditPerson> getProducers() {
        return producers;
    }

    /**
     * Gets the list of <a href="https://contribute.imdb.com/updates/guide/production_designers">people
     * who are credited as production designer</a> on this movie.
     */
    public List<CreditPerson> getProductionDesigners() {
        return productionDesigners;
    }

    /**
     * Gets the list of <a href="https://contribute.imdb.com/updates/guide/writers">screenplay or
     * story writers</a> for this movie, in the order they appear in the credits.
     */
    public List<CreditWriter> getWriters() {
        return writers;
    }

    /**
     * Gets the list of <a href="https://contribute.imdb.com/updates/guide/certificates">certificates</a>
     * for this movie (such as age ratings) in different countries.
     */
    public List<Certificate> getCertificates() {
        return certificates;
    }

    /**
     * Gets information on <a href="https://contribute.imdb.com/updates/guide/color_info">whether
     * this movie was shot in colour or in black and white</a>. This list may have multiple
     * entries if it is a combination of both.
     */
    public List<ColorInfo> getColorInfo() {
        return colorInfo;
    }

    /**
     * Gets the list of <a href="https://contribute.imdb.com/updates/guide/genres">genres that
     * characterise this movie</a>.
     */
    public List<String> getGenres() {
        return genres;
    }

    /**
     * Gets the list of <a href="https://contribute.imdb.com/updates/guide/keywords">keywords
     * that describe any notable object, concept, style or action that takes place during this
     * movie</a>.
     */
    public List<String> getKeywords() {
        return keywords;
    }

    /**
     * Gets the list of <a href="https://contribute.imdb.com/updates/guide/language">languages
     * spoken in this movie</a>.
     */
    public List<Language> getLanguages() {
        return languages;
    }

    /**
     * Gets the list of <a href="https://contribute.imdb.com/updates/guide/locations">places
     * in which this movie was filmed</a>.
     */
    public List<Location> getLocations() {
        return locations;
    }

    /**
     * Gets the <a href="https://contribute.imdb.com/updates/guide/release_dates">release dates</a>
     * for this movie. The information is structured as a map, where the key is a country name
     * (for example, <tt>"USA"</tt>) and the value is a list of dates at which the movie was
     * released in that country. There may be multiple dates for the same country due to a premiere
     * or film festival being listed separately from the main release date, or differences between
     * regions.
     */
    public Map<String, List<ReleaseDate>> getReleaseDates() {
        return releaseDates;
    }

    /**
     * Gets the <a href="https://contribute.imdb.com/updates/guide/running_times">running time</a>
     * of this movie. The list usually has only one entry, but there may be several entries if
     * there are different versions of the movie that have different running times.
     */
    public List<RunningTime> getRunningTimes() {
        return runningTimes;
    }
}
