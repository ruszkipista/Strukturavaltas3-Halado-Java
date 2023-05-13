package org.training360.musicians;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AlbumTest {

    @Test
    void testCreateAlbum() {
        Album album = new Album("Kill 'em all", LocalDate.parse("1981-11-11"));

        assertEquals("Kill 'em all", album.getTitle());
        assertEquals(LocalDate.parse("1981-11-11"), album.getReleaseDate());
    }

}