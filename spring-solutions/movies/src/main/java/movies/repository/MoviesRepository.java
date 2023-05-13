package movies.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import movies.model.Movie;

@Repository
public class MoviesRepository {
    private List<Movie> movies = new ArrayList<>();
    private AtomicLong lastId =  new AtomicLong();
    
    public MoviesRepository() {
    }

    public List<Movie> getMovies() {
        return this.movies;
    }

    public Optional<Movie> getMovieById(long movieId) {
        return this.movies.stream()
                .filter(l -> l.getId() == movieId)
                .findAny();
    }

    public Movie saveMovie(Movie movie) {
        movie.setId(this.lastId.incrementAndGet());
        this.movies.add(movie);
        return movie;
    }

    public Movie updateMovieById(Movie movie) {
        for(int i=0; i<this.movies.size(); i++){
            if (this.movies.get(i).getId().equals(movie.getId())) {
                this.movies.set(i, movie);
                return movie;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find movie to update" + movie.getId());
    }

    public void removeMovieById(long movieId) {
        for(int i=0; i<this.movies.size(); i++){
            if (this.movies.get(i).getId().equals(movieId)) {
                this.movies.remove(i);
                return;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find movie to delete " + movieId);
    }

    public List<Movie> getMoviesByTitleFragment(String titleFragment) {
        return this.movies.stream()
                .filter(m -> m.getTitle().toLowerCase().contains(titleFragment.toLowerCase()))
                .toList();
    }
}
