package valejaco.crossfit.lahorie.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import valejaco.crossfit.lahorie.models.Planning;

public interface PlanningRepository extends JpaRepository<Planning, Long> {
}
