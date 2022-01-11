package valejaco.crossfit.lahorie.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import valejaco.crossfit.lahorie.models.SeancePlanning;

public interface SeancesPlanningRepository extends JpaRepository<SeancePlanning, Long> {
}
