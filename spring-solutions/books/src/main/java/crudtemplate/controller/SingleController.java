package crudtemplate.controller;

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

import crudtemplate.dto.CreateSingleCommand;
import crudtemplate.dto.SingleDto;
import crudtemplate.dto.UpdateSingleCommand;
import crudtemplate.service.SingleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/singles")
public class SingleController {
    private SingleService service;

    @GetMapping
    public List<SingleDto> listSingles(
            @RequestParam 
            Optional<String> part) {
        return service.getSingles(part);
    }

    @GetMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<SingleDto> getSingleById(
            @PathVariable("id") 
            long id) {
        return ResponseEntity.ok(service.getSingleById(id));
    }

    @PostMapping
    public ResponseEntity<SingleDto> createSingle(
            @Valid 
            @RequestBody 
            CreateSingleCommand command,
            UriComponentsBuilder uriComponentsBuilder ) {
        SingleDto entityDto = service.createSingle(command);
        URI locationUri = uriComponentsBuilder
            .path("/api/singles/{id}")
            .buildAndExpand(entityDto.getId())
            .toUri();
        return ResponseEntity
            .created(locationUri)
            .body(entityDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<SingleDto> modifySingleById(
            @PathVariable("id") 
            long id,
            @Valid 
            @RequestBody 
            UpdateSingleCommand command) {
        SingleDto entityDto = service.updateSingleById(id, command);
        return ResponseEntity.ok(entityDto);
    }
    
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSingleById(
            @PathVariable("id") 
            long singleId) {
        service.removeSingleById(singleId);
    }

}