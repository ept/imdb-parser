package uk.ac.cam.cl.databases.moviedb.internal;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.neo4j.tooling.ImportTool;
import uk.ac.cam.cl.databases.moviedb.MovieDB;
import uk.ac.cam.cl.databases.moviedb.model.*;

//
// ImportGraph document-database-directory csv-output-directory neo4j-output-directory
//
public class ImportGraph {

    // 0 is used as an error and should never happen ....
    private static Integer next_country_id = 1;
    private static Integer next_genre_id = 1;

    private static TreeMap<String, Integer> country_id_map = new TreeMap<>();
    private static TreeMap<String, Integer> genre_id_map = new TreeMap<>();

    private static Integer get_country_id(String s) {
        Integer id = country_id_map.get(s);
        if (id == null) {
            country_id_map.put(s, next_country_id);
            id = next_country_id;
            next_country_id++;
        }
        return id;
    }

    private static Integer get_genre_id(String s) {
        Integer id = genre_id_map.get(s);
        if (id == null) {
            genre_id_map.put(s, next_genre_id);
            id = next_genre_id;
            next_genre_id++;
        }
        return id;
    }

    public static void main(String[] args) throws Exception {

        String document_db_dir = args[0];
        String csv_dir = args[1];
        String neo4j_dir = args[2];
        new File(csv_dir).mkdirs();

        try (MovieDB database = MovieDB.open(document_db_dir)) {

            FileWriter f_person = new FileWriter(csv_dir + "/person.csv");
            f_person.write("personID:ID(Person)|name:string|gender:string|:LABEL\n");

            // scan persons
            for (Person person : database.getByNamePrefix("")) {
                String name = person.getName();
                if (name == null) {
                    name = "";
                }
                f_person.write(person.getId() +
                               "|" +
                               name.replace("\"", "\\\"") +
                               "|" +
                               person.getGender() +
                               "|Person\n");
            }
            f_person.close();

            // Must use ID-spaces since movie ids and person ids overlap in large data set

            FileWriter f_movie = new FileWriter(csv_dir + "/movie.csv");
            // FIX Keywords ... ?
            // f_movie.write("movieID:ID(Movie)|title:string|year:int|running_times:string[]|keywords:string[]|:LABEL\n");
            f_movie.write("movieID:ID(Movie)|title:string|year:int|running_times:string[]|:LABEL\n");

            FileWriter f_acts_in = new FileWriter(csv_dir + "/acts_in.csv");
            f_acts_in.write(":START_ID(Person)|:END_ID(Movie)|:TYPE|role:string|position:int|info:string\n");

            FileWriter f_directs = new FileWriter(csv_dir + "/directs.csv");
            f_directs.write(":START_ID(Person)|:END_ID(Movie)|:TYPE|info:string\n");

            FileWriter f_camera = new FileWriter(csv_dir + "/camera.csv");
            f_camera.write(":START_ID(Person)|:END_ID(Movie)|:TYPE|info:string\n");

            FileWriter f_compose = new FileWriter(csv_dir + "/compose.csv");
            f_compose.write(":START_ID(Person)|:END_ID(Movie)|:TYPE|info:string\n");

            FileWriter f_edits = new FileWriter(csv_dir + "/edits.csv");
            f_edits.write(":START_ID(Person)|:END_ID(Movie)|:TYPE|info:string\n");

            FileWriter f_produces = new FileWriter(csv_dir + "/produces.csv");
            f_produces.write(":START_ID(Person)|:END_ID(Movie)|:TYPE|info:string\n");

            FileWriter f_prod_design = new FileWriter(csv_dir + "/production_design.csv");
            f_prod_design.write(":START_ID(Person)|:END_ID(Movie)|:TYPE|info:string\n");

            FileWriter f_design = new FileWriter(csv_dir + "/costume_design.csv");
            f_design.write(":START_ID(Person)|:END_ID(Movie)|:TYPE|info:string\n");

            FileWriter f_writes = new FileWriter(csv_dir + "/writes.csv");
            f_writes.write(":START_ID(Person)|:END_ID(Movie)|:TYPE|info:string|line_order:int|group_order:int|subgroup_order:int\n");

            FileWriter f_release_dates = new FileWriter(csv_dir + "/release_dates.csv");
            f_release_dates.write(":START_ID(Movie)|:END_ID(Country)|:TYPE|date:string|note:string\n");

            FileWriter f_certificates = new FileWriter(csv_dir + "/certificates.csv");
            f_certificates.write(":START_ID(Movie)|:END_ID(Country)|:TYPE|certificate:string|note:string\n");

            FileWriter f_has_genre = new FileWriter(csv_dir + "/has_genre.csv");
            f_has_genre.write(":START_ID(Movie)|:END_ID(Genre)|:TYPE\n");

            // scan movies
            for (Movie movie : database.getByTitlePrefix("")) {
                int mid = movie.getId();
                String keywords = "";
                String running_times = "";

                // gather list-based movie properties
                // list properties are ;-separated
                if (movie.getKeywords() != null) {
                    boolean first = true;
                    for (String s : movie.getKeywords()) {
                        if (first) {
                            keywords = s;
                            first = false;
                        } else {
                            keywords = keywords + ";" + s;
                        }
                    }
                }

                // since running times are already strange, just append note to the running time!
                if (movie.getRunningTimes() != null) {
                    boolean first = true;
                    for (RunningTime t : movie.getRunningTimes()) {
                        String time = t.getRunningTime();
                        String note = t.getNote();
                        if (note != null) time = time + ":" + note.replace("\"", "\\\"");
                        if (first) {
                            running_times = time;
                            first = false;
                        } else {
                            running_times = running_times + ";" + time;
                        }
                    }
                }

                // output movie record
                String title = movie.getTitle();
                if (title == null) title = ""; // you never know ...
                f_movie.write(mid +
                              "|" +
                              title.replace("\"", "\\\"") +
                              "|" +
                              movie.getYear() +
                              "|" +
                              running_times +
                              // FIX KEYWORDS ?
                              // When these are included all movie properties vanish in Neo4j!
                              // Are some keyword lists too long (600 keywords) for Neo4j?
                              // Or is there another problem?
                              // "|" +
                              // keywords +
                              "|Movie\n");

                if (movie.getCertificates() != null) {
                    for (Certificate c : movie.getCertificates()) {
                        String note = c.getNote();
                        if (note == null) note = "";
                        Integer country_id = get_country_id(c.getCountry());
                        if (country_id == null) country_id = 0; // should never happen!
                        f_certificates.write(mid +
                                             "|" +
                                             country_id +
                                             "|" +
                                             "CERTIFIED_IN" +
                                             "|" +
                                             c.getCertificate() +
                                             "|" +
                                             note.replace("\"", "\\\"") +
                                             "\n");
                    }
                }

                if (movie.getReleaseDates() != null) {
                    for (Map.Entry<String, List<ReleaseDate>> rel : movie.getReleaseDates().entrySet()) {
                        Integer country_id = get_country_id(rel.getKey());
                        if (country_id == null) country_id = 0; // should never happen!
                        for (ReleaseDate d : rel.getValue()) {
                            String note = d.getNote();
                            if (note == null) note = "";
                            String date = d.getReleaseDate();
                            if (date == null) date = "";
                            f_release_dates.write(mid +
                                                 "|" +
                                                 country_id +
                                                 "|" +
                                                 "RELEASED_IN" +
                                                 "|" + date + "|" +
                                                 note.replace("\"", "\\\"") +
                                                 "\n");
                        }
                    }
                }

                if (movie.getDirectors() != null) {
                    for (CreditPerson p : movie.getDirectors()) {
                        String info = p.getInfo();
                        if (info == null) info = "";
                        f_directs.write(p.getPersonId() +
                                        "|" +
                                        mid +
                                        "|" +
                                        "DIRECTED" +
                                        "|" +
                                        info.replace("\"", "\\\"") +
                                        "\n");
                    }
                }

                if (movie.getActors() != null) {
                    for (CreditActor p : movie.getActors()) {
                        Integer pos = p.getPosition();
                        String info = p.getInfo();
                        String character = p.getCharacter();

                        if (info == null) info = "";
                        if (character == null) character = "";

                        f_acts_in.write(p.getPersonId() +
                                        "|" +
                                        mid +
                                        "|" +
                                        "ACTED_IN" +
                                        "|" +
                                        character.replace("\"", "\\\"") +
                                        "|" +
                                        ((pos == null) ? 0 : pos) + // really?
                                        "|" +
                                        info.replace("\"", "\\\"") +
                                        "\n");
                    }
                }

                if (movie.getCinematographers() != null) {
                    for (CreditPerson p : movie.getCinematographers()) {
                        String info = p.getInfo();
                        if (info == null) info = "";
                        f_camera.write(p.getPersonId() +
                                       "|" +
                                       mid +
                                       "|" +
                                       "WAS_CINEMATOGRAPHER_FOR" +
                                       "|" +
                                       info.replace("\"", "\\\"") +
                                       "\n");
                    }
                }

                if (movie.getComposers() != null) {
                    for (CreditPerson p : movie.getComposers()) {
                        String info = p.getInfo();
                        if (info == null) info = "";
                        f_compose.write(p.getPersonId() +
                                        "|" +
                                        mid +
                                        "|" +
                                        "WAS_COMPOSER_FOR" +
                                        "|" +
                                        info.replace("\"", "\\\"") +
                                        "\n");
                    }
                }

                if (movie.getEditors() != null) {
                    for (CreditPerson p : movie.getEditors()) {
                        String info = p.getInfo();
                        if (info == null) info = "";
                        f_edits.write(p.getPersonId() +
                                      "|" +
                                      mid +
                                      "|" +
                                      "EDITED" +
                                      "|" +
                                      info.replace("\"", "\\\"") +
                                      "\n");
                    }
                }

                if (movie.getCostumeDesigners() != null) {
                    for (CreditPerson p : movie.getCostumeDesigners()) {
                        String info = p.getInfo();
                        if (info == null) info = "";
                        f_design.write(p.getPersonId() +
                                       "|" +
                                       mid +
                                       "|" +
                                       "COSTUME_DESIGNER_FOR" +
                                       "|" +
                                       info.replace("\"", "\\\"") +
                                       "\n");
                    }
                }

                if (movie.getProductionDesigners() != null) {
                    for (CreditPerson p : movie.getProductionDesigners()) {
                        String info = p.getInfo();
                        if (info == null) info = "";
                        f_prod_design.write(p.getPersonId() +
                                            "|" +
                                            mid +
                                            "|" +
                                            "PRODUCTION_DESIGNER_FOR" +
                                            "|" +
                                            info.replace("\"", "\\\"") +
                                            "\n");
                    }
                }

                if (movie.getProducers() != null) {
                    for (CreditPerson p : movie.getProducers()) {
                        String info = p.getInfo();
                        if (info == null) info = "";
                        f_produces.write(p.getPersonId() +
                                         "|" +
                                         mid +
                                         "|" +
                                         "PRODUCED" +
                                         "|" +
                                         info.replace("\"", "\\\"") +
                                         "\n");
                    }
                }

                if (movie.getWriters() != null) {
                    for (CreditWriter p : movie.getWriters()) {
                        String info = p.getInfo();
                        if (info == null) info = "";
                        f_writes.write(p.getPersonId() +
                                       "|" +
                                       mid +
                                       "|" +
                                       "WROTE" +
                                       "|" +
                                       info.replace("\"", "\\\"") +
                                       "|" +
                                       p.getLineOrder() +
                                       "|" +
                                       p.getGroupOrder() +
                                       "|" +
                                       p.getSubgroupOrder() +
                                       "\n");
                    }
                }

                if (movie.getGenres() != null) {
                    for (String s : movie.getGenres()) {
                        Integer genre_id = get_genre_id(s);
                        if (genre_id == null) genre_id = 0; // should never happen!
                        f_has_genre.write(mid +
                                          "|" +
                                          genre_id +
                                          "|HAS_GENRE\n");
                    }
                }
            }

            f_movie.close();
            f_acts_in.close();
            f_compose.close();
            f_directs.close();
            f_edits.close();
            f_design.close();
            f_writes.close();
            f_produces.close();
            f_camera.close();
            f_prod_design.close();
            f_certificates.close();
            f_release_dates.close();
            f_has_genre.close();

            // Finally, output countries, genres

            FileWriter f_country = new FileWriter(csv_dir + "/country.csv");
            f_country.write("countryID:ID(Country)|country:string|:LABEL\n");

            for (Map.Entry<String, Integer> map_entry : country_id_map.entrySet()) {
                f_country.write(map_entry.getValue() +
                                "|" +
                                map_entry.getKey() +
                                "|Country\n");
            }
            f_country.close();

            FileWriter f_genres = new FileWriter(csv_dir + "/genres.csv");
            f_genres.write("genreID:ID(Genre)|genre:string|:LABEL\n");

            for (Map.Entry<String, Integer> map_entry : genre_id_map.entrySet()) {
                f_genres.write(map_entry.getValue() +
                               "|" +
                               map_entry.getKey() +
                               "|Genre\n");
            }
            f_genres.close();
        }

        ImportTool.main(new String[] {
            "--delimiter", "|", 
            "--into", neo4j_dir,
            "--nodes", csv_dir + "/movie.csv",
            "--nodes", csv_dir + "/person.csv",
            "--nodes", csv_dir + "/country.csv",
            "--nodes", csv_dir + "/genres.csv",
            "--relationships", csv_dir + "/acts_in.csv",
            "--relationships", csv_dir + "/directs.csv",
            "--relationships", csv_dir + "/camera.csv",
            "--relationships", csv_dir + "/compose.csv",
            "--relationships", csv_dir + "/edits.csv",
            "--relationships", csv_dir + "/produces.csv",
            "--relationships", csv_dir + "/production_design.csv",
            "--relationships", csv_dir + "/costume_design.csv",
            "--relationships", csv_dir + "/certificates.csv",
            "--relationships", csv_dir + "/release_dates.csv", 
            "--relationships", csv_dir + "/has_genre.csv",
            "--relationships", csv_dir + "/writes.csv"
        });
    }
}
