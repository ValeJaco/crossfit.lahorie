package valejaco.crossfit.lahorie.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import valejaco.crossfit.lahorie.chunk.SeancesRequest;
import valejaco.crossfit.lahorie.dao.SeancesRepository;
import valejaco.crossfit.lahorie.dao.UsersRepository;
import valejaco.crossfit.lahorie.models.Seance;
import valejaco.crossfit.lahorie.models.User;

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

    @PostMapping("/seances")
    public ResponseEntity<?> createSeance( @RequestBody SeancesRequest payload ) {

        Seance seance = new Seance();
        return ResponseEntity.ok( updateAndSaveSeance( seance , payload ) );
    }

    @PatchMapping("/seances/{seanceId}")
    public ResponseEntity<?> patchSeance( @PathVariable Long seanceId , @RequestBody SeancesRequest payload ) {
        Optional<Seance> seance = seancesRepository.findById( seanceId );

        if( seance.isPresent() ){
            return ResponseEntity.ok( updateAndSaveSeance( seance.get() , payload ) );
        }

        return ResponseEntity.badRequest().body( "Error while updating Seance " + seanceId );
    }

    private Seance updateAndSaveSeance(Seance seance, SeancesRequest payload){
        updateUsers( seance , payload );
        seance.patchValues( payload );
        return seancesRepository.save(seance);
    }

    private void updateUsers( Seance seance , SeancesRequest patch ) {

        if( patch.getUserToAddId().isPresent() ){
            Optional<User> userToAdd = usersRepository.findById( patch.getUserToAddId().get() );
            userToAdd.ifPresent(seance::addUserToSeance);
        }

        if( patch.getUserToRemoveId().isPresent() ){
            Optional<User> userToRemove = usersRepository.findById( patch.getUserToRemoveId().get() );
            userToRemove.ifPresent(seance::removeUserFromSeance);
        }
    }

}
