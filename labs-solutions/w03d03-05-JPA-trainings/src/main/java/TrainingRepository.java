import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class TrainingRepository {
    EntityManagerFactory entityManagerFactory;

    public TrainingRepository() {
    }

    public TrainingRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void save(Training training) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(training);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public Training findTrainingById(long trainingId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(Training.class, trainingId);
        } finally {
            entityManager.close();
        }
    }

    public void updateTrainingWithTrainer(long trainingId, long trainerId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Trainer trainerRef = entityManager.getReference(Trainer.class, trainerId);
            Training trainingRef = entityManager.getReference(Training.class, trainingId);
            trainingRef.setTrainer(trainerRef);
            entityManager.persist(trainingRef);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public void updateTrainingWithStudent(long trainingId, long studentId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Student studentRef = entityManager.getReference(Student.class, studentId);
            Training trainingRef = entityManager.getReference(Training.class, trainingId);
            trainingRef.addStudent(studentRef);
            entityManager.persist(trainingRef);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public Training findTrainingByIdWithStudents(Long trainingId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager
                    .createQuery(
                            "SELECT DISTINCT tr FROM Training tr LEFT JOIN FETCH tr.students WHERE tr.id=:trainingId",
                            Training.class)
                    .setParameter("trainingId", trainingId)
                    .getSingleResult();
        } finally {
            entityManager.close();
        }
    }
}
