package crudtemplate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import crudtemplate.model.Multiple;

@Repository
public interface MultipleRepository extends JpaRepository<Multiple, Long> {

    @Query("SELECT m FROM Multiple m WHERE :part IS NULL OR LOCATE(UPPER(:part), UPPER(m.name)) > 0")
    List<Multiple> findAllByNamePart(Optional<String> part);
 
}
