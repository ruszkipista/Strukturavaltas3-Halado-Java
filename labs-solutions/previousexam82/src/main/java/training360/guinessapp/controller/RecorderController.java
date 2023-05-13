package training360.guinessapp.controller;

import lombok.AllArgsConstructor;
import training360.guinessapp.dto.RecorderShortDto;
import training360.guinessapp.dto.RecorderCreateCommand;
import training360.guinessapp.dto.RecorderDto;
import training360.guinessapp.service.GuinnessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/recorders")
@AllArgsConstructor
public class RecorderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecorderController.class);

    private GuinnessService guinnessService;

    @PostMapping
    public ResponseEntity<RecorderDto> save(@Valid @RequestBody RecorderCreateCommand command) {
        LOGGER.info("HTTP POST /api/recorder, command: " + command);
        RecorderDto saved = guinnessService.saveRecorder(command);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RecorderShortDto>> listCertainRecorders() {
        LOGGER.info("HTTP GET /api/recorder");
        List<RecorderShortDto> recorderShortDtos = guinnessService.listCertainRecorders();
        return new ResponseEntity<>(recorderShortDtos, HttpStatus.OK);
    }
}
