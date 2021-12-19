package valejaco.crossfit.lahorie.controlers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import valejaco.crossfit.lahorie.dao.UsersRepository;

@RestController
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;

    @RequestMapping("/users")
    public ResponseEntity<?> getUsersList() {
        return ResponseEntity.ok(usersRepository.findAll());
    }

}
