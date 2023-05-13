package org.training360.musicians;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

class BandsMusiciansRepositoryTest {

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");

    BandsMusiciansRepository repository = new BandsMusiciansRepository(factory);

    @AfterEach
    void close() {
        factory.close();
    }

    @Test
    void testSaveAndFindBand() {
        Band band = repository.saveBand(new Band("Metallica", Genre.ROCK));
        Band found = repository.findBandById(band.getId());

        assertEquals("Metallica", found.getBandName());
        assertEquals(Genre.ROCK, found.getGenre());
    }

    @Test
    void testSaveBandWithMusician() {
        Musician musician = new Musician("James Hetfield", LocalDate.parse("1963-08-03"), "guitar-vocal");
        Band band = new Band("Metallica", Genre.ROCK);
        band.addMusician(musician);
        repository.saveBand(band);

        assertNotNull(musician.getId());
    }

    @Test
    void testUpdateBandWithMusician() {
        Band band = new Band("Metallica", Genre.ROCK);
        Musician musician = new Musician("James Hetfield", LocalDate.parse("1963-08-03"), "guitar-vocal");

        repository.saveBand(band);
        repository.updateBandWithMusician(band.getId(), musician);

        assertNotNull(musician.getId());
    }

    @Test
    void testFindBandWithAllMusicians() {
        Band other = new Band("Iron Maiden", Genre.ROCK);
        Band band = new Band("Metallica", Genre.ROCK);
        Musician musician = new Musician("James Hetfield", LocalDate.parse("1963-08-03"), "guitar-vocal");
        Musician musician2 = new Musician("Kirk Hammet", LocalDate.parse("1963-01-14"), "guitar");
        Musician musician3 = new Musician("Lars Ulrich", LocalDate.parse("1962-11-01"), "drums");
        Musician musician4 = new Musician("Dave Murray", LocalDate.parse("1950-11-01"), "guitar");
        band.addMusician(musician);
        band.addMusician(musician3);
        other.addMusician(musician4);

        repository.saveBand(other);
        repository.saveBand(band);
        repository.updateBandWithMusician(band.getId(), musician2);
        Band found = repository.findBandWithAllMusicians(band.getId());

        assertEquals(3, found.getMusicians().size());

        assertTrue(found.getMusicians().stream().map(Musician::getName).toList()
                .containsAll(List.of("Lars Ulrich", "Kirk Hammet", "James Hetfield")));
    }

    @Test
    void testDeleteMusicianById() {
        Band band = new Band("Metallica", Genre.ROCK);
        Musician musician = new Musician("James Hetfield", LocalDate.parse("1963-08-03"), "guitar-vocal");
        Musician musician2 = new Musician("Kirk Hammet", LocalDate.parse("1963-01-14"), "guitar");
        Musician musician3 = new Musician("Lars Ulrich", LocalDate.parse("1962-11-01"), "drums");
        band.addMusician(musician);
        band.addMusician(musician2);
        band.addMusician(musician3);
        repository.saveBand(band);

        repository.deleteMusicianById(musician3.getId());

        Band found = repository.findBandWithAllMusicians(band.getId());

        assertEquals(2, found.getMusicians().size());

        assertTrue(found.getMusicians().stream().map(Musician::getName).toList()
                .containsAll(List.of("Kirk Hammet", "James Hetfield")));
    }

    @Test
    void testFindBandWithAlbumsAfter() {
        Band ironMaiden = new Band("Iron Maiden", Genre.ROCK);
        Band metallica = new Band("Metallica", Genre.ROCK);
        Band gunsNRoses = new Band("Guns n Roses", Genre.ROCK);

        ironMaiden.addAlbum(new Album("Piece of Mind", LocalDate.parse("1984-03-11")));
        ironMaiden.addAlbum(new Album("Book of Souls", LocalDate.parse("2016-04-11")));
        ironMaiden.addAlbum(new Album("Iron Maiden", LocalDate.parse("1979-05-10")));

        repository.saveBand(ironMaiden);
        repository.saveBand(metallica);
        repository.saveBand(gunsNRoses);

        Band found = repository.findBandWithAlbumsAfter(LocalDate.parse("1984-03-10"));

        assertEquals(2, found.getDiscography().size());
        assertTrue(found.getDiscography().stream().map(Album::getTitle).toList()
                .containsAll(List.of("Book of Souls", "Piece of Mind")));
    }

    @Test
    void testFindBandWithMusicianName() {
        Band band1 = new Band("Band1", Genre.ROCK);
        Band band2 = new Band("Band2", Genre.POP);
        Band band3 = new Band("Band3", Genre.HIP_HOP);

        band1.addMusician(new Musician("John Doe", LocalDate.parse("1992-11-11"), "guitar"));
        band1.addMusician(new Musician("John Other", LocalDate.parse("1991-11-11"), "bass"));
        band2.addMusician(new Musician("Jack Other", LocalDate.parse("1990-10-11"), "bass"));
        band2.addMusician(new Musician("Jill Other", LocalDate.parse("1991-02-15"), "bass"));
        band3.addMusician(new Musician("John Other", LocalDate.parse("1992-01-01"), "drums"));
        repository.saveBand(band1);
        repository.saveBand(band2);
        repository.saveBand(band3);

        List<Band> found = repository.findBandsWithMusicianName("John");

        assertEquals(2, found.size());
        assertTrue(found.stream().map(Band::getBandName).toList()
                .containsAll(List.of("Band1", "Band3")));

        found = repository.findBandsWithMusicianName("Jill");
        assertEquals(1, found.size());
    }
}