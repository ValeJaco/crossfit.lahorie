package valejaco.crossfit.lahorie.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import valejaco.crossfit.lahorie.models.SeanceWaiting;
import valejaco.crossfit.lahorie.models.keys.SeanceWaitingKey;

public interface SeancesWaitingRepository extends JpaRepository<SeanceWaiting, SeanceWaitingKey> {
}
