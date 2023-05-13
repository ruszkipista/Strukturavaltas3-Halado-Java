package locations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import locations.controller.LocationsController;
import locations.model.LocationCreateCommand;
import locations.model.LocationDto;
import locations.model.LocationUpdateCommand;
import locations.service.LocationNotFoundException;
import locations.service.LocationsService;

@ExtendWith(MockitoExtension.class)
public class LocationsControllerTest {

    @Mock
    LocationsService service;

    @InjectMocks
    LocationsController controller;

    @Mock
    UriComponentsBuilder builder;
    @Mock
    UriComponents components;

    List<LocationDto> locations;

    @BeforeEach
    void intit(){
        locations = List.of(
            new LocationDto(1L, "A"),
            new LocationDto(2L, "B")
        );
    }
 
    @Test
    void listAllLocations_listed() throws IOException{
        Mockito.when(service.getLocations(Optional.empty())).thenReturn(locations);

        Assertions.assertEquals(locations, controller.listLocations(Optional.empty()));

        Mockito.verify(service).getLocations(any());
    }

    @Test
    public void testCreateLocation() {
        // create a LocationCreateCommand instance
        LocationCreateCommand command = new LocationCreateCommand("test location", 1d, 3d);       
        // create a LocationDto instance
        LocationDto locationDto = new LocationDto(42L, "test location");

        // mock the service method to return the LocationDto instance
        when(service.createLocation(command)).thenReturn(locationDto);

        URI expectedLocation = URI.create("http://localhost/locations/42");

        when(builder.path(any())).thenReturn(builder);
        when(builder.buildAndExpand(anyLong())).thenReturn(components);
        when(components.toUri()).thenReturn(expectedLocation);
              
        // call the createLocation method on the controller
        var response = controller.createLocation(command, builder);
        
        // verify that the service method was called with the command argument
        verify(service).createLocation(command);
        
        // verify that the response status is CREATED
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        
        // verify that the LocationDto instance is returned in the response body
        assertEquals(locationDto, response.getBody());
        
        // verify that the response header contains a Location header with the URI of the new location
        assertEquals(expectedLocation, response.getHeaders().getLocation());
    }

    @Test
    public void getById_locationNotFound_404() {
        doThrow(new LocationNotFoundException("NotFound")).when(service).getLocationById(anyLong()); 

        assertEquals(HttpStatus.NOT_FOUND, controller.getLocationById(42L).getStatusCode());
    }

    @Test
    public void deleteById_locationNotFound_404() {
        doThrow(new LocationNotFoundException("NotFound")).when(service).removeLocationById(anyLong()); 

        assertThrows(LocationNotFoundException.class, ()->controller.deleteLocationById(42L));
    }

    @Test
    public void updateById_locationNotFound_404() {
        doThrow(LocationNotFoundException.class).when(service).updateLocationById(anyLong(), any()); 

        assertThrows(LocationNotFoundException.class, 
                    ()->controller.modifyLocationById(42L, new LocationUpdateCommand()));
    }
}
