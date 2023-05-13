package jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class NestDao {

    private EntityManagerFactory factory;

    public NestDao(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public void saveNest(Nest nest) {
        EntityManager entityManager = factory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(nest);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public Nest findNestById(long id) {
        EntityManager entityManager = factory.createEntityManager();
        try {
            return entityManager.find(Nest.class, id);
        } finally {
            entityManager.close();
        }
    }

    public Nest findNestWithMinBirds() {
        EntityManager entityManager = factory.createEntityManager();
        try {
            return entityManager.createQuery("select distinct n from Nest n left join fetch n.birds where n.birds.size = " +
                    "(select min(m.birds.size) from Nest m)", Nest.class)
                    .getSingleResult();
        } finally {
            entityManager.close();
        }
    }

    public long countNestsWithEggsGiven(int eggs) {
        EntityManager entityManager = factory.createEntityManager();
        try {
            return entityManager.createQuery("select count(n) from Nest n where n.numberOfEggs = :eggs", Long.class)
                    .setParameter("eggs", eggs)
                    .getSingleResult();
        } finally {
            entityManager.close();
        }
    }
}
