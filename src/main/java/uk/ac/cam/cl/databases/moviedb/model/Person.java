package uk.ac.cam.cl.databases.moviedb.model;

import java.util.List;
import uk.ac.cam.cl.databases.moviedb.MovieDB;

/**
 * A Person object represents everything we know about a person in our database.
 * You can look up a person by ID using {@link MovieDB#getPersonById(int)}, and search
 * for people by name using {@link MovieDB#getByNamePrefix(String)}.
 */
public class Person {
    private int id;
    private String name;
    private String gender;
    private List<Role> actorIn;
    private List<BiographyItem> cinematographerIn;
    private List<BiographyItem> composerIn;
    private List<BiographyItem> costumeDesignerIn;
    private List<BiographyItem> directorIn;
    private List<BiographyItem> editorIn;
    private List<BiographyItem> miscellaneousIn;
    private List<BiographyItem> producerIn;
    private List<BiographyItem> productionDesignerIn;
    private List<ScriptWriter> writerIn;

    /**
     * Parses a JSON string into a {@link Person} object.
     * @param json JSON data representing a person.
     */
    public static Person fromJson(String json) {
        return Movie.JSON_CODEC.fromJson(json, Person.class);
    }

    /**
     * Generates a JSON string representing this person.
     */
    @Override
    public String toString() {
        return Movie.JSON_CODEC.toJson(this);
    }

    /**
     * Gets the unique numerical ID of this person. This ID is only meaningful within
     * this example database; it does not correspond to the ID on the IMDb website.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique numerical ID of this person. This ID is only meaningful within
     * this example database; it does not correspond to the ID on the IMDb website.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the <a href="https://contribute.imdb.com/updates/guide/names_all">name of this
     * person, usually in the form "surname, firstname"</a>. If there are several people
     * with the same name in the database, the name is followed by a roman numeral in
     * parentheses to resolve the ambiguity (the number is arbitrarily assigned). Names
     * in languages that use non-latin characters are romanised into the latin alphabet.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the <a href="https://contribute.imdb.com/updates/guide/names_all">name of this
     * person, usually in the form "surname, firstname"</a>. See {@link #getName()} for
     * details.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the <a href="https://contribute.imdb.com/updates/guide/cast#gender">gender of
     * this person</a>, either <tt>"male"</tt> or <tt>"female"</tt>. Only set for actors
     * and actresses, which are separated into two separate lists in IMDb; for other people
     * (e.g. directors, producers) the gender is <tt>null</tt>. For transgender or
     * non-binary gender people, either <tt>"male"</tt> or <tt>"female"</tt> has been chosen
     * by whoever entered the data into IMDb.
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the <a href="https://contribute.imdb.com/updates/guide/cast#gender">gender of
     * this person</a>.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * If this person is an actor or actress, gets the list of movies
     * <a href="https://contribute.imdb.com/updates/guide/cast">in which they played a
     * role</a>, sorted by year. <tt>null</tt> if this person is not an actor.
     */
    public List<Role> getActorIn() {
        return actorIn;
    }

    /**
     * Sets the list of movies <a href="https://contribute.imdb.com/updates/guide/cast">in
     * which this person played a role</a>.
     */
    public void setActorIn(List<Role> actorIn) {
        this.actorIn = actorIn;
    }

    /**
     * If this person is a <a href="https://contribute.imdb.com/updates/guide/cinematographers">cinematographer
     * or director of photography</a>, gets a list of movies that they worked on, sorted by year.
     * Otherwise returns <tt>null</tt>.
     */
    public List<BiographyItem> getCinematographerIn() {
        return cinematographerIn;
    }

    /**
     * Sets the list of movies in which this person worked as
     * <a href="https://contribute.imdb.com/updates/guide/cinematographers">cinematographer
     * or director of photography</a>.
     */
    public void setCinematographerIn(List<BiographyItem> cinematographerIn) {
        this.cinematographerIn = cinematographerIn;
    }

    /**
     * If this person is a composer, gets the
     * <a href="https://contribute.imdb.com/updates/guide/composers">list of movies on which
     * they composed the main background score</a>, sorted by year. Otherwise returns <tt>null</tt>.
     */
    public List<BiographyItem> getComposerIn() {
        return composerIn;
    }

