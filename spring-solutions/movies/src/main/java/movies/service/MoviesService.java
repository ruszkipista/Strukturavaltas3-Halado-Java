package movies.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;
import movies.model.Movie;
import movies.model.MovieCreateCommand;
import movies.model.MovieDto;
import movies.model.MovieMapper;
import movies.model.MovieUpdateCommand;
import movies.model.RatingDto;
import movies.model.RatingMapper;
import movies.repository.MoviesRepository;

@Service
@AllArgsConstructor
public class MoviesService {
    private MoviesRepository repo;
    private MovieMapper movieMapper;
    private RatingMapper ratingMapper;

    public List<MovieDto> getMovies(Optional<String> titleFragment) {
        if (titleFragment.isEmpty()){
            return this.movieMapper.toDto(repo.getMovies());
        }
        return this.movieMapper.toDto(repo.getMoviesByTitleFragment(titleFragment.get()));
    }

    public MovieDto getMovieById(long movieId) {
        Optional<Movie> movie = repo.getMovieById(movieId);
        if (movie.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find movie " + movieId);
        }
        return this.movieMapper.toDto(movie.get());
    }
    
    public MovieDto createMovie(MovieCreateCommand createCommand) {
        Movie movie = repo.saveMovie(this.movieMapper.fromCreateCommand(createCommand));
        return this.movieMapper.toDto(movie);
    }

    public MovieDto updateMovieById(long movieId, MovieUpdateCommand updateCommand) {
        Optional<Movie> movie = repo.getMovieById(movieId);
        if (movie.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find movie " + movieId);
        }
        Movie movieUpdated = this.movieMapper.fromUpdateCommand(updateCommand, movie.get());
        movieUpdated = repo.updateMovieById(movieUpdated);
        return this.movieMapper.toDto(movieUpdated);
    }

    public void removeMovieById(long movieId) {
        repo.removeMovieById(movieId);
    }

    public List<RatingDto> getRatingsOfMovieById(long movieId) {
        Optional<Movie> movie = repo.getMovieById(movieId);
        if (movie.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find movie " + movieId);
        }
        return this.ratingMapper.toDto(movie.get().getRatings());
    }

    public List<RatingDto> addRatingToMovieById(long movieId, RatingDto createCommand) {
        Optional<Movie> movie = repo.getMovieById(movieId);
        if (movie.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find movie " + movieId);
        }
        movie.get().addRating(ratingMapper.fromDto(createCommand));
        return this.ratingMapper.toDto(movie.get().getRatings());
    }
}
