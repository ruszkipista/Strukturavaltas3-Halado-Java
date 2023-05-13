package crudtemplate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import crudtemplate.model.Single;

@Repository
public interface SingleRepository extends JpaRepository<Single, Long> {

    @Query("SELECT w FROM Single w WHERE LOCATE(UPPER(:part), UPPER(w.name)) > 0")
    List<Single> findAllByNamePart(String part);
 
}
