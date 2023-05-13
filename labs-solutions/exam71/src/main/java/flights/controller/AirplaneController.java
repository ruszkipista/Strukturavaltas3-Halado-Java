package flights.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import flights.dtos.AirplaneDto;
import flights.dtos.CreateAirplaneCommand;
import flights.dtos.CreateRouteCommand;
import flights.dtos.RouteDto;
import flights.dtos.UpdateAirplaneCommand;
import flights.exception.AirplaneNotFoundException;
import flights.service.AirplaneService;
import flights.service.RouteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/airplanes")
public class AirplaneController {
    private AirplaneService airplaneService;
    private RouteService routeService;

    @GetMapping
    public List<AirplaneDto> listAirplanes(
            @RequestParam 
            Optional<String> ownerAirline) {
        return airplaneService.getAirplanes(ownerAirline);
    }

    @GetMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AirplaneDto> getAirplaneById(
            @PathVariable("id") 
            long id) {
        try {
            return ResponseEntity.ok(airplaneService.getAirplaneById(id));
        } catch (AirplaneNotFoundException nfe) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<AirplaneDto> createAirplane(
            @Valid 
            @RequestBody 
            CreateAirplaneCommand command,
            UriComponentsBuilder uriComponentsBuilder ) {
        AirplaneDto entityDto = airplaneService.createAirplane(command);
        URI locationUri = uriComponentsBuilder
            .path("/api/airplanes/{id}")
            .buildAndExpand(entityDto.getId())
            .toUri();
        return ResponseEntity
            .created(locationUri)
            .body(entityDto);
    }

    @PostMapping("{airplaneId}/routes")
    public ResponseEntity<RouteDto> addRouteToAirplane(
            @PathVariable("airplaneId") 
            long airplaneId,
            @Valid 
            @RequestBody 
            CreateRouteCommand command,
            UriComponentsBuilder uriComponentsBuilder ) {
        RouteDto entityDto = routeService.createRoute(airplaneId, command);           
        URI locationUri = uriComponentsBuilder
            .path("/api/airplanes/{id}/routes")
            .buildAndExpand(entityDto.getId())
            .toUri();
        return ResponseEntity
            .created(locationUri)
            .body(entityDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<AirplaneDto> modifyAirplaneById(
            @PathVariable("id") 
            long id,
            @Valid 
            @RequestBody 
            UpdateAirplaneCommand command) {
        AirplaneDto entityDto = airplaneService.updateAirplaneById(id, command);
        return ResponseEntity.ok(entityDto);
    }

    @PutMapping("{airplaneId}/routes/{routeId}")
    public ResponseEntity<AirplaneDto> cancelAirplaneRouteById(
            @PathVariable("airplaneId") 
            long airplaneId,
            @PathVariable("routeId") 
            long routeId) {
        AirplaneDto entityDto = routeService.cancelAirplaneRouteById(airplaneId, routeId);            
        return ResponseEntity.ok(entityDto);
    }
    
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAirplaneById(
            @PathVariable("id") 
            long airplaneId) {
        airplaneService.removeAirplaneById(airplaneId);
    }

}