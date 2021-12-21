package valejaco.crossfit.lahorie.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import valejaco.crossfit.lahorie.models.Role;

public interface RolesRepository extends JpaRepository<Role, Long> {

}
