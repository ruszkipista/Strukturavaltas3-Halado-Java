package user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class CommentDao {

    private EntityManagerFactory entityManagerFactory;

    public CommentDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public Comment saveComment(Comment comment){
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(comment);
            em.getTransaction().commit();
            return comment;
        } finally {
            em.close();
        }
    }

    public Comment findComment(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Comment comment = entityManager.find(Comment.class, id);
            return comment;
        } finally {
            entityManager.close();
        }
    }
}
