package uk.ac.cam.cl.databases.moviedb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;
import uk.ac.cam.cl.databases.moviedb.model.Movie;

public class Import {
    private File inputFile, outputDir;
    private static final Charset UTF8 = Charset.forName("UTF-8");

    public Import(File inputFile, File outputDir) {
        this.inputFile = inputFile;
        this.outputDir = outputDir;
    }

    public void process() throws IOException {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(
                                    new GZIPInputStream(new FileInputStream(inputFile)), UTF8));
                MovieDB movieDB = new MovieDB(outputDir)) {
            input.lines().forEach(json -> movieDB.putMovie(json));
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: Import input.json.gz output-dir");
            System.exit(1);
        }
        //new Import(new File(args[0]), new File(args[1])).process();
        try (MovieDB movies = new MovieDB(new File(args[1]))) {
            for (Movie m : movies.getByTitlePrefix("Star Wars")) {
                System.out.println(m);
            }
        }
    }
}
