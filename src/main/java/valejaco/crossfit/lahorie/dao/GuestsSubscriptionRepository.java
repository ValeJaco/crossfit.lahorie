package valejaco.crossfit.lahorie.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import valejaco.crossfit.lahorie.models.GuestSubscription;

import java.util.Optional;

public interface GuestsSubscriptionRepository extends JpaRepository<GuestSubscription,Long> {

    @Query("SELECT SUM(guestNumber) FROM GuestSubscription WHERE seanceId = ?1 ")
    Optional<Integer> guestNumberFromSeance(Long seanceId );
}
