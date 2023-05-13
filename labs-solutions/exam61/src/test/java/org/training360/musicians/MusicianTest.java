package org.training360.musicians;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MusicianTest {

    @Test
    void testCreateMusician() {
        Musician musician = new Musician("James Hetfield", LocalDate.parse("1963-08-03"), "guitar-vocal");

        assertEquals("James Hetfield", musician.getName());
        assertEquals(LocalDate.parse("1963-08-03"), musician.getDateOfBirth());
        assertEquals("guitar-vocal", musician.getInstrument());
    }

}