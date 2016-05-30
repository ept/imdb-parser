package uk.ac.cam.cl.databases.moviedb.model;

import java.util.List;

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

    public static Person fromJson(String json) {
        return Movie.JSON_CODEC.fromJson(json, Person.class);
    }

    @Override
    public String toString() {
        return Movie.JSON_CODEC.toJson(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Role> getActorIn() {
        return actorIn;
    }

    public void setActorIn(List<Role> actorIn) {
        this.actorIn = actorIn;
    }

    public List<BiographyItem> getCinematographerIn() {
        return cinematographerIn;
    }

    public void setCinematographerIn(List<BiographyItem> cinematographerIn) {
        this.cinematographerIn = cinematographerIn;
    }

    public List<BiographyItem> getComposerIn() {
        return composerIn;
    }

    public void setComposerIn(List<BiographyItem> composerIn) {
        this.composerIn = composerIn;
    }

    public List<BiographyItem> getCostumeDesignerIn() {
        return costumeDesignerIn;
    }

    public void setCostumeDesignerIn(List<BiographyItem> costumeDesignerIn) {
        this.costumeDesignerIn = costumeDesignerIn;
    }

    public List<BiographyItem> getDirectorIn() {
        return directorIn;
    }

    public void setDirectorIn(List<BiographyItem> directorIn) {
        this.directorIn = directorIn;
    }

    public List<BiographyItem> getEditorIn() {
        return editorIn;
    }

    public void setEditorIn(List<BiographyItem> editorIn) {
        this.editorIn = editorIn;
    }

    public List<BiographyItem> getMiscellaneousIn() {
        return miscellaneousIn;
    }

    public void setMiscellaneousIn(List<BiographyItem> miscellaneousIn) {
        this.miscellaneousIn = miscellaneousIn;
    }

    public List<BiographyItem> getProducerIn() {
        return producerIn;
    }

    public void setProducerIn(List<BiographyItem> producerIn) {
        this.producerIn = producerIn;
    }

    public List<BiographyItem> getProductionDesignerIn() {
        return productionDesignerIn;
    }

    public void setProductionDesignerIn(List<BiographyItem> productionDesignerIn) {
        this.productionDesignerIn = productionDesignerIn;
    }

    public List<ScriptWriter> getWriterIn() {
        return writerIn;
    }

    public void setWriterIn(List<ScriptWriter> writerIn) {
        this.writerIn = writerIn;
    }
}
