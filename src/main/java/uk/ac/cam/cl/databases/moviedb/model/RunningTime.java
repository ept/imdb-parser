package uk.ac.cam.cl.databases.moviedb.model;

/**
 * A RunningTime object appears on a {@link Movie} object, and describes the
 * <a href="https://contribute.imdb.com/updates/guide/running_times">running time</a>
 * of the movie in minutes. There is usually only one running time, but there may
 * be several different values if there are different versions of the movie with
 * different lengths.
 */
public class RunningTime {
    private String runningTime, note;

    /**
     * Gets the running time, either just the number of minutes, or the number of
     * minutes prefixed with a country, such as <tt>"Brazil:58"</tt> if different
     * countries have different versions of the movie.
     */
    public String getRunningTime() {
        return runningTime;
    }

    /**
     * Sets the running time in minutes.
     */
    public void setRunningTime(String runningTime) {
        this.runningTime = runningTime;
    }

    /**
     * Gets an informational attribute about the running time, for example describing
     * which version of the movie it applies to.
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets an informational attribute about the running time, for example describing
     * which version of the movie it applies to.
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Generates a JSON string representing this RunningTime object.
     */
    @Override
    public String toString() {
        return Movie.JSON_CODEC.toJson(this);
    }
}
