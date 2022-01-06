package valejaco.crossfit.lahorie.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import valejaco.crossfit.lahorie.models.UserWaiting;
import valejaco.crossfit.lahorie.models.keys.UserWaitingKey;

public interface UsersWaitingRepository extends JpaRepository<UserWaiting, Long> {
}
