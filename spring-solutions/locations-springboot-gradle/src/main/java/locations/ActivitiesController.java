package locations;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActivitiesController {
    @Autowired
    private ActivitiesService service;

    @GetMapping("/activities")
    public String listActivities() throws IOException {
        return service.getFavouriteActivities();
    }

}