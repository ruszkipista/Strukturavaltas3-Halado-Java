package locations;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {

    @Mock
    LocationsRepository repo;

    @InjectMocks
    LocationsService service;

    Location locBud = new Location("Budapest", 47.49791, 19.04023);
    Location locDeb = new Location("Debrecen", 47.52997, 21.63916);

    @Test
    void testCalculateDistanceNoStubs_FirstNotFound(){
        Optional<Double> distance = service.calculateDistance(locDeb.getName(), locBud.getName());

        assertEquals(true, distance.isEmpty());
        verify(repo).findByName(locDeb.getName());
        verify(repo, times(1)).findByName(anyString());
    }

    @Test
    void testCalculateDistance_FirstNotFound(){
        when(repo.findByName(anyString()))
            .thenAnswer(invocation ->{
                if ("Debrecen".equals(invocation.getArgument(0))){
                    return Optional.of(locDeb);
                } else {
                    return Optional.empty();
                }
            });

        Optional<Double> distance = service.calculateDistance("NoName", locDeb.getName());

        assertEquals(true, distance.isEmpty());
        verify(repo).findByName("NoName");
        verify(repo, times(1)).findByName(anyString());
    }

    @Test
    void testCalculateDistance_SecondNotFound(){
        when(repo.findByName(anyString()))
            .thenAnswer(invocation ->{
                if ("Debrecen".equals(invocation.getArgument(0))){
                    return Optional.of(locDeb);
                } else {
                    return Optional.empty();
                }
            });

        Optional<Double> distance = service.calculateDistance(locDeb.getName(), "NoName");

        assertEquals(true, distance.isEmpty());
        verify(repo).findByName(locDeb.getName());
        verify(repo).findByName("NoName");
        verify(repo, times(2)).findByName(anyString());
    }

    @Test
    void testCalculateDistanceTwoDifferent_givesDistance(){
        when(repo.findByName(locDeb.getName())).thenReturn(Optional.of(locDeb));
        when(repo.findByName(locBud.getName())).thenReturn(Optional.of(locBud));

        Optional<Double> distance = service.calculateDistance(locDeb.getName(), locBud.getName());

        assertEquals(195.2, distance.get(), 0.01);
        verify(repo).findByName(locDeb.getName());
        verify(repo).findByName(locBud.getName());
        verify(repo, times(2)).findByName(anyString());
    }

    @Test
    void testCalculateDistanceSame_gives0(){
        when(repo.findByName(locDeb.getName())).thenReturn(Optional.of(locDeb));

        Optional<Double> distance = service.calculateDistance(locDeb.getName(), locDeb.getName());

        assertEquals(0.0, distance.get());
        verify(repo, times(2)).findByName(locDeb.getName());
    }

    @Test
    void testIsOnNorth_Yes(){
        when(repo.findLatitudeByName(anyString())).thenReturn(Optional.of(42.0));

        assertEquals(true, service.isOnNorthernHemisphere("Oslo"));
        verify(repo).findLatitudeByName("Oslo");
    }

    @Test
    void testIsOnNorth_No(){
        when(repo.findLatitudeByName(anyString())).thenReturn(Optional.of(-42.0));

        assertEquals(false, service.isOnNorthernHemisphere("Sydney"));
        verify(repo).findLatitudeByName("Sydney");
    }

    @Test
    void testIsOnNorth_NoSuchPlace(){
        when(repo.findLatitudeByName(anyString())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.isOnNorthernHemisphere("NoName"));
        verify(repo).findLatitudeByName("NoName");
    }
}
