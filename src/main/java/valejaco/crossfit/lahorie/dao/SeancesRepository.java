package valejaco.crossfit.lahorie.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import valejaco.crossfit.lahorie.models.Seance;

public interface SeancesRepository extends JpaRepository<Seance, Long> {
}
