package uk.ac.cam.cl.databases.moviedb.model;

import java.util.List;
import java.util.Map;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Movie {
    private int id;
    private String title;
    private int year, yearTo;
    private String info;
    private boolean suspended;
    private List<Actor> actors;
    private List<Person> cinematographers;
    private List<Person> composers;
    private List<Person> costumeDesigners;
    private List<Person> directors;
    private List<Person> editors;
    private List<Person> miscellaneous;
    private List<Person> producers;
    private List<Person> productionDesigners;
    private List<Writer> writers;
    private List<Certificate> certificates;
    private List<ColorInfo> colorInfo;
    private List<String> countries;
    private List<Company> distributors;
    private List<String> genres;
    private List<String> keywords;
    private List<Language> language;
    private List<Location> locations;
    private List<Company> prodCompanies;
    private Map<String, List<ReleaseDate>> releaseDates;
    private List<RunningTime> runningTimes;
    private List<GeneralInfo> soundMix;
    private List<Company> sfxCompanies;
    private Map<String, List<GeneralInfo>> technical;

    static final Gson JSON_CODEC = new GsonBuilder()
            .setPrettyPrinting()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    public static Movie fromJson(String json) {
        return JSON_CODEC.fromJson(json, Movie.class);
    }

    @Override
    public String toString() {
        return JSON_CODEC.toJson(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYearTo() {
        return yearTo;
    }

    public void setYearTo(int yearTo) {
        this.yearTo = yearTo;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public List<Person> getCinematographers() {
        return cinematographers;
    }

    public void setCinematographers(List<Person> cinematographers) {
        this.cinematographers = cinematographers;
    }

    public List<Person> getComposers() {
        return composers;
    }

    public void setComposers(List<Person> composers) {
        this.composers = composers;
    }

    public List<Person> getCostumeDesigners() {
        return costumeDesigners;
    }

    public void setCostumeDesigners(List<Person> costumeDesigners) {
        this.costumeDesigners = costumeDesigners;
    }

    public List<Person> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Person> directors) {
        this.directors = directors;
    }

    public List<Person> getEditors() {
        return editors;
    }

    public void setEditors(List<Person> editors) {
        this.editors = editors;
    }

    public List<Person> getMiscellaneous() {
        return miscellaneous;
    }

    public void setMiscellaneous(List<Person> miscellaneous) {
        this.miscellaneous = miscellaneous;
    }

    public List<Person> getProducers() {
        return producers;
    }

    public void setProducers(List<Person> producers) {
        this.producers = producers;
    }

    public List<Person> getProductionDesigners() {
        return productionDesigners;
    }

    public void setProductionDesigners(List<Person> productionDesigners) {
        this.productionDesigners = productionDesigners;
    }

    public List<Writer> getWriters() {
        return writers;
    }

    public void setWriters(List<Writer> writers) {
        this.writers = writers;
    }

    public List<Certificate> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<Certificate> certificates) {
        this.certificates = certificates;
    }

    public List<ColorInfo> getColorInfo() {
        return colorInfo;
    }

    public void setColorInfo(List<ColorInfo> colorInfo) {
        this.colorInfo = colorInfo;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<Company> getDistributors() {
        return distributors;
    }

    public void setDistributors(List<Company> distributors) {
        this.distributors = distributors;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<Language> getLanguage() {
        return language;
    }

    public void setLanguage(List<Language> language) {
        this.language = language;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public List<Company> getProdCompanies() {
        return prodCompanies;
    }

    public void setProdCompanies(List<Company> prodCompanies) {
        this.prodCompanies = prodCompanies;
    }

    public Map<String, List<ReleaseDate>> getReleaseDates() {
        return releaseDates;
    }

    public void setReleaseDates(Map<String, List<ReleaseDate>> releaseDates) {
        this.releaseDates = releaseDates;
    }

    public List<RunningTime> getRunningTimes() {
        return runningTimes;
    }

    public void setRunningTimes(List<RunningTime> runningTimes) {
        this.runningTimes = runningTimes;
    }

    public List<GeneralInfo> getSoundMix() {
        return soundMix;
    }

    public void setSoundMix(List<GeneralInfo> soundMix) {
        this.soundMix = soundMix;
    }

    public List<Company> getSfxCompanies() {
        return sfxCompanies;
    }

    public void setSfxCompanies(List<Company> sfxCompanies) {
        this.sfxCompanies = sfxCompanies;
    }

    public Map<String, List<GeneralInfo>> getTechnical() {
        return technical;
    }

    public void setTechnical(Map<String, List<GeneralInfo>> technical) {
        this.technical = technical;
    }
}
