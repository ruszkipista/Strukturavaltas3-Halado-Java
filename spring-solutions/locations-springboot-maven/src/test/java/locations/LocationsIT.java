package locations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponentsBuilder;

import locations.controller.LocationsController;
import locations.model.CoordinateLimit;
import locations.model.Location;
import locations.model.LocationCreateCommand;
import locations.model.LocationDto;
import locations.model.LocationUpdateCommand;
import locations.repository.LocationsRepository;

@SpringBootTest
public class LocationsIT {
    @Autowired
    LocationsController controller;

    @Autowired
    LocationsRepository repo;

    List<Location> locations;

    @BeforeEach
    void init(){
        repo.deleteAll();
        locations = repo.saveAllAndFlush(List.of(
            new Location("Aaa",0,0),
            new Location("Bbb",0,1),
            new Location("Ccc",1,0),
            new Location("Ddd",1,1),
            new Location("Eee",-1,1)
        ));
    }

	@Test
	void contextLoads() {
	}

    @Test
    void getLocations_getList() throws IOException{
        Assertions.assertThat(controller.listLocations(Optional.empty()))
            .hasSize(5)
            .extracting(LocationDto::getName)
            .containsExactlyInAnyOrder("Aaa", "Bbb", "Ccc", "Ddd", "Eee");
    }

    @Test
    void getLocationsByName() throws IOException {
        Assertions.assertThat(controller.listLocations(Optional.of("Bbb")))
            .hasSize(1)
            .extracting(LocationDto::getName)
            .contains("Bbb");
    }

    @Test
    void getLocationsByName_notFound() throws IOException {
        Assertions.assertThat(controller.listLocations(Optional.of("Xxx")))
            .hasSize(0);
    }

    @Test
    void getLocationsWithinLimit_allReturned() throws IOException {
        CoordinateLimit limit = new CoordinateLimit(-1, 1, -1, 1);
        assertEquals("[Aaa, Bbb, Ccc, Ddd, Eee]", controller.listLocationsWithinLimits(limit));
    }

    @Test
    void getLocationsWithinLimit_oneReturned() throws IOException {
        CoordinateLimit limit = new CoordinateLimit(0, 0, 0, 0);
        assertEquals("[Aaa]", 
                    controller.listLocationsWithinLimits(limit));
    }

    @Test
    void createLocation_createdWithId() throws IOException {      
        var command = new LocationCreateCommand("Zzz", -1.0, 1.0);
        var response = controller.createLocation(command, UriComponentsBuilder.newInstance());

        Assertions.assertThat(List.of(response.getBody()))
            .extracting(LocationDto::getName)
            .contains("Zzz");

        assertTrue(response.getHeaders().getLocation().toString().startsWith("/locations/", 0));
    }

    @Test
    void updateLocationById_modifiedName() throws IOException {
        Long id = locations.get(2).getId();

        var locationUpdateCommand = new LocationUpdateCommand("Yyy", -1.0, 1.0);
        controller.modifyLocationById(id, locationUpdateCommand);

        LocationDto location = controller.getLocationById(id).getBody();

        Assertions.assertThat(List.of(location))
            .extracting(LocationDto::getId, LocationDto::getName)
            .contains(Assertions.tuple(id, "Yyy"));
    }

    @Test
    void deleteLocationById_deleted() {
        Long id = locations.get(0).getId();
        controller.deleteLocationById(id);

        assertEquals(HttpStatus.NOT_FOUND, controller.getLocationById(id).getStatusCode());
    }
}
