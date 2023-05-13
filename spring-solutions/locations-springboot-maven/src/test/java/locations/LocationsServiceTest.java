package locations;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import locations.model.CoordinateLimit;
import locations.model.Location;
import locations.model.LocationDto;
import locations.model.LocationMapperImpl;
import locations.repository.LocationsRepository;
import locations.service.LocationsService;

@ExtendWith(MockitoExtension.class)
public class LocationsServiceTest {
    @Mock
    LocationsRepository repo;

    @InjectMocks
    LocationsService service = new LocationsService(repo, new LocationMapperImpl());

    List<Location> locations;

    @BeforeEach
    void init() {
        locations = List.of(
            new Location(1L, "Abc", 1d, 1d),
            new Location(2L, "Bcd", 1d, 2d),
            new Location(3L, "Cde", 2d, 1d),
            new Location(4L, "BcX", 2d, 2d)
            );
    }

    @Test
    void getFavouriteLocations_returnsList() throws IOException {
        Mockito.when(repo.findAll()).thenReturn(locations);

        Assertions.assertThat(service.getLocations(Optional.empty()))
            .hasSize(4)
            .extracting(LocationDto::getId, LocationDto::getName)
            .containsExactlyInAnyOrder(
                Assertions.tuple(1L, "Abc"), 
                Assertions.tuple(2L, "Bcd"),
                Assertions.tuple(3L, "Cde"),
                Assertions.tuple(4L, "BcX")
            );

        verify(repo, times(1)).findAll();
    }

    @Test
    void getFavouriteLocationsStartingWithBc_returns2ElementList() throws IOException {
        locations = List.of(
            new Location(2L, "Bcd", 1d, 2d),
            new Location(4L, "BcX", 2d, 2d)
        );        
        Mockito.when(repo.findAllByPrefix(anyString())).thenReturn(locations);

        Assertions.assertThat(service.getLocations(Optional.of("Bc")))
            .hasSize(2)
            .extracting(LocationDto::getId, LocationDto::getName)
            .containsExactlyInAnyOrder(
                Assertions.tuple(2L, "Bcd"),
                Assertions.tuple(4L, "BcX")
            );

        verify(repo, times(1)).findAllByPrefix("Bc");
    }

    @Test
    void getLocationsWithinLimits_returns4ElementList() throws IOException {
        Mockito.when(repo.findAll()).thenReturn(locations);

        CoordinateLimit limits = new CoordinateLimit(0, 2, 0, 2);

        Assertions.assertThat(service.getLocationsWithinLimits(limits))
            .hasSize(4)
            .extracting(LocationDto::getId)
            .containsExactlyInAnyOrder(1L,2L,3L,4L);

        verify(repo, times(1)).findAll();
    }

    @Test
    void getLocationsWithinLimits_returns1ElementList() throws IOException {
        Mockito.when(repo.findAll()).thenReturn(locations);

        CoordinateLimit limits = new CoordinateLimit(0, 1, 0, 1);

        Assertions.assertThat(service.getLocationsWithinLimits(limits))
            .hasSize(1)
            .extracting(LocationDto::getName)
            .containsExactlyInAnyOrder("Abc");

        verify(repo, times(1)).findAll();
    }
    
}
