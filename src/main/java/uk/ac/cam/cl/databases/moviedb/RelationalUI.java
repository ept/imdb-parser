package uk.ac.cam.cl.databases.moviedb;

import java.io.File;
import org.hsqldb.util.DatabaseManagerSwing;

public class RelationalUI {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java -jar relational-db.jar <path-to-extract-database>");
            System.exit(1);
        }

        File dataDir = new File(args[0]);
        if (!dataDir.exists()) dataDir.mkdirs();

        DatabaseManagerSwing.main(new String[] {
            "--url", "jdbc:hsqldb:file:" + new File(dataDir, "moviedb") + ";shutdown=true"
        });
    }
}
