package locations;

import java.nio.file.Path;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LocationsController {

    @GetMapping("/locations")
    @ResponseBody
    public String listLocations() {
        return getLocations().toString();
    }

    private List<Location> getLocations() {
        return LocationsRepository.readFromCsvFile(Path.of("src/main/resources/locations.csv"));
    }
}