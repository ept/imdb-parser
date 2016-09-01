package uk.ac.cam.cl.databases.moviedb.internal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;
import uk.ac.cam.cl.databases.moviedb.MovieDB;

public class ImportDocument {
    private final File moviesFile, peopleFile, outputDir;
    private static final Charset UTF8 = Charset.forName("UTF-8");

    public ImportDocument(File inputFile, File peopleFile, File outputDir) {
        this.moviesFile = inputFile;
        this.peopleFile = peopleFile;
        this.outputDir = outputDir;
    }

    private BufferedReader readFile(File inputFile) throws IOException {
        return new BufferedReader(new InputStreamReader(
            new GZIPInputStream(new FileInputStream(inputFile)), UTF8));
    }

    public void process() throws IOException {
        if (!outputDir.exists()) outputDir.mkdirs();

        try (MovieDB movieDB = MovieDB.open(outputDir.getAbsolutePath())) {
            try (BufferedReader input = readFile(moviesFile)) {
                input.lines().forEach(json -> movieDB.putMovie(json));
            }

            try (BufferedReader input = readFile(peopleFile)) {
                input.lines().forEach(json -> movieDB.putPerson(json));
            }
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            System.err.println("Usage: Import movies_doc.json.gz people_doc.json.gz output-dir");
            System.exit(1);
        }
        new ImportDocument(new File(args[0]), new File(args[1]), new File(args[2])).process();
    }
}
