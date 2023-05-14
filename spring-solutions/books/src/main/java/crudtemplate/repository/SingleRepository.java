package crudtemplate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import crudtemplate.model.Single;

@Repository
public interface SingleRepository extends JpaRepository<Single, Long> {

    @Query("SELECT s FROM Single s WHERE :part IS NULL OR LOCATE(UPPER(:part), UPPER(s.name)) > 0")
    List<Single> findAllByNamePart(Optional<String> part);
 
}
