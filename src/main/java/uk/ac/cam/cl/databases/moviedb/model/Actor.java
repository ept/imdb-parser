package uk.ac.cam.cl.databases.moviedb.model;

public class Actor extends Person {
    private String character;
    private Integer position;

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
