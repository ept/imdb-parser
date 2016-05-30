import java.io.File;
import uk.ac.cam.cl.databases.moviedb.MovieDB;
import uk.ac.cam.cl.databases.moviedb.model.*;

public class DocumentExample {
    public static void main(String[] args) {
        // Open the database files
        try (MovieDB database = new MovieDB(new File("document-db"))) {
            // Search for movies whose title starts with some string
            System.out.println("Titles of Harry Potter movies:");
            for (Movie movie : database.getByTitlePrefix("Harry Potter")) {
                System.out.println("\t" + movie.getTitle());
            }
            System.out.println();

            for (Person person : database.getByNamePrefix("Radcliffe, Daniel")) {
                System.out.println(person.getName() + " acted in:");
                for (Role role : person.getActorIn()) {
                    System.out.println("\t" + role.getTitle() + " (as " + role.getCharacter() + ")");
                }
            }
        }
    }
}
