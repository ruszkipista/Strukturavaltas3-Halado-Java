import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class TrackPointDao {
    EntityManagerFactory entityManagerFactory;

    public TrackPointDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void saveTrackPoint(TrackPoint trackPoint){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            trackPoint.getActivity().addTrackPoint(trackPoint);
            entityManager.persist(trackPoint);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public List<Coordinate> findTrackPointCoordinatesAfterDateTime(LocalDateTime afterThis, int start, int max){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createNamedQuery("coordinates",Coordinate.class)
                .setParameter("afterThis", afterThis)
                .setFirstResult(start)
                .setMaxResults(max)
                .getResultList();
        } finally {
            entityManager.close();
        }
    }
}