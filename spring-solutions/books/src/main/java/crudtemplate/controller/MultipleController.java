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

import crudtemplate.model.CreateMultipleCommand;
import crudtemplate.model.MultipleDto;
import crudtemplate.model.UpdateMultipleCommand;
import crudtemplate.service.MultipleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/multiples")
public class MultipleController {
    private MultipleService service;

    @GetMapping
    public List<MultipleDto> listMultiples(
            @RequestParam 
            Optional<String> part) {
        return service.getMultiples(part);
    }

    @GetMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MultipleDto> getMultipleById(
            @PathVariable("id") 
            long id) {
        return ResponseEntity.ok(service.getMultipleById(id));
    }

    @PostMapping
    public ResponseEntity<MultipleDto> createMultiple(
            @Valid 
            @RequestBody 
            CreateMultipleCommand command,
            UriComponentsBuilder uriComponentsBuilder ) {
        MultipleDto entityDto = service.createMultiple(command);
        URI locationUri = uriComponentsBuilder
            .path("/api/multiples/{id}")
            .buildAndExpand(entityDto.getId())
            .toUri();
        return ResponseEntity
            .created(locationUri)
            .body(entityDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<MultipleDto> modifyMultipleById(
            @PathVariable("id") 
            long id,
            @Valid 
            @RequestBody 
            UpdateMultipleCommand command) {
        MultipleDto entityDto = service.updateMultipleById(id, command);
        return ResponseEntity.ok(entityDto);
    }
    
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMultipleById(
            @PathVariable("id") 
            long singleId) {
        service.removeMultipleById(singleId);
    }

}