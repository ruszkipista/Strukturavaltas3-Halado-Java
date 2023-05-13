package org.training360.musicians;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BandTest {

    @Test
    void testCreateBand() {
        Band band = new Band("Metallica", Genre.ROCK);

        assertEquals("Metallica", band.getBandName());
        assertEquals(Genre.ROCK, band.getGenre());
    }

    @Test
    void testAddAlbum() {
        Band band = new Band("Metallica", Genre.ROCK);
        band.addAlbum(new Album("Kill 'em all", LocalDate.parse("1981-11-11")));

        assertEquals(1, band.getDiscography().size());
    }

}