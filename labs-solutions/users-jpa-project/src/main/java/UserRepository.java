import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

public class UserRepository {
    EntityManagerFactory entityManagerFactory;

    public UserRepository() {
    }

    public UserRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public User save(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
            return user;
        } finally {
            entityManager.close();
        }
    }

    public User findById(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(User.class, id);
        } finally {
            entityManager.close();
        }
    }

    public Optional<User> findByUsername(String username) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return Optional.of(
                    entityManager
                            .createQuery("SELECT u FROM User u WHERE u.username=?1", User.class)
                            .setParameter(1, username)
                            .getSingleResult());
        } catch(NoResultException nre){
            return Optional.empty();
        } finally {
            entityManager.close();
        }
    }    

    public User updatePasswordById(long id, String passwordHash) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            User user = entityManager.find(User.class, id);
            user.setPasswordHash(passwordHash);
            entityManager.getTransaction().commit();
            return user;
        } finally {
            entityManager.close();
        }
    }

    public User updatePasswordByUsername(String username, String newPasswordHash) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            User user = entityManager
                .createQuery("SELECT u FROM User u WHERE u.username=?1", User.class)
                .setParameter(1, username)
                .getSingleResult();
            user.setPasswordHash(newPasswordHash);
            entityManager.getTransaction().commit();
            return user;
        } finally {
            entityManager.close();
        }
    }

    public void removeById(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            User user = entityManager.getReference(User.class, id);
            entityManager.remove(user);
            entityManager.getTransaction().commit();
            entityManager.close();
        } finally {
            entityManager.close();
        }
    }

    public List<User> listAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager
                    .createQuery("SELECT u FROM User u ORDER BY u.username", User.class)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

}
