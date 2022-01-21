package valejaco.crossfit.lahorie.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import valejaco.crossfit.lahorie.models.Seance;

import java.time.OffsetDateTime;
import java.util.List;

public interface SeancesRepository extends JpaRepository<Seance, Long> {

    List<Seance> findAllByStartDateGreaterThanEqualAndStartDateLessThanEqualOrderByStartDateAsc(OffsetDateTime startDate , OffsetDateTime endDate);

    List<Seance> findByStartDateGreaterThanEqualAndUsers_IdOrderByStartDateAsc(OffsetDateTime startDate, Long usersId);
    List<Seance> findByUsers_IdOrderByStartDateAsc(Long usersId);

    List<Seance> findByStartDateGreaterThanEqualAndUsersWaiting_userIdOrderByStartDateAsc(OffsetDateTime startDate, Long usersId);

}
