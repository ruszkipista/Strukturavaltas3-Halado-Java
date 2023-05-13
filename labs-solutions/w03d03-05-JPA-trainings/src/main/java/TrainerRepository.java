import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class TrainerRepository {
    EntityManagerFactory entityManagerFactory;

    public TrainerRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void save(Trainer trainer) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(trainer);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public void updateTrainerWithTraining(long trainerId, long trainingId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Training trainingRef = entityManager.getReference(Training.class, trainingId);
            Trainer trainerRef = entityManager.getReference(Trainer.class, trainerId);
            trainerRef.addTraining(trainingRef);
            entityManager.persist(trainerRef);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public void updateTrainerWithTrainings(Long trainerId, List<Long> trainingIds) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Trainer trainerRef = entityManager.getReference(Trainer.class, trainerId);
            Training trainingRef;
            for (Long trainingId : trainingIds) {
                trainingRef = entityManager.getReference(Training.class, trainingId);
                trainerRef.addTraining(trainingRef);
            }
            entityManager.persist(trainerRef);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public Trainer findTrainerById(long trainerId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(Trainer.class, trainerId);
        } finally {
            entityManager.close();
        }
    }

    public Trainer findTrainerByIdWithTrainings(Long trainerId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager
                    .createQuery("SELECT tr FROM Trainer tr LEFT JOIN FETCH tr.trainings tg WHERE tr.id=:trainerId",
                            Trainer.class)
                    .setParameter("trainerId", trainerId)
                    .getSingleResult();
        } finally {
            entityManager.close();
        }
    }

    public Trainer findTrainerByIdWithTrainingsBetween(Long trainerId, LocalDate intervalLow, LocalDate intervalHigh) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager
                    .createQuery("SELECT tr FROM Trainer tr LEFT JOIN FETCH tr.trainings tg WHERE tr.id=:trainerId"
                                 +" AND NOT (tg.endDate < :intervalLow OR tg.startDate > :intervalHigh)",
                            Trainer.class)
                    .setParameter("trainerId", trainerId)
                    .setParameter("intervalLow", intervalLow)
                    .setParameter("intervalHigh", intervalHigh)
                    .getSingleResult();
        } finally {
            entityManager.close();
        }
    }

    public Trainer findTrainerByIdWithTrainingsAndStudents_JOINs(Long trainerId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Trainer trainer = entityManager
                    .createQuery("SELECT DISTINCT tr FROM Trainer tr "
                                +"LEFT JOIN FETCH tr.trainings tg "
                                +"LEFT JOIN FETCH tg.students st "
                                +"WHERE tr.id = :trainerId",
                            Trainer.class)
                    .setParameter("trainerId", trainerId)
                    .getSingleResult();
            return trainer;
        } finally {
            entityManager.close();
        }
    }

    public Trainer findTrainerByIdWithTrainingsAndStudents_TwoPhase(Long trainerId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Trainer trainer = entityManager
                    .createQuery("SELECT tr FROM Trainer tr LEFT JOIN FETCH tr.trainings tg WHERE tr.id = :trainerId",
                            Trainer.class)
                    .setParameter("trainerId", trainerId)
                    .getSingleResult();
            entityManager
                .createQuery("SELECT tg FROM Training tg LEFT JOIN FETCH tg.students WHERE tg IN :trainings",
                            Training.class)
                .setParameter("trainings", trainer.getTrainings())
                .getResultList();
            return trainer;
        } finally {
            entityManager.close();
        }
    }

    public Trainer findTrainerByIdWithTrainingsAndStudents_graph(Long trainerId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            EntityGraph<Trainer> graph = entityManager.createEntityGraph(Trainer.class);
            graph.addAttributeNodes("trainings");
            graph.addSubgraph("trainings").addAttributeNodes("students");
            return entityManager.find(Trainer.class, trainerId, Map.of("javax.persistence.fetchgraph", graph));
        } finally {
            entityManager.close();
        }
    }

}
