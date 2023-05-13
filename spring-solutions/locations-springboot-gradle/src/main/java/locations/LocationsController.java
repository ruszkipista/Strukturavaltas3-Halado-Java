package locations;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocationsController {
    @Autowired
    private LocationsService service;

    @GetMapping("/locations")
    public String listLocations() throws IOException {
        return service.getFavouriteLocations().toString();
    }

}