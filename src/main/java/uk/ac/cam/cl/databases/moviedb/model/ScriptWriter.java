package uk.ac.cam.cl.databases.moviedb.model;

/**
 * A ScriptWriter appears on a {@link Person} object, and describes a movie for
 * which that person wrote the screenplay or story. ScriptWriter is a subclass of
 * {@link BiographyItem}, so all of the notes there apply. ScriptWriter extends
 * {@link BiographyItem} with information about the order in which the writers
 * are credited, for which there are
 * <a href="https://contribute.imdb.com/updates/guide/writers">quite specific and
 * complex rules</a> reflecting collaborations and level of contribution.
 *
 * <p>When you look at a {@link Movie} object, the writers of that movie are
 * given as a list of {@link CreditWriter} objects. So {@link CreditWriter} is
 * like {@link ScriptWriter}, but in the opposite direction (from a movie to a
 * person).
 */
public class ScriptWriter extends BiographyItem {
    private Integer lineOrder, groupOrder, subgroupOrder;

    /**
     * Gets the first part of the order triple.
     */
    public Integer getLineOrder() {
        return lineOrder;
    }

    /**
     * Gets the second part of the order triple.
     */
    public Integer getGroupOrder() {
        return groupOrder;
    }

    /**
     * Gets the third part of the order triple.
     */
    public Integer getSubgroupOrder() {
        return subgroupOrder;
    }
}
