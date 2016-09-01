package uk.ac.cam.cl.databases.moviedb.internal;

import java.io.File;
import org.hsqldb.util.DatabaseManagerSwing;

public class RelationalUI {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java -jar relational-db.jar <path-to-data-directory>");
            System.exit(1);
        }

        File dataDir = new File(args[0]);
        if (!dataDir.exists()) {
            System.err.println("Data directory does not exist: " + dataDir.getAbsolutePath());
            System.exit(1);
        }

        DatabaseManagerSwing.main(new String[] {
            "--url", "jdbc:hsqldb:file:" + new File(dataDir, "moviedb") + ";shutdown=true"
        });
    }
}
