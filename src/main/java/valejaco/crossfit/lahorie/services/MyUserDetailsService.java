package valejaco.crossfit.lahorie.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import valejaco.crossfit.lahorie.dao.UserRepository;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        valejaco.crossfit.lahorie.models.User u1 = userRepository.findByUserName(username);
        if( u1 != null ) {
            return new User( u1.getUserName() , u1.getPassword() , new ArrayList<>() );
        }else{
            return new User( "foo" , "foo" , new ArrayList<>() );
        }
    }
}
