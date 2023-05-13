package locations;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class LocationsWriter {
    public void writeLocations(Path file, List<Location> locations){
        String csv;
        try (FileWriter writer = new FileWriter(file.toFile());){
            for (Location location : locations){
                csv = location.getName() + "," + location.getLatitude() + "," + location.getLongitude() + "\n";
                try {
                    writer.write(csv);
                } catch (IOException e) {
                    throw new IllegalStateException("Writing to file failed");
                }
            }
        } catch (IOException e){
            throw new IllegalStateException("Opening file to write failed");
        }

    }
}
