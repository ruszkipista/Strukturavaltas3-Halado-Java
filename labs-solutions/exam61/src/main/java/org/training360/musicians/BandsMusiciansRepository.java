package org.training360.musicians;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class BandsMusiciansRepository {
    private EntityManagerFactory factory;

    public BandsMusiciansRepository(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public Band saveBand(Band band) {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(band);
            em.getTransaction().commit();
            return band;
        } finally {
            em.close();
        }
    }
 
    public Band findBandById(Long bandId) {
        EntityManager em = factory.createEntityManager();
        try {
            return em.find(Band.class, bandId);
        } finally {
            em.close();
        }
    }

    public void updateBandWithMusician(Long bandId, Musician musician) {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            Band band = em.getReference(Band.class, bandId);
            band.addMusician(musician);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Band findBandWithAllMusicians(Long bandId){
        EntityManager em = factory.createEntityManager();
        try {
            return em.createQuery("SELECT b FROM Band b LEFT JOIN FETCH b.musicians m WHERE b.id = :bandId",
                          Band.class)
                .setParameter("bandId", bandId)
                .getSingleResult();
        } finally {
            em.close();
        }
    }

    public void deleteMusicianById(Long musicianId) {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            Musician musician = em.getReference(Musician.class, musicianId);
            em.remove(musician);
            em.getTransaction().commit();
        } finally {
            em.close();
        }    
    }

    public Band findBandWithAlbumsAfter(LocalDate afterThis) {
        EntityManager em = factory.createEntityManager();
        try {
            return em.createQuery("SELECT b FROM Band b LEFT JOIN FETCH b.albums a WHERE a.releaseDate > :afterThis",
                          Band.class)
                .setParameter("afterThis", afterThis)
                .getSingleResult();
        } finally {
            em.close();
        }
    }

    public List<Band> findBandsWithMusicianName(String musicianName) {
        EntityManager em = factory.createEntityManager();
        try {
            return em.createQuery("SELECT DISTINCT b FROM Band b LEFT JOIN FETCH b.musicians m WHERE LOCATE(:musicianName, m.name) > 0",
                          Band.class)
                .setParameter("musicianName", musicianName)
                .getResultList();
        } finally {
            em.close();
        }
    }

}
