package valejaco.crossfit.lahorie.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import valejaco.crossfit.lahorie.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserName( String userName );
}
