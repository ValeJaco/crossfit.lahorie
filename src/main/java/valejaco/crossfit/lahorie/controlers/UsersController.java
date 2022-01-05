package valejaco.crossfit.lahorie.controlers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import valejaco.crossfit.lahorie.chunk.SeancesRequest;
import valejaco.crossfit.lahorie.chunk.UsersRequest;
import valejaco.crossfit.lahorie.dao.RolesRepository;
import valejaco.crossfit.lahorie.dao.UsersRepository;
import valejaco.crossfit.lahorie.models.Role;
import valejaco.crossfit.lahorie.models.Seance;
import valejaco.crossfit.lahorie.models.User;

import java.util.Optional;

@RestController
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/users")
    public ResponseEntity<?> getUsersList() {
        return ResponseEntity.ok(usersRepository.findAll());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        Optional<User> user = usersRepository.findById(userId);

        if( user.isPresent() ){
            return ResponseEntity.ok( user.get() );
        }

        return ResponseEntity.badRequest().body( "Error NOT EXISTING User for ID : " + userId );
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody UsersRequest payload) {
        User newUser = new User();
        newUser.setPassword( passwordEncoder.encode("lala" ));
        updateAndSaveUser(newUser,payload);
        return ResponseEntity.ok( newUser );
    }

    @PatchMapping("/users/{userId}")
    public ResponseEntity<?> patchUser(@PathVariable Long userId, @RequestBody UsersRequest payload) {
        Optional<User> user = usersRepository.findById(userId);
        if (user.isPresent()) {
            updateAndSaveUser(user.get(),payload);
            return ResponseEntity.ok( user.get() );
        }

        return ResponseEntity.badRequest().body( "Error while patching User " + userId );
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId){
        usersRepository.deleteById( userId );
        return ResponseEntity.ok( true );
    }

    private void updateAndSaveUser(User user, UsersRequest payload){
        updateRoles( user , payload );
        user.patchValues(payload);
        usersRepository.save(user);
    }

    private void updateRoles( User user , UsersRequest payload ) {

        if( payload.getRoles().isPresent() ){
            user.removeAllRolesFromUser();
            payload.getRoles().get().forEach( roleName -> {
                Role roleToAdd = rolesRepository.findByName( roleName );
                user.addRoleToUser(roleToAdd);
            });
        }

        if( payload.getRoleToRemoveId().isPresent() ){
            payload.getRoleToRemoveId().get().forEach( roleName -> {
                Role roleToRemove = rolesRepository.findByName( roleName );
                user.removeRoleFromUser(roleToRemove);
            });
        }

        if( payload.getRoleToAddId().isPresent() ){

            payload.getRoleToAddId().get().forEach( roleName -> {
                Role roleToAdd = rolesRepository.findByName( roleName );
                user.addRoleToUser(roleToAdd);
            });
        }
    }
}
