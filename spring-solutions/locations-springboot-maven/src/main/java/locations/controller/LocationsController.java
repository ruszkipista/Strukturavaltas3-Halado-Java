package locations.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;
import locations.model.CoordinateLimit;
import locations.model.LocationCreateCommand;
import locations.model.LocationDto;
import locations.model.LocationUpdateCommand;
import locations.service.LocationNotFoundException;
import locations.service.LocationsService;
import lombok.AllArgsConstructor;
import lombok.Data;

@RestController
@AllArgsConstructor
public class LocationsController {
    private LocationsService service;

    // accept optional URL parameter ...?name=Abc
    @GetMapping("/locations")
    public List<LocationDto> listLocations(@RequestParam Optional<String> name) {
        return service.getLocations(name);
    }

    // mandatory URL parameters packed into object of CoordinateLimit
    @GetMapping("/locations-within-limit")
    public String listLocationsWithinLimits(CoordinateLimit limit) {
        return service.getLocationsWithinLimits(limit).stream()
            .map(l->l.getName())
            .sorted()
            .toList()
            .toString();
    }

    // expect path variable .../42
    @GetMapping(value = "/locations/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<LocationDto> getLocationById(@PathVariable("id") long locationId) {
        try {
            return ResponseEntity.ok(service.getLocationById(locationId));
        } catch (LocationNotFoundException iae) {
            return ResponseEntity.notFound().build();
        }

    }

    // POST location attributes in a JSON structure sent in the body of the request
    @PostMapping(value="/locations")
    public ResponseEntity<LocationDto> createLocation(@Valid @RequestBody LocationCreateCommand command,
                                                        UriComponentsBuilder uriComponentsBuilder ) {
        LocationDto locationDto = service.createLocation(command);
        URI locationUri = uriComponentsBuilder
            .path("/locations/{id}")
            .buildAndExpand(locationDto.getId())
            .toUri();
        return ResponseEntity
            .created(locationUri)
            .body(locationDto);
    }

    // PUT expect path variable .../42 and only attributes which are to be overwritten
    @PutMapping("/locations/{id}")
    public ResponseEntity<LocationDto> modifyLocationById(@PathVariable("id") long locationId,
                                      @Valid @RequestBody LocationUpdateCommand command) {
        LocationDto locationDto = service.updateLocationById(locationId, command);
        return ResponseEntity.ok(locationDto);        
    }
    
    // DELETE expect path variable .../42
    @DeleteMapping("/locations/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLocationById(@PathVariable("id") long locationId) {
        service.removeLocationById(locationId);
    }

    @ExceptionHandler(LocationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ProblemDetail resourceNotFoundNew(Exception e){
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problem.setType(URI.create("locations/location-not-found"));
        return problem;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ProblemDetail requestNotValid(MethodArgumentNotValidException e){
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        problem.setType(URI.create("locations/request-not-valid"));
        problem.setProperty("violations", e.getBindingResult().getFieldErrors().stream().map((FieldError fe) -> new Violation(fe.getField(), fe.getDefaultMessage())).collect(Collectors.toList()));
        return problem;
    }

    @Data
    @AllArgsConstructor
    private class Violation{
        String field;
        String message;
    }
}