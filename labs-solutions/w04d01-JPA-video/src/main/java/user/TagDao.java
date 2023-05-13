package user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class TagDao {

    private EntityManagerFactory entityManagerFactory;

    public TagDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public Tag saveTag(Tag tag){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(tag);
        em.getTransaction().commit();
        em.close();
        return tag;
    }

    public Tag findTag(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Tag tag = entityManager.find(Tag.class, id);
        entityManager.close();
        return tag;
    }
}
