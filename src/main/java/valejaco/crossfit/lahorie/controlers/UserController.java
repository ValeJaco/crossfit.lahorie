package valejaco.crossfit.lahorie.controlers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import valejaco.crossfit.lahorie.dao.UserRepository;
import valejaco.crossfit.lahorie.models.User;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/users")
    public ResponseEntity<?> getUsersList() {
        return ResponseEntity.ok(userRepository.findAll());
    }

}
