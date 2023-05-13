import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MovieServiceTest {

    MovieService movieService = new MovieService();

    @BeforeEach
    void init() {
        movieService.addMovie(new Movie("Titanic", LocalDate.of(1997, 12, 12), 120, MovieGenre.DRAMA));
        movieService.addMovie(new Movie("Jurassic Park", LocalDate.of(1993, 12, 12), 119, MovieGenre.ACTION));
        movieService.addMovie(new Movie("Spiderman", LocalDate.of(2022, 12, 12), 134, MovieGenre.DRAMA));
        movieService.addMovie(new Movie("Dumb and Dumber", LocalDate.of(1994, 12, 12), 92, MovieGenre.COMEDY));
    }

    @Test
    void testAddMovie() {
        MovieService movieService = new MovieService();

        movieService.addMovie(new Movie("Forest Gump", LocalDate.of(1994, 12, 12), 121, MovieGenre.DRAMA));

        assertThat(movieService.getMovies())
                .hasSize(1)
                .extracting(Movie::getTitle)
                .contains("Forest Gump");
    }

    @Test
    void testAddMovieWrongDate() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> movieService
                        .addMovie(new Movie("Forest Gump", LocalDate.of(1910, 12, 31), 121, MovieGenre.DRAMA)))
                .withMessage("Movie mustn't be produced before 1911-01-01");
    }

    @Test
    void testFindByTitle() {
        Movie movie = movieService.findByTitle("Spiderman").get();

        assertThat(movie.getTitle()).isEqualTo("Spiderman");
        assertThat(movie.getLengthInMinutes()).isEqualTo(134);

    }

    @Test
    void testFindMoviesAfterDate() {
        List<Movie> result = movieService.findAfterDate(LocalDate.of(1994, 12, 13));

        assertThat(result)
                .hasSize(2)
                .extracting(Movie::getTitle)
                .containsExactly("Titanic", "Spiderman");
    }
}
