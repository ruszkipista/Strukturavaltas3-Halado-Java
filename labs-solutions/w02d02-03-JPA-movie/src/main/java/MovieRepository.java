import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class MovieRepository {
    EntityManagerFactory entityManagerFactory;

    public MovieRepository() {
    }

    public MovieRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public Long save(Movie movie) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(movie);
            entityManager.getTransaction().commit();
            return movie.getId();
        } finally {
            entityManager.close();
        }
    }

    public Movie findById(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(Movie.class, id);
        } finally {
            entityManager.close();
        }
    }

    public Movie updateTitleById(long id, String title) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Movie movie = entityManager.find(Movie.class, id);
            movie.setTitle(title);
            entityManager.getTransaction().commit();
            return movie;
        } finally {
            entityManager.close();
        }
    }

    public void removeById(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Movie movie = entityManager.getReference(Movie.class, id);
            entityManager.remove(movie);
            entityManager.getTransaction().commit();
            entityManager.close();
        } finally {
            entityManager.close();
        }
    }

    public List<Movie> listAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager
                    .createQuery("SELECT m FROM Movie m ORDER BY m.title", Movie.class)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    public List<Movie> findMoviesByTitle(String title) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager
                    .createQuery("SELECT m FROM Movie m WHERE m.title=?1", Movie.class)
                    .setParameter(1, title)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }
}
