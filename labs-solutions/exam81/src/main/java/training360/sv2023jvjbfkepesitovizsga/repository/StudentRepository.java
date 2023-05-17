package training360.sv2023jvjbfkepesitovizsga.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import training360.sv2023jvjbfkepesitovizsga.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT s FROM Student s LEFT JOIN FETCH Test t " 
          +"WHERE s.id = :studentId AND (:subject IS NULL OR t.subject = :subject)")
    List<Student> getStudentByIdWithTests(long studentId, Optional<String> subject);
 
}
