package valejaco.crossfit.lahorie.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import valejaco.crossfit.lahorie.chunk.UsersRequest;
import valejaco.crossfit.lahorie.dao.RolesRepository;
import valejaco.crossfit.lahorie.dao.UsersRepository;
import valejaco.crossfit.lahorie.models.Role;
import valejaco.crossfit.lahorie.models.User;

import java.util.Optional;

@CrossOrigin("*")
@RestController
@PropertySource("classpath:constant.properties")
public class UsersController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Environment env;

    @PreAuthorize("hasAnyRole('COACH','OFFICE')")
    @GetMapping("/users")
    public ResponseEntity<?> getUsersList() {
        return ResponseEntity.ok(usersRepository.findAll());
    }

    @PreAuthorize("hasAnyRole('COACH','OFFICE')")
    @GetMapping("/usersByName")
    public ResponseEntity<?> getUsersListByName(
            @RequestParam String searchedName,
            @RequestParam(required = false, defaultValue = "20") Long resultSize) {

        return ResponseEntity.ok(usersRepository.searchUsersByName(searchedName).stream().limit(resultSize));
    }

    @PreAuthorize("hasAnyRole('COACH','OFFICE')")
    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        Optional<User> user = usersRepository.findById(userId);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.badRequest().body("Error NOT EXISTING User for ID : " + userId);
    }

    @PreAuthorize("hasAnyRole('COACH','OFFICE')")
    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody UsersRequest payload) {
        User newUser = new User();
        newUser.setPassword(passwordEncoder.encode( env.getProperty("default.password") ));
        updateAndSaveUser(newUser, payload);

        return ResponseEntity.ok(newUser);
    }

    @PreAuthorize("hasAnyRole('COACH','OFFICE','MEMBER')")
    @PatchMapping("/users/pwd/{userId}")
    public ResponseEntity<?> patchUserPassword(@PathVariable Long userId, @RequestBody UsersRequest payload) {
        Optional<User> user = usersRepository.findById(userId);
        if (user.isPresent() && payload.getOld().isPresent()) {
            try{
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(user.get().getUsername(), payload.getOld().get()));
            } catch (BadCredentialsException e) {
                return ResponseEntity.badRequest().body("failed to auth user");
            }
            user.get().setPassword(passwordEncoder.encode( payload.getConfirm().get() ));
            updateAndSaveUser(user.get(), payload);
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.badRequest().body("Error while patching User " + userId);
    }

    @PreAuthorize("hasAnyRole('COACH','OFFICE')")
    @PatchMapping("/users/resetPwd/{userId}")
    public ResponseEntity<?> resetUserPassword(@PathVariable Long userId) {
        Optional<User> user = usersRepository.findById(userId);
        if (user.isPresent() ) {
            user.get().setPassword(passwordEncoder.encode( env.getProperty("default.password") ));
            updateAndSaveUser(user.get(), new UsersRequest());
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.badRequest().body("Error while reset User password " + userId);
    }

    @PreAuthorize("hasAnyRole('COACH','OFFICE')")
    @PatchMapping("/users/{userId}")
    public ResponseEntity<?> patchUser(@PathVariable Long userId, @RequestBody UsersRequest payload) {
        Optional<User> user = usersRepository.findById(userId);
        if (user.isPresent()) {
            updateAndSaveUser(user.get(), payload);
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.badRequest().body("Error while patching User " + userId);
    }

    @PreAuthorize("hasAnyRole('COACH','OFFICE')")
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        usersRepository.deleteById(userId);
        return ResponseEntity.ok(true);
    }


    private void updateAndSaveUser(User user, UsersRequest payload) {
        updateRoles(user, payload);
        user.patchValues(payload);
        usersRepository.save(user);
    }

    private void updateRoles(User user, UsersRequest payload) {

        if (payload.getRoles().isPresent()) {
            user.removeAllRolesFromUser();
            payload.getRoles().get().forEach(roleName -> {
                Role roleToAdd = rolesRepository.findByName(roleName);
                user.addRoleToUser(roleToAdd);
            });
        }

        if (payload.getRoleToRemoveId().isPresent()) {
            payload.getRoleToRemoveId().get().forEach(roleName -> {
                Role roleToRemove = rolesRepository.findByName(roleName);
                user.removeRoleFromUser(roleToRemove);
            });
        }

        if (payload.getRoleToAddId().isPresent()) {

            payload.getRoleToAddId().get().forEach(roleName -> {
                Role roleToAdd = rolesRepository.findByName(roleName);
                user.addRoleToUser(roleToAdd);
            });
        }
    }
}
