package valejaco.crossfit.lahorie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import valejaco.crossfit.lahorie.models.AuthenticationRequest;
import valejaco.crossfit.lahorie.models.AuthenticationResponse;
import valejaco.crossfit.lahorie.models.User;
import valejaco.crossfit.lahorie.services.MyUserDetailsService;
import valejaco.crossfit.lahorie.util.JwtUtil;

import java.util.Locale;

@CrossOrigin("*")
@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @PostMapping(value = "/authenticate" )
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername().toUpperCase(Locale.ROOT),
                            authenticationRequest.getPassword()
                    ));

            final User user = userDetailsService.loadUserByUsername(authenticationRequest.getUsername().toUpperCase(Locale.ROOT));
            final String jwt = jwtTokenUtil.generateToken(user);

            return ResponseEntity.ok(new AuthenticationResponse(jwt));

        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("Incorrect username or password");
        }
    }
}
