package videos;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class UserDao {

    private EntityManagerFactory entityManagerFactory;

    public UserDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public User saveUser(User user) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            return user;
        } finally {
            em.close();
        }
    }

    public User findUserById(Long userId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            return em.find(User.class, userId);
        } finally {
            em.close();
        }
    }

    public User findUserByIdWithVideos(Long userId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            User user = em.createQuery("SELECT u FROM User u LEFT JOIN FETCH u.videos v WHERE u.id = :userId",
                          User.class)
                .setParameter("userId", userId)
                .getSingleResult();
            return user;
        } finally {
            em.close();
        }
    }

    public User updateUserWithVideo(long userId, Video video) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            User user = em.getReference(User.class, userId);
            video.setUser(user);
            em.persist(video);
            em.getTransaction().commit();
            return user;
        } finally {
            em.close();
        }
    }

    public User updateUserStatus(long userId, UserStatus status){
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            User user = em.getReference(User.class, userId);
            user.setUserStatus(status);
            em.getTransaction().commit();
            return user;
        } finally {
            em.close();
        }
    }

    public List<User> findUsersWithMoreVideosThan(int count){
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM User u LEFT JOIN FETCH u.videos v WHERE SIZE(v) > :count",
                          User.class)
                .setParameter("count", count)
                .getResultList();
        } finally {
            em.close();
        }
    }

    public Integer countUploadedVideos(long userId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            return em.createQuery("SELECT SIZE(u.videos) FROM User u LEFT JOIN FETCH u.videos v WHERE u.id = :userId",
                          Integer.class)
                .setParameter("userId", userId)
                .getSingleResult();
        } finally {
            em.close();
        }
    }

    public void removeUserById(long userId){
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            User user = em.getReference(User.class, userId);
            em.remove(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
