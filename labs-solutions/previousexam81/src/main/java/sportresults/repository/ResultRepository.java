package sportresults.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sportresults.model.Result;
import sportresults.model.SportType;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResultRepository extends JpaRepository<Result,Long> {

    @Query("select r from Result r where (:sportType is null or r.sportType = :sportType)")
    List<Result> findBySportType(Optional<String> sportType);
}
