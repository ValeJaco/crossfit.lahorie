package valejaco.crossfit.lahorie.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import valejaco.crossfit.lahorie.models.Seance;
import java.time.LocalDateTime;
import java.util.List;

public interface SeancesRepository extends JpaRepository<Seance, Long> {

    List<Seance> findAllByStartDateGreaterThanEqualAndStartDateLessThanEqualOrderByStartDateAsc(LocalDateTime startDate , LocalDateTime endDate);
}
