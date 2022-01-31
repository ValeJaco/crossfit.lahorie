package valejaco.crossfit.lahorie.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import valejaco.crossfit.lahorie.models.Planning;

import java.util.List;

public interface PlanningRepository extends JpaRepository<Planning, Long> {

    Planning findByName(String name);

    List<Planning> findByIsActive(Boolean isActive);
}
