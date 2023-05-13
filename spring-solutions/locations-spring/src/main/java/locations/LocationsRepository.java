package locations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LocationsRepository {

    public static List<Location> readFromCsvFile(Path file) {
        List<Location> locations = new ArrayList<>();
        List<String> lines;
        try {
            lines = Files.readAllLines(file);
        } catch (IOException e) {
            throw new IllegalArgumentException("File read failed");
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
