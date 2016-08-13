package uk.ac.cam.cl.databases.moviedb.model;

/**
 * A Role appears on a {@link Person} object, and describes a part that this person
 * played in a particular movie (as actor or actress). Role is a subclass of
 * {@link BiographyItem}, so all of the notes there apply. Role extends
 * {@link BiographyItem} with information about the character that this person
 * played in this movie, and the position at which they appear in the credits.
 *
 * <p>When you look at a {@link Movie} object, the actors on that movie are given as
 * a list of {@link CreditActor} objects. So {@link CreditActor} is like {@link Role},
 * but in the opposite direction (from a movie to a person).
 */
public class Role extends BiographyItem {
    private String character;
    private Integer position;

    /**
     * Gets the <a href="https://contribute.imdb.com/updates/guide/characters">name of
     * the character</a> that this person played in this movie.
     */
    public String getCharacter() {
        return character;
    }

    /**
     * Gets the <a href="https://contribute.imdb.com/updates/guide/cast#castorders">position
     * of this actor in the cast order for the movie</a>. For example, the actor who is
     * listed first in the credits has a position of 1, the actor who is listed second has 2,
     * and so on. Uncredited actors have a <tt>null</tt> position.
     */
    public Integer getPosition() {
        return position;
    }
}
