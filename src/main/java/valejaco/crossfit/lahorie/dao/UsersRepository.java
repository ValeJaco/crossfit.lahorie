package valejaco.crossfit.lahorie.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import valejaco.crossfit.lahorie.models.User;

import java.util.List;

public interface UsersRepository extends JpaRepository<User, Long> {

    User findByUsername( String username );

    @Query("SELECT u FROM User u " +
            "WHERE UPPER( CONCAT( u.forename , ' ' , u.lastname ) )  like UPPER( CONCAT('%',?1,'%') ) " +
            "OR UPPER( CONCAT( u.lastname , ' ' , u.forename ) ) like UPPER( CONCAT('%',?1,'%') ) "+
            "order by u.forename, u.lastname")
    List<User> searchUsersByName(String searchedName );

}
