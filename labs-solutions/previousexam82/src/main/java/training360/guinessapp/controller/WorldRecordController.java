package training360.guinessapp.controller;

import lombok.AllArgsConstructor;
import training360.guinessapp.dto.BeatWorldRecordCommand;
import training360.guinessapp.dto.BeatWorldRecordDto;
import training360.guinessapp.dto.WorldRecordCreateCommand;
import training360.guinessapp.dto.WorldRecordDto;
import training360.guinessapp.service.GuinnessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/worldrecords")
@AllArgsConstructor
public class WorldRecordController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorldRecordController.class);

    private GuinnessService guinnessService;

    @PostMapping
    public ResponseEntity<WorldRecordDto> save(@Valid @RequestBody WorldRecordCreateCommand command) {
        LOGGER.info("HTTP POST /api/worldrecord, command: " + command);
        WorldRecordDto saved = guinnessService.saveWorldRecord(command);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/beatrecords")
    private ResponseEntity<BeatWorldRecordDto> beatRecord(@PathVariable("id") Long id, @Valid @RequestBody BeatWorldRecordCommand command) {
        LOGGER.info("HTTP PUT /api/worldrecord/beatrecord, command: " + command);
        BeatWorldRecordDto beated = guinnessService.beatRecord(id, command);
        return new ResponseEntity<>(beated, HttpStatus.OK);
    }
}
