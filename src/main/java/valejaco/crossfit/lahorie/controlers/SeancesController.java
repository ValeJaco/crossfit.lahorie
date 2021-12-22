package valejaco.crossfit.lahorie.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import valejaco.crossfit.lahorie.chunk.SeancesRequest;
import valejaco.crossfit.lahorie.dao.GuestsSubscriptionRepository;
import valejaco.crossfit.lahorie.dao.SeancesRepository;
import valejaco.crossfit.lahorie.dao.SeancesWaitingRepository;
import valejaco.crossfit.lahorie.dao.UsersRepository;
import valejaco.crossfit.lahorie.models.Seance;
import valejaco.crossfit.lahorie.models.SeanceWaiting;
import valejaco.crossfit.lahorie.models.User;
import valejaco.crossfit.lahorie.models.keys.SeanceWaitingKey;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

@RestController
public class SeancesController {
    @Autowired
    private SeancesRepository seancesRepository;

    @Autowired
    private SeancesWaitingRepository seancesWaitingRepository;

    @Autowired
    private GuestsSubscriptionRepository guestsSubscriptionRepository;

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

            if( getFreeSpotNumber(seance) < seance.getMaxSpot() ){
                Optional<User> userToAdd = usersRepository.findById( patch.getUserToAddId().get() );
                userToAdd.ifPresent(seance::addUserToSeance);
            }else{

                Optional<SeanceWaiting> seanceWaiting = seancesWaitingRepository.findById( new SeanceWaitingKey(seance.getId(),patch.getUserToAddId().get()) );
                boolean isUserAlreadySubscribed = seance.getUsers().stream().map(User::getId).filter(aLong -> Objects.equals(aLong, patch.getUserToAddId().get())).toArray().length > 0;

                // If not subscribed to session + not in waiting list
                if( seanceWaiting.isEmpty() && !isUserAlreadySubscribed ) {
                    SeanceWaiting waitlistEntryToAdd = new SeanceWaiting();
                    waitlistEntryToAdd.setSeanceId(seance.getId());
                    waitlistEntryToAdd.setUserId(patch.getUserToAddId().get());
                    waitlistEntryToAdd.setSubscriptionTime(Instant.now());
                    seancesWaitingRepository.save(waitlistEntryToAdd);
                }
            }
        }

        if( patch.getUserToRemoveId().isPresent() ){
            Optional<User> userToRemove = usersRepository.findById( patch.getUserToRemoveId().get() );
            userToRemove.ifPresent(seance::removeUserFromSeance);

            Optional<SeanceWaiting> seanceWaiting = seancesWaitingRepository.findById( new SeanceWaitingKey(seance.getId(),patch.getUserToAddId().get()) );
            seanceWaiting.ifPresent(waiting -> seancesWaitingRepository.delete(waiting));
        }
    }

    private Integer getFreeSpotNumber( Seance seance ) {

        Integer nbUser = seance.getUsers().size();
        Integer nbGuest = guestsSubscriptionRepository.guestNumberFromSeance( seance.getId() ).orElse( 0 );
        return nbUser + nbGuest;
    }

}
