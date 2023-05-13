package user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class VideoDao {

    private EntityManagerFactory entityManagerFactory;

    public VideoDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public Video saveVideo(Video video) {
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

    public Video findVideo(Long videoId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            Video video = em.find(Video.class, videoId);
            return video;
        } finally {
            em.close();
        }
    }

    public void saveTagToVideo(Long videoId, Long tagId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            Video video = em.getReference(Video.class, videoId);
            Tag tag = em.getReference(Tag.class, tagId);
            video.addTag(tag);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Video findVideoByIdWithTags(Long videoId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            return em.createQuery("SELECT v FROM Video v LEFT JOIN FETCH v.tags t WHERE v.id = :videoId",
                    Video.class)
                    .setParameter("videoId", videoId)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }
}
