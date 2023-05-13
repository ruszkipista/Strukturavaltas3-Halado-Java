package locations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class LocationsWriterTest {
    List<Location> locations = new ArrayList<>();

    @TempDir
    Path path;

    @BeforeEach
    void intit(){
        locations.add(new Location("Budapest", 47.49791, 19.04023));
        locations.add(new Location("Debrecen", 47.52997, 21.63916));
        locations.add(new Location("Afrika", 0.0, 20.0));
    }

    @Test
    void testFileWrite() throws IOException{
        path = path.resolve("locations.txt");
        new LocationsWriter().writeLocations(path, locations);

        List<String> lines = Files.readAllLines(path);
        assertEquals("Debrecen,47.52997,21.63916", lines.get(1));
    }
}
