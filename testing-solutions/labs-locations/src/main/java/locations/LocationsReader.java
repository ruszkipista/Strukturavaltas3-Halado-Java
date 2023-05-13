package locations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LocationsReader {

    public List<Location> readFromCsvFile(Path file, LocationParser parser) {
        List<Location> locations = new ArrayList<>();
        List<String> lines;
        try {
            lines = Files.readAllLines(file);
        } catch (IOException e) {
            throw new IllegalArgumentException("File read failed");
        }
        for (String line : lines) {
            locations.add(parser.parse(line));
        }
        return locations;
    }

    public static List<Location> filterLocationsBeyondArcticCircle(List<Location> locations){
        return locations.stream()
            .filter(l -> l.getLatitude() > 66.57)
            .toList();
    }
}
