package user;

import java.util.function.Consumer;
import java.util.function.Function;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class PersistenceContextHandler {

    private EntityManagerFactory entityManagerFactory;

    public PersistenceContextHandler(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void doInTransaction(Consumer<EntityManager> consumer) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            consumer.accept(entityManager);
            entityManager.getTransaction().commit();
        }
        finally {
            entityManager.close();
        }
    }

    public <T> T query(Function<EntityManager, T> function) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return function.apply(entityManager);
        } finally {
            entityManager.close();
        }
    }

    public <T> T queryWithTransaction(Function<EntityManager, T> function) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            T result = function.apply(entityManager);
            entityManager.getTransaction().commit();
            return result;
        }
        finally {
            entityManager.close();
        }
    }
}
