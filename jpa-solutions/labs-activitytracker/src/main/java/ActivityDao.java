import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class ActivityDao {
    EntityManagerFactory entityManagerFactory;

    public ActivityDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void saveActivity(Activity activity){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(activity);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public Activity findActivityById(Long activityId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(Activity.class, activityId);
        } finally {
            entityManager.close();
        }
    }

    public void deleteActivityById(Long activityId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Activity activity = entityManager.getReference(Activity.class, activityId);
            entityManager.remove(activity);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public void updateTypeOfActivityById(Long activityId, Activity.ActivityType basketball) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Activity activity = entityManager.getReference(Activity.class, activityId);
            activity.setType(basketball);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public List<Activity> findAllActivities() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery("SELECT a FROM Activity a", Activity.class)
                .getResultList();
        } finally {
            entityManager.close();
        }
    }

    public void updateDescriptionOfActivityById(Long activityId, String newDescription) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Activity activity = entityManager.getReference(Activity.class, activityId);
            activity.setDescription(newDescription);;
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public Activity findActivityByIdWithLabels(Long activityId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery("SELECT a FROM Activity a "
                                            +"LEFT JOIN FETCH a.labels "
                                            +"WHERE a.id = :activityId",
                                Activity.class)
                .setParameter("activityId", activityId)
                .getSingleResult();
        } finally {
            entityManager.close();
        }
    }

    public Activity findActivityByIdWithTrackPoints(long activityId){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery("SELECT a FROM Activity a "
                                            +"LEFT JOIN FETCH a.trackPoints t "
                                            +"WHERE a.id = :activityId "
                                            +"ORDER BY t.time",
                                Activity.class)
                .setParameter("activityId", activityId)
                .getSingleResult();
        } finally {
            entityManager.close();
        }
    }

    public List<Object[]> getTrackPointCountByActivity(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery("SELECT a.description, SIZE(a.trackPoints) "
                                            +"FROM Activity a "
                                            +"ORDER BY a.description", Object[].class)
                .getResultList();
        } finally {
            entityManager.close();
        }
    }

}
