package jpa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Bird business logic test")
@ExtendWith(MockitoExtension.class)
class BirdServiceTest {

    @Mock
    BirdDao birdDao;

    @InjectMocks
    BirdService birdService;

    @Test
    @DisplayName("Test bird species statistics")
    void testGetBirdStatistics() {
        Map<BirdSpecies, Integer> expected = Map.of(
                BirdSpecies.SWALLOW, 1,
                BirdSpecies.BLACKBIRD, 2
        );
        when(birdDao.listBirds()).thenReturn(List.of(
                new Bird(BirdSpecies.BLACKBIRD),
                new Bird(BirdSpecies.SWALLOW),
                new Bird(BirdSpecies.BLACKBIRD)
        ));

        assertEquals(expected, birdService.getBirdStatistics());
        verify(birdDao).listBirds();
    }
}
