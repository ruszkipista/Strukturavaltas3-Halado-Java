package sportresults.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sportresults.dto.AthleteDto;
import sportresults.dto.CreateAthleteCommand;
import sportresults.dto.CreateResultCommand;
import sportresults.service.SportResultService;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/api/athletes")
public class AthleteController {

    private SportResultService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AthleteDto createAthlete(@Valid @RequestBody CreateAthleteCommand command) {
        return service.createAthlete(command);
    }

    @PostMapping("/{id}/results")
    @ResponseStatus(HttpStatus.CREATED)
    public AthleteDto createAthlete(@PathVariable Long id, @Valid @RequestBody CreateResultCommand command) {
        return service.addResultToAthlete(id, command);
    }

}
