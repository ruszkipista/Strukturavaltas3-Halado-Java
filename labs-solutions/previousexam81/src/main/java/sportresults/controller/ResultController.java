package sportresults.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sportresults.dto.ResultDto;
import sportresults.dto.UpdateMeasureCommand;
import sportresults.service.SportResultService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/results")

public class ResultController {

    private SportResultService service;

    @PutMapping("/{id}")
    public ResultDto updateResult(@PathVariable Long id, @Valid @RequestBody UpdateMeasureCommand command) {
        return service.updateResult(id, command);
    }

    @GetMapping
    public List<ResultDto> getResults (@RequestParam Optional<String> sportType) {
        return service.getResults(sportType);
    }

}
