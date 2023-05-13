import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MovieRepositoryTest {
    EntityManagerFactory entityManagerFactory;
    MovieRepository repo;

    @BeforeEach
    void init(){
        entityManagerFactory = Persistence.createEntityManagerFactory("pu");
        repo = new MovieRepository(entityManagerFactory);
    }

    @AfterEach
    void close(){
        entityManagerFactory.close();
    }

    @Test
    void testListAll_Empty(){
        List<Movie> movies = repo.listAll();
        assertEquals(List.of(), movies);
    }

    @Test
    void testSave1_findById(){
        long id = repo.save(new Movie("Titanic",123, LocalDate.parse("1997-12-17")));

        Assertions.assertThat(List.of(repo.findById(id)))
            .extracting(Movie::getTitle, Movie::getLength)
            .contains(Assertions.tuple("Titanic", 123));
    }

    @Test
    void testSave3_listThemAll(){
        repo.save(new Movie("Titanic",123, LocalDate.parse("1997-12-17")));
        repo.save(new Movie("LOTR",180, LocalDate.parse("2005-09-01")));
        repo.save(new Movie("Dummy",0, LocalDate.parse("9999-12-31")));

        Assertions.assertThat(repo.listAll())
            .hasSize(3)
            .extracting(Movie::getTitle)
            .containsExactly("Dummy", "LOTR", "Titanic");
    }

    @Test
    void testUpdateTitleById(){
        repo.save(new Movie("Titanic",123, LocalDate.parse("1997-12-17")));
        long id = repo.save(new Movie("LOTR",180, LocalDate.parse("2005-09-01")));
        repo.save(new Movie("Dummy",0, LocalDate.parse("9999-12-31")));

        String newTitle = "Lord of the rings";
        repo.updateTitleById(id, newTitle);
        Movie movie = repo.findById(id);

        assertEquals(newTitle, movie.getTitle());
    }

    @Test
    void save3ThenRemove2nd_listAll2(){
        repo.save(new Movie("Titanic",123, LocalDate.parse("1997-12-17")));
        repo.save(new Movie("LOTR",180, LocalDate.parse("2005-09-01")));
        long id = repo.save(new Movie("Dummy",0, LocalDate.parse("9999-12-31")));

        repo.removeById(id);

        Assertions.assertThat(repo.listAll())
            .hasSize(2)
            .extracting(Movie::getTitle)
            .containsExactly("LOTR", "Titanic");
    }

    @Test
    void save2Similar_list2FoundByTitle(){
        repo.save(new Movie("LOTR",180, LocalDate.parse("2005-09-01")));
        repo.save(new Movie("LOTR",210, LocalDate.parse("2008-09-01")));
        repo.save(new Movie("Dummy",0, LocalDate.parse("9999-12-31")));

        Assertions.assertThat(repo.findMoviesByTitle("LOTR"))
            .hasSize(2)
            .extracting(Movie::getLength)
            .containsExactlyInAnyOrder(180, 210);
    }

}
