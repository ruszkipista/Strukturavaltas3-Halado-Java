import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class AreaDao {
    EntityManagerFactory entityManagerFactory;

    public AreaDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void saveArea(Area area){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(area);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public Area findAreaById(Long areaId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(Area.class, areaId);
        } finally {
            entityManager.close();
        }
    }

    public City saveCity(City city) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(city);
            entityManager.getTransaction().commit();
            return city;
        } finally {
            entityManager.close();
        }
    }

    public Area getAreaByIdWithCities(Long areaId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery("SELECT a FROM Area a "
                                            +"LEFT JOIN FETCH a.cities "
                                            +"WHERE a.id = :areaId",
                                Area.class)
                .setParameter("areaId", areaId)
                .getSingleResult();
        } finally {
            entityManager.close();
        }
    }

}