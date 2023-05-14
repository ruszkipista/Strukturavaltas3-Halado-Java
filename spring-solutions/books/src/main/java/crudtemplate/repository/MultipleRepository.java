package crudtemplate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import crudtemplate.model.Multiple;

@Repository
public interface MultipleRepository extends JpaRepository<Multiple, Long> {

    @Query("SELECT m FROM Multiple m WHERE LOCATE(UPPER(:part), UPPER(m.name)) > 0")
    List<Multiple> findAllByNamePart(String part);
 
}
