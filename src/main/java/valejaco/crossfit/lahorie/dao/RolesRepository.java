package valejaco.crossfit.lahorie.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import valejaco.crossfit.lahorie.models.Role;
import valejaco.crossfit.lahorie.models.User;

public interface RolesRepository extends JpaRepository<Role, Long> {

    Role findByName(String roleName);
}
