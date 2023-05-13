package jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class BirdDao {

    private EntityManagerFactory factory;

    public BirdDao(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public void saveBird(Bird bird) {
        EntityManager entityManager = factory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(bird);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public List<Bird> listBirds() {
        EntityManager entityManager = factory.createEntityManager();
        try {
            return entityManager.createQuery("select b from Bird b", Bird.class)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    public List<Bird> listBirdsSpeciesGiven(BirdSpecies species) {
        EntityManager entityManager = factory.createEntityManager();
        try {
            return entityManager.createQuery("select b from Bird b where b.species = :species", Bird.class)
                    .setParameter("species", species)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    public List<Bird> listBirdsWithEggsGiven(int eggs) {
        EntityManager entityManager = factory.createEntityManager();
        try {
            return entityManager.createQuery("select b from Bird b where b.nest.numberOfEggs = :eggs", Bird.class)
                    .setParameter("eggs", eggs)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    public void deleteBird(long id) {
        EntityManager entityManager = factory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
//            Bird bird = entityManager.getReference(Bird.class, id);
//            entityManager.remove(bird);
            entityManager.createQuery("delete Bird b where b.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }
}
