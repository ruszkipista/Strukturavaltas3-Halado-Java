import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

public class StudentRepository {
    EntityManagerFactory entityManagerFactory;

    public StudentRepository() {
    }

    public StudentRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void save(Student student) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(student);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public void updateStudentWithTraining(long studentId, long trainingId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Student studentRef = entityManager.getReference(Student.class, studentId);
            Training trainingRef = entityManager.getReference(Training.class, trainingId);
            studentRef.addTraining(trainingRef);
            entityManager.persist(studentRef);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public Student findStudentById(long studentId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(Student.class, studentId);
        } finally {
            entityManager.close();
        }
    }

    // list all, order by attribute
    public List<Student> listAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager
                    .createQuery("SELECT st FROM Student st ORDER BY st.name", Student.class)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    public Student findStudentByIdWithTrainings(Long studentId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager
                    .createQuery(
                            "SELECT DISTINCT st FROM Student st LEFT JOIN FETCH st.trainings WHERE st.id=:studentId",
                            Student.class)
                    .setParameter("studentId", studentId)
                    .getSingleResult();
        } finally {
            entityManager.close();
        }
    }

    public List<Student> findStudentsByChargeType(Student.ChargeType chargeType) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager
                    .createQuery("SELECT st FROM Student st WHERE st.chargeType=?1", Student.class)
                    .setParameter(1, chargeType)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    public Optional<Student> findByName(String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return Optional.of(
                    entityManager
                            .createQuery("SELECT st FROM Student st WHERE st.name=?1", Student.class)
                            .setParameter(1, name)
                            .getSingleResult());
        } catch(NoResultException nre){
            return Optional.empty();
        } finally {
            entityManager.close();
        }
    } 

    // modify attribute
    public Student updateNameById(long studentId, String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Student student = entityManager.getReference(Student.class, studentId);
            student.setName(name);
            entityManager.getTransaction().commit();
            return student;
        } finally {
            entityManager.close();
        }
    }

    // remove one found by ID
    public void removeStudentById(long studentId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Student student = entityManager.getReference(Student.class, studentId);
            entityManager.remove(student);
            entityManager.getTransaction().commit();
            entityManager.close();
        } finally {
            entityManager.close();
        }
    }

}
