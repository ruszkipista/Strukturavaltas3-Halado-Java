package movies.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.AllArgsConstructor;
import movies.model.MovieCreateCommand;
import movies.model.MovieDto;
import movies.model.MovieUpdateCommand;
import movies.model.RatingDto;
import movies.service.MoviesService;

@RestController
@RequestMapping("/api/movies")
@AllArgsConstructor
public class MoviesController {
    private MoviesService service;

    // accept optional URL parameter ...?titleFragment=Abc
    @GetMapping
    public List<MovieDto> listMovies(@RequestParam Optional<String> titleFragment) {
        return service.getMovies(titleFragment);
    }

    // expect path variable .../42
    @GetMapping("/{id}")
    public MovieDto getMovieById(@PathVariable("id") long movieId) {
        return service.getMovieById(movieId);
    }

    // POST, attributes in a JSON structure sent in the body of the request
    @PostMapping
    public ResponseEntity<MovieDto> createMovie(@RequestBody MovieCreateCommand createCommand,
                                                UriComponentsBuilder uriComponentsBuilder ) {
        MovieDto movieDto = service.createMovie(createCommand);
        URI movieUri = uriComponentsBuilder
            .path("/api/movies/{id}")
            .buildAndExpand(movieDto.getId())
            .toUri();
        return ResponseEntity
            .created(movieUri)
            .body(movieDto);
    }

    // PUT expect path variable .../42 and only attributes which are to be overwritten
    @PutMapping("/{id}")
    public MovieDto modifyMovie(@PathVariable("id") long movieId,
                                      @RequestBody MovieUpdateCommand updateCommand) {
        return service.updateMovieById(movieId, updateCommand);
    }
    
    // DELETE expect path variable .../42
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovieById(@PathVariable("id") long movieId) {
        service.removeMovieById(movieId);
    }

    @GetMapping("/{id}/ratings")
    public List<RatingDto> getRatingsOfMovieById(@PathVariable("id") long movieId) {
        return service.getRatingsOfMovieById(movieId);
    }

    @PostMapping("/{id}/ratings")
    public ResponseEntity<List<RatingDto>> addRatingToMovieById(
                @PathVariable("id") long movieId,
                @RequestBody RatingDto createCommand,
                UriComponentsBuilder uriComponentsBuilder ) {
        List<RatingDto> ratings = service.addRatingToMovieById(movieId, createCommand);
        URI movieUri = uriComponentsBuilder
            .path("/api/movies/{id}/ratings")
            .buildAndExpand(movieId)
            .toUri();
        return ResponseEntity
            .created(movieUri)
            .body(ratings);
    }
}