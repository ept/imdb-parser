package uk.ac.cam.cl.databases.moviedb.model;

/**
 * A CreditActor appears on a {@link Movie} object, and describes one of the actors
 * or actresses who played in that movie. CreditActor is a subclass of
 * {@link CreditPerson}, so all of the notes there apply. CreditActor extends
 * {@link CreditPerson} with information about the character that this actor played
 * in this movie, and the position at which they appear in the credits.
 *
 * <p>When you look at a {@link Person} object, the movies in which that person
 * has played are given as a list of {@link Role} objects. So {@link Role} is like
 * {@link CreditActor}, but in the opposite direction (from a person to a movie).
 */
public class CreditActor extends CreditPerson {
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
