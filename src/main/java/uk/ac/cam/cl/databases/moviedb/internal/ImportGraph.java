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
    private static Integer next_keyword_id = 1;
    private static Integer next_language_id = 1;
    private static Integer next_location_id = 1;

    private static TreeMap<String, Integer> country_id_map = new TreeMap<>();
    private static TreeMap<String, Integer> genre_id_map = new TreeMap<>();
    private static TreeMap<String, Integer> keyword_id_map = new TreeMap<>();
    private static TreeMap<String, Integer> language_id_map = new TreeMap<>();
    private static TreeMap<String, Integer> location_id_map = new TreeMap<>();

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

    private static Integer get_keyword_id(String s) {
        Integer id = keyword_id_map.get(s);
        if (id == null) {
            keyword_id_map.put(s, next_keyword_id);
            id = next_keyword_id;
            next_keyword_id++;
        }
        return id;
    }

    private static Integer get_language_id(String s) {
        Integer id = language_id_map.get(s);
        if (id == null) {
            language_id_map.put(s, next_language_id);
            id = next_language_id;
            next_language_id++;
        }
        return id;
    }

    private static Integer get_location_id(String s) {
        Integer id = location_id_map.get(s);
        if (id == null) {
            location_id_map.put(s, next_location_id);
            id = next_location_id;
            next_location_id++;
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
            f_person.write("person_id:ID(Person)|name:string|gender:string|:LABEL\n");

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
            f_movie.write("movie_id:ID(Movie)|title:string|year:int|color_info:string[]|running_times:string[]|:LABEL\n");

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

            FileWriter f_has_keyword = new FileWriter(csv_dir + "/has_keyword.csv");
            f_has_keyword.write(":START_ID(Movie)|:END_ID(Keyword)|:TYPE\n");

            FileWriter f_has_language = new FileWriter(csv_dir + "/has_language.csv");
            f_has_language.write(":START_ID(Movie)|:END_ID(Language)|:TYPE|note:string\n");

            FileWriter f_has_location = new FileWriter(csv_dir + "/has_location.csv");
            f_has_location.write(":START_ID(Movie)|:END_ID(Location)|:TYPE|note:string\n");

            // scan movies
            for (Movie movie : database.getByTitlePrefix("")) {
                int mid = movie.getId();

                if (movie.getKeywords() != null) {
                    for (String s : movie.getKeywords()) {
                        Integer kid = get_keyword_id(s);
                        if (kid == null) kid = 0; // should never happen!
                        f_has_keyword.write(mid +
                                          "|" +
                                          kid +
                                          "|HAS_KEYWORD\n");

                    }
                }
                if (movie.getLanguages() != null) {
                    for (Language l : movie.getLanguages()) {
                        String s = l.getLanguage(); 
                        Integer lid = get_language_id(s);
                        if (lid == null) lid = 0; // should never happen!
                        String note = l.getNote();
                        if (note == null) note = "";
                        f_has_language.write(mid +
					     "|" +
					     lid +
					     "|HAS_LANGUAGE|" +
                                             note.replace("\"", "\\\"") +
					     "\n");

                    }
                }
                if (movie.getLocations() != null) {
                    for (Location l : movie.getLocations()) {
                        String s = l.getLocation(); 
                        Integer lid = get_location_id(s);
                        if (lid == null) lid = 0; // should never happen!
                        String note = l.getNote();
                        if (note == null) note = "";
                        f_has_location.write(mid +
					     "|" +
					     lid +
					     "|HAS_LOCATION|" +
                                             note.replace("\"", "\\\"") +
					     "\n");

                    }
                }


                // gather list-based movie properties
                // list properties are ;-separated
                String running_times = "";
                String color_info = "";


                // append note to the running time!
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

                // append note to the color info!
                if (movie.getColorInfo() != null) {
                    boolean first = true;
                    for (ColorInfo t : movie.getColorInfo()) {
                        String color = t.getColorInfo();
                        String note = t.getNote();
                        if (note != null) color = color + ":" + note.replace("\"", "\\\"");
                        if (first) {
                            color_info = color;
                            first = false;
                        } else {
                            color_info = color_info + ";" + color;
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
                              color_info +
                              "|" +
                              running_times +
                              "|Movie\n");

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
                                        "ACTS_IN" +
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
                                       "CINEMATOGRAPHER_FOR" +
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
                                        "COMPOSER_FOR" +
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
            f_has_keyword.close();
            f_has_language.close();
            f_has_location.close();

            // Finally, output nodes for countries, genres, keywords, languages, locations 

            FileWriter f_country = new FileWriter(csv_dir + "/country.csv");
            f_country.write("country_id:ID(Country)|country:string|:LABEL\n");

            for (Map.Entry<String, Integer> map_entry : country_id_map.entrySet()) {
                f_country.write(map_entry.getValue() +
                                "|" +
                                map_entry.getKey() +
                                "|Country\n");
            }
            f_country.close();

            FileWriter f_genres = new FileWriter(csv_dir + "/genres.csv");
            f_genres.write("genre_id:ID(Genre)|genre:string|:LABEL\n");

            for (Map.Entry<String, Integer> map_entry : genre_id_map.entrySet()) {
                f_genres.write(map_entry.getValue() +
                               "|" +
                               map_entry.getKey() +
                               "|Genre\n");
            }
            f_genres.close();

            FileWriter f_keywords = new FileWriter(csv_dir + "/keywords.csv");
            f_keywords.write("keyword_id:ID(Keyword)|keyword:string|:LABEL\n");

            for (Map.Entry<String, Integer> map_entry : keyword_id_map.entrySet()) {
		String s = map_entry.getKey(); 
                f_keywords.write(map_entry.getValue() +
                               "|" +
                               s.replace("\"", "\\\"") +
                               "|Keyword\n");
            }
            f_keywords.close();



            FileWriter f_languages = new FileWriter(csv_dir + "/languages.csv");
            f_languages.write("language_id:ID(Language)|language:string|:LABEL\n");

            for (Map.Entry<String, Integer> map_entry : language_id_map.entrySet()) {
                f_languages.write(map_entry.getValue() +
                               "|" +
                               map_entry.getKey() +
                               "|Language\n");
            }
            f_languages.close();


            FileWriter f_locations = new FileWriter(csv_dir + "/locations.csv");
            f_locations.write("location_id:ID(Location)|location:string|:LABEL\n");

            for (Map.Entry<String, Integer> map_entry : location_id_map.entrySet()) {
		String s = map_entry.getKey(); 
                f_locations.write(map_entry.getValue() +
                               "|" +
                               s.replace("\"", "\\\"") + 
                               "|Location\n");
            }
            f_locations.close();

        }

        ImportTool.main(new String[] {
            "--delimiter", "|", 
            "--into", neo4j_dir,
            "--nodes", csv_dir + "/movie.csv",
            "--nodes", csv_dir + "/person.csv",
            "--nodes", csv_dir + "/country.csv",
            "--nodes", csv_dir + "/genres.csv",
            "--nodes", csv_dir + "/keywords.csv",
            "--nodes", csv_dir + "/languages.csv",
            "--nodes", csv_dir + "/locations.csv",
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
            "--relationships", csv_dir + "/has_keyword.csv",
            "--relationships", csv_dir + "/has_language.csv",
            "--relationships", csv_dir + "/has_location.csv",
            "--relationships", csv_dir + "/writes.csv"
        });
    }
}
