package appointments.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import appointments.model.CaseTypeDto;
import appointments.service.CaseTypeService;

@RestController
public class CaseTypeController {
    @Autowired
    private CaseTypeService service;

    @GetMapping("/casetypes")
    public List<CaseTypeDto> listActivities() throws IOException {
        return service.getCaseTypes();
    }

}
