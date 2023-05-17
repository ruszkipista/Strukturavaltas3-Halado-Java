package training360.sv2023jvjbfkepesitovizsga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import training360.sv2023jvjbfkepesitovizsga.model.Test;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
 
}
