package valejaco.crossfit.lahorie.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import valejaco.crossfit.lahorie.chunk.SeancesChangeRequest;
import valejaco.crossfit.lahorie.dao.SeancesRepository;
import valejaco.crossfit.lahorie.dao.UsersRepository;
import valejaco.crossfit.lahorie.models.AuthenticationRequest;
import valejaco.crossfit.lahorie.models.Seance;

import java.time.temporal.ChronoUnit;
import java.util.Optional;

@RestController
public class SeancesController {
    @Autowired
    private SeancesRepository seancesRepository;

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/seances")
    public ResponseEntity<?> getSeancesList() {
        return ResponseEntity.ok(seancesRepository.findAll());
    }

    @PostMapping("/seances/{id}")
    public ResponseEntity<?> addSeancesList( @PathVariable Long id, @RequestBody SeancesChangeRequest patch ) {
        Optional<Seance> seance = seancesRepository.findById(1L);

        if( seance.isPresent() ){
            seance.get().addUser( usersRepository.findById(2L).orElse( null ) );
            seance.get().setStartDate( seance.get().getStartDate().plus(1, ChronoUnit.HOURS) );

            if (patch.getProductId().isPresent()) {
                seance.get().setId(patch.getProductId().get());
            }

            seancesRepository.save(seance.get());
        }

        return ResponseEntity.ok(seancesRepository.findAll());
    }

}
