package uk.ac.cam.cl.databases.moviedb.internal;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Consumer;
import uk.ac.cam.cl.databases.moviedb.MovieDB;

/**
 * NOTE: This importer runs out of memory and crashes. Haven't yet figured out why
 * memory isn't getting freed.
 */
public class ImportDocument2 {
    private final File outputDir;
    private final String moviesTable, peopleTable;

    public ImportDocument2(File outputDir, boolean onlyTopTitles) {
        this.outputDir = outputDir;
        if (onlyTopTitles) {
            this.moviesTable = "movies_doc_small";
            this.peopleTable = "people_doc_small";
        } else {
            this.moviesTable = "movies_doc";
            this.peopleTable = "people_doc";
        }
    }

    private void readTable(Connection db, String tableName, Consumer<? super String> action) throws SQLException {
        try (Statement query = db.createStatement()) {
            final ResultSet results = query.executeQuery("select properties from " + tableName);
            while (results.next()) {
                action.accept(results.getString(1));
            }
        }
    }

    public void process() throws IOException, SQLException {
        if (!outputDir.exists()) outputDir.mkdirs();

        try (Connection pg = DriverManager.getConnection("jdbc:postgresql:");
                MovieDB movieDB = MovieDB.open(outputDir.getAbsolutePath())) {
            readTable(pg, moviesTable, movieDB::putMovie);
            readTable(pg, peopleTable, movieDB::putPerson);
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 2 || !(args[0].equals("--large") || args[0].equals("--small"))) {
            System.err.println("Usage: ImportDocument [--large|--small] output-dir");
            System.exit(1);
        }
        Class.forName("org.postgresql.Driver");
        new ImportDocument2(new File(args[1]), args[0].equals("--small")).process();
    }
}
