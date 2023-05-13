package locations.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import locations.service.ActivitiesService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ActivitiesController {
    private ActivitiesService service;

    @GetMapping("/activities")
    public String listActivities() throws IOException {
        return service.getFavouriteActivities();
    }

}