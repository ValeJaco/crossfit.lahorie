package valejaco.crossfit.lahorie.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import valejaco.crossfit.lahorie.models.User;

public interface UsersRepository extends JpaRepository<User, Long> {

    User findByUsername( String username );
}