    /**
     * Sets the list of movies for which this person
     * <a href="https://contribute.imdb.com/updates/guide/composers">composed the main
     * background score</a>.
     */
    public void setComposerIn(List<BiographyItem> composerIn) {
        this.composerIn = composerIn;
    }

    /**
     * If this person is a <a href="https://contribute.imdb.com/updates/guide/costume_designers">costume
     * designer</a>, gets a list of movies that they worked on, sorted by year.
     * Otherwise returns <tt>null</tt>.
     */
    public List<BiographyItem> getCostumeDesignerIn() {
        return costumeDesignerIn;
    }

    /**
     * Sets the list of movies in which this person worked as
     * <a href="https://contribute.imdb.com/updates/guide/costume_designers">costume designer</a>.
     */
    public void setCostumeDesignerIn(List<BiographyItem> costumeDesignerIn) {
        this.costumeDesignerIn = costumeDesignerIn;
    }

    /**
     * If this person is a director, gets the
     * <a href="https://contribute.imdb.com/updates/guide/directors">list of movies they
     * directed</a>, sorted by year. Otherwise returns <tt>null</tt>.
     */
    public List<BiographyItem> getDirectorIn() {
        return directorIn;
    }

    /**
     * Sets the list of movies that this person
     * <a href="https://contribute.imdb.com/updates/guide/directors">directed</a>.
     */
    public void setDirectorIn(List<BiographyItem> directorIn) {
        this.directorIn = directorIn;
    }

    /**
     * If this person is a <a href="https://contribute.imdb.com/updates/guide/editors">picture
     * editor</a>, gets a list of movies that they worked on, sorted by year.
     * Otherwise returns <tt>null</tt>.
     */
    public List<BiographyItem> getEditorIn() {
        return editorIn;
    }

    /**
     * Sets the list of movies on which this person worked as
     * <a href="https://contribute.imdb.com/updates/guide/editors">picture editor</a>.
     */
    public void setEditorIn(List<BiographyItem> editorIn) {
        this.editorIn = editorIn;
    }

    /**
     * If this person was a miscellaneous crew member for movies (i.e. not an actor, director,
     * producer, designer, etc), gets the list of movies that they worked on, sorted by year.
     * Otherwise returns <tt>null</tt>.
     */
    public List<BiographyItem> getMiscellaneousIn() {
        return miscellaneousIn;
    }

    /**
     * Sets the list of movies on which this person worked as miscellaneous crew member.
     */
    public void setMiscellaneousIn(List<BiographyItem> miscellaneousIn) {
        this.miscellaneousIn = miscellaneousIn;
    }

    /**
     * If this person is a <a href="https://contribute.imdb.com/updates/guide/producers">producer
     * (including executive producer, line producer, etc.)</a>, gets the list of movies that they
     * produced, sorted by year. Otherwise returns <tt>null</tt>.
     */
    public List<BiographyItem> getProducerIn() {
        return producerIn;
    }

    /**
     * Sets the list of movies on which this person worked as
     * <a href="https://contribute.imdb.com/updates/guide/producers">producer</a>.
     */
    public void setProducerIn(List<BiographyItem> producerIn) {
        this.producerIn = producerIn;
    }

    /**
     * If this person is a <a href="https://contribute.imdb.com/updates/guide/production_designers">production
     * designer</a>, gets the list of movies that they worked on, sorted by year. Otherwise
     * returns <tt>null</tt>.
     */
    public List<BiographyItem> getProductionDesignerIn() {
        return productionDesignerIn;
    }

    /**
     * Sets the list of movies on which this person worked as
     * <a href="https://contribute.imdb.com/updates/guide/production_designers">production designer</a>.
     */
    public void setProductionDesignerIn(List<BiographyItem> productionDesignerIn) {
        this.productionDesignerIn = productionDesignerIn;
    }

    /**
     * If this person is a <a href="https://contribute.imdb.com/updates/guide/writers">screenplay
     * or story writer</a>, gets a list of movies that they wrote, sorted by year.
     * Otherwise returns <tt>null</tt>.
     */
    public List<ScriptWriter> getWriterIn() {
        return writerIn;
    }

    /**
     * Sets the list of movies on which this person worked as
     * <a href="https://contribute.imdb.com/updates/guide/writers">screenplay
     * or story writer</a>.
     */
    public void setWriterIn(List<ScriptWriter> writerIn) {
        this.writerIn = writerIn;
    }
}
