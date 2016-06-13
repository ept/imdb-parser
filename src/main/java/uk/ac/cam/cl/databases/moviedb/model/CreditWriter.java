package uk.ac.cam.cl.databases.moviedb.model;

/**
 * A {@link CreditWriter} appears on a {@link Movie} object, and describes one of
 * the people who is credited as a screenplay or story writer on that movie.
 * CreditWriter is a subclass of {@link CreditPerson}, so all of the notes there
 * apply. CreditWriter extends {@link CreditPerson} with information about the order
 * in which the writers are credited, for which there are
 * <a href="https://contribute.imdb.com/updates/guide/writers">quite specific and
 * complex rules</a> reflecting collaborations and level of contribution.
 *
 * <p>When you look at a {@link Person} object, the movies that the person has
 * written are given as a list of {@link ScriptWriter} objects. So {@link ScriptWriter}
 * is like {@link CreditWriter}, but in the opposite direction (from a person to
 * a movie).
 */
public class CreditWriter extends CreditPerson {
    private Integer lineOrder, groupOrder, subgroupOrder;

    public Integer getLineOrder() {
        return lineOrder;
    }

    public void setLineOrder(Integer lineOrder) {
        this.lineOrder = lineOrder;
    }

    public Integer getGroupOrder() {
        return groupOrder;
    }

    public void setGroupOrder(Integer groupOrder) {
        this.groupOrder = groupOrder;
    }

    public Integer getSubgroupOrder() {
        return subgroupOrder;
    }

    public void setSubgroupOrder(Integer subgroupOrder) {
        this.subgroupOrder = subgroupOrder;
    }
}
