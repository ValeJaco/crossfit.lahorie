package valejaco.crossfit.lahorie.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import valejaco.crossfit.lahorie.models.Seance;

import java.time.OffsetDateTime;
import java.util.List;

public interface SeancesRepository extends JpaRepository<Seance, Long> {

    List<Seance> findAllByStartDateGreaterThanEqualAndStartDateLessThanEqualOrderByStartDateAsc(OffsetDateTime startDate , OffsetDateTime endDate);

    List<Seance> findByStartDateGreaterThanEqualAndUsers_IdOrderByStartDateAsc(OffsetDateTime startDate, Long users_id);
    List<Seance> findByUsers_IdOrderByStartDateAsc(Long users_id);
}
