package locations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

@Repository
public class LocationsRepository {
    Path path;

    public LocationsRepository(Path path){
        this.path = path;
    }

    public LocationsRepository() throws IOException{
        this.path = new ClassPathResource("locations.csv").getFile().toPath();
    }
    
    public List<Location> getFavouriteLocations() {
        return readFromCsvFile(path);
    }

    private List<Location> readFromCsvFile(Path path) {
        List<Location> locations = new ArrayList<>();
        List<String> lines;
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            throw new IllegalArgumentException("File read failed", e);
        }
        for (String line : lines) {
            locations.add(parse(line));
        }
        return locations;
    }

    public static Location parse(String text) {
        String[] fields = text.split(",");
        String name = fields[0];
        double latitude = Double.parseDouble(fields[1]);
        double longitude = Double.parseDouble(fields[2]);
        return new Location(name, latitude, longitude);
    }
}
