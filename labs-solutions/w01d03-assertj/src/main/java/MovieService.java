import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class MovieService {
    private final static LocalDate MIN_DATE = LocalDate.of(1911, 1, 1);
    private List<Movie> movies = new ArrayList<>();

    public void addMovie(Movie movie){
        checkProductionDate(movie);
        this.movies.add(movie);
    }

    private void checkProductionDate(Movie movie) {
        if (movie.getProductionDate().isBefore(MIN_DATE)){
            throw new IllegalArgumentException("Movie mustn't be produced before " + MIN_DATE);
        }
    }

    public List<Movie> getMovies() {
        return this.movies;
    }

    public Optional<Movie> findByTitle(String title){
        return movies.stream()
                .filter(m->m.getTitle().equals(title))
                .findAny();
    }

    public Map<MovieGenre, List<Movie>> groupMoviesByGenre(){
        return this.movies.stream()
                .collect(Collectors.groupingBy(Movie::getGenre));
    }

    public List<Movie> findAfterDate(LocalDate minDate) {
        return movies.stream()
                .filter(m->m.getProductionDate().isAfter(minDate))
                .toList();
    }
}
