package training360.guinessapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import training360.guinessapp.domain.Recorder;
import training360.guinessapp.domain.WorldRecord;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

public interface WorldRecordRepository extends JpaRepository<WorldRecord, Long>  {

}
