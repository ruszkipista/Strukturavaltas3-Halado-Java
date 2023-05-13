package videos;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class VideoDao {

    private EntityManagerFactory entityManagerFactory;

    public VideoDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public Video saveVideoOfUser(Video video) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(video);
            em.getTransaction().commit();
            return video;
        } finally {
            em.close();
        }
    }

    public Video findVideoById(Long videoId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            return em.find(Video.class, videoId);
        } finally {
            em.close();
        }
    }

    public List<Video> findVideosForUserId(Long userId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            return em.createQuery("SELECT v FROM Video v WHERE v.user.id = :userId",
                          Video.class)
                .setParameter("userId", userId)
                .getResultList();
        } finally {
            em.close();
        }
    }

    public void removeVideoById(Long videoId){
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            Video video = em.getReference(Video.class, videoId);
            em.remove(video);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

}

