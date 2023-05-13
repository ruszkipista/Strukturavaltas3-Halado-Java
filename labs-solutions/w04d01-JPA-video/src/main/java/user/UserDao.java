package user;

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

    public User findUser(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            User user = em.find(User.class, id);
            return user;
        } finally {
            em.close();
        }
    }

    public void saveVideoToUser(Long userId, Long videoId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            User user = em.getReference(User.class, userId);
            Video video = em.getReference(Video.class, videoId);
            user.addVideo(video);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public User findUserByIdWithVideos(Long userId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM User u LEFT JOIN FETCH u.videos v WHERE u.id = :userId",
                    User.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    public void saveCommentToUser(Long userId, Long commentId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            User user = em.getReference(User.class, userId);
            Comment comment = em.getReference(Comment.class, commentId);
            user.addComment(comment);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public User findUserByIdWithComments(Long userId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM User u LEFT JOIN FETCH u.comments c WHERE u.id = :userId",
                    User.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    public User findUserByIdWithEVERYTHING(Long userId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            // User user = em.createQuery("select distinct u from User u left join fetch u.comments where u.id = :id",
            //                 User.class)
            //         .setParameter("id", userId)
            //         .getSingleResult();
            // em.createQuery("select distinct u from User u left join fetch u.videos where u.id = :id", User.class)
            //         .setParameter("id", userId)
            //         .getSingleResult();
            // em.createQuery("select distinct v from Video v left join fetch v.tags where v in :videos", Video.class)
            //         .setParameter("videos", user.getVideos())
            //         .getResultList();
            User user = em.createQuery("SELECT u FROM User u "
                                 +"LEFT JOIN FETCH u.comments c "
                                 +"LEFT JOIN FETCH u.videos v "
                                 +"LEFT JOIN FETCH v.tags t "
                                 +"WHERE u.id = :userId",
                                    User.class)
                .setParameter("userId", userId)
                .getSingleResult();
            em.getTransaction().commit();
            return user;
        } finally {
            em.close();
        }
    }
}
