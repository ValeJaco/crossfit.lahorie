package valejaco.crossfit.lahorie.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import valejaco.crossfit.lahorie.chunk.GuestsRequest;
import valejaco.crossfit.lahorie.chunk.SeancesRequest;
import valejaco.crossfit.lahorie.dao.GuestsSubscriptionRepository;
import valejaco.crossfit.lahorie.dao.SeancesRepository;
import valejaco.crossfit.lahorie.dao.UsersRepository;
import valejaco.crossfit.lahorie.dao.UsersWaitingRepository;
import valejaco.crossfit.lahorie.models.GuestSubscription;
import valejaco.crossfit.lahorie.models.Seance;
import valejaco.crossfit.lahorie.models.User;
import valejaco.crossfit.lahorie.models.UserWaiting;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class SeancesController {
    @Autowired
    private SeancesRepository seancesRepository;

    @Autowired
    private GuestsSubscriptionRepository guestsSubscriptionRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UsersWaitingRepository usersWaitingRepository;

    @GetMapping("/incomingSeances/{userId}")
    public ResponseEntity<?> getIncommingSeancesForUser(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSz") OffsetDateTime startDate,
            @PathVariable Long userId) {
        List<Seance> seanceList = seancesRepository.findByStartDateGreaterThanEqualAndUsers_IdOrderByStartDateAsc(startDate, userId);

        // + Séances en file d'attente
        seanceList.addAll( getWaitingSeanceListForUser(startDate,userId) );

        return ResponseEntity.ok(seanceList);
    }

    @GetMapping("/allSeances/{userId}")
    public ResponseEntity<?> getAllSeancesForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(seancesRepository.findByUsers_IdOrderByStartDateAsc(userId));
    }

    @GetMapping("/seances")
    public ResponseEntity<List<Seance>> getSeancesList(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSz") OffsetDateTime startDate,
            @RequestParam(required = false, defaultValue = "15") Long daysToAdd) {

        if (startDate != null) {
            OffsetDateTime maxDate = startDate.plusDays(daysToAdd);
            return ResponseEntity.ok(seancesRepository.findAllByStartDateGreaterThanEqualAndStartDateLessThanEqualOrderByStartDateAsc(startDate, maxDate));
        } else {
            return ResponseEntity.ok(seancesRepository.findAll((Sort.by(Sort.Direction.ASC, "startDate"))));
        }
    }

    @GetMapping("/seances/{seanceId}")
    public ResponseEntity<?> getSeanceById(@PathVariable Long seanceId) {

        Optional<Seance> seance = seancesRepository.findById(seanceId);

        if (seance.isPresent()) {
            return ResponseEntity.ok(seance.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/seances/guests")
    public ResponseEntity<?> addGuestToSeance(@RequestBody GuestsRequest payload) {

        GuestSubscription guestSub = new GuestSubscription();

        if (payload.getSeanceId().isPresent()) {
            Optional<Seance> seance = seancesRepository.findById(payload.getSeanceId().get());
            guestSub.patchValues(payload);
            seance.ifPresent(updatedSeance -> updatedSeance.addGuestToSeance(guestSub));
            seance.ifPresent(updatedSeance -> seancesRepository.save(updatedSeance));
            return ResponseEntity.ok(seance);
        }

        return ResponseEntity.badRequest().body("Error while creating guestSubscription");
    }

    @PatchMapping("/seances/guests/{guestSubId}")
    public ResponseEntity<?> PatchGuest(@PathVariable Long guestSubId, @RequestBody GuestsRequest payload) {

        Optional<GuestSubscription> guestSub = guestsSubscriptionRepository.findById(guestSubId);

        if (guestSub.isPresent()) {
            guestsSubscriptionRepository.save(guestSub.get());
            return ResponseEntity.ok(guestSub.get());
        }
        return ResponseEntity.badRequest().body("Error while updating guestSubscription " + guestSubId);
    }

    @DeleteMapping("/seances/guests/{guestSubId}")
    public ResponseEntity<?> RemoveGuestFromSeance(@PathVariable Long guestSubId) {

        Optional<GuestSubscription> guestSub = guestsSubscriptionRepository.findById(guestSubId);

        if (guestSub.isPresent()) {
            Optional<Seance> seance = seancesRepository.findById(guestSub.get().getSeanceId());

            if (seance.isPresent()) {
                Optional<GuestSubscription> guestToUnsubscribe = seance.get().getGuests().stream()
                        .filter(guest -> Objects.equals(guest.getId(), guestSubId))
                        .findFirst();

                if (guestToUnsubscribe.isPresent()) {
                    seance.ifPresent(updatedSeance -> updatedSeance.removeGuestFromSeance(guestToUnsubscribe.get()));
                    seance.ifPresent(updatedSeance -> seancesRepository.save(updatedSeance));
                    return ResponseEntity.ok(seance);
                }
            }
        }
        return ResponseEntity.badRequest().body("Error while deleting guestSubscription " + guestSubId);
    }

    @PostMapping("/seances")
    public ResponseEntity<?> createSeance(@RequestBody SeancesRequest payload) {

        Seance seance = new Seance();
        return ResponseEntity.ok(updateAndSaveSeance(seance, payload));
    }

    @PatchMapping("/seances/{seanceId}")
    public ResponseEntity<?> patchSeance(@PathVariable Long seanceId, @RequestBody SeancesRequest payload) {
        Optional<Seance> seance = seancesRepository.findById(seanceId);

        if (seance.isPresent()) {
            return ResponseEntity.ok(updateAndSaveSeance(seance.get(), payload));
        }
        return ResponseEntity.badRequest().body("Error while updating Seance " + seanceId);
    }

    @DeleteMapping("/seances/{seanceId}")
    public ResponseEntity<?> deleteSeance(@PathVariable Long seanceId) {
        Optional<Seance> seance = seancesRepository.findById(seanceId);

        if (seance.isPresent()) {
            seancesRepository.delete(seance.get());
            return ResponseEntity.ok(seance.get());
        }
        return ResponseEntity.badRequest().body("Error while updating Seance " + seanceId);
    }

    private Seance updateAndSaveSeance(Seance seance, SeancesRequest payload) {
        updateUsers(seance, payload);
        seance.patchValues(payload);
        return seancesRepository.save(seance);
    }

    private void updateUsers(Seance seance, SeancesRequest patch) {

        if (patch.getUserToAddId().isPresent()) {

            if (getFreeSpotNumber(seance) < seance.getMaxSpot()) {
                Optional<User> userToAdd = usersRepository.findById(patch.getUserToAddId().get());
                userToAdd.ifPresent(seance::addUserToSeance);
            } else {

                this.subscribeToWaitingList(seance, patch.getUserToAddId().get());
            }
        }

        if (patch.getUserToRemoveId().isPresent()) {
            Optional<User> userToRemove = usersRepository.findById(patch.getUserToRemoveId().get());
            userToRemove.ifPresent(seance::removeUserFromSeance);

            try {
                this.unsubscribeFromWaitingList(seance, patch.getUserToRemoveId().get());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void unsubscribeFromWaitingList(Seance seance, Long userId) {
        Optional<UserWaiting> userToUnsubscribe = seance.getUsersWaiting().stream()
                .filter(userWaiting -> Objects.equals(userWaiting.getUserId(), userId))
                .findFirst();

        userToUnsubscribe.ifPresent(seance::removeUserFromWaitingSeance);
        userToUnsubscribe.ifPresent(
                userWaiting -> usersWaitingRepository
                        .delete(userWaiting)
        );
    }

    private void subscribeToWaitingList(Seance seance, Long userId) {

        boolean isUserAlreadySubscribed = seance.getUsers().stream().map(User::getId).filter(aLong -> Objects.equals(aLong, userId)).toArray().length > 0;

        if (!isUserAlreadySubscribed) {

            UserWaiting waitlistEntryToAdd = new UserWaiting();
            waitlistEntryToAdd.setSeanceId(seance.getId());
            waitlistEntryToAdd.setUserId(userId);
            waitlistEntryToAdd.setSubscriptionTime(OffsetDateTime.now());
            usersWaitingRepository.save(waitlistEntryToAdd);

            seance.addUserToWaitingSeance(waitlistEntryToAdd);
        }
    }

    private Integer getFreeSpotNumber(Seance seance) {

        Integer nbUser = seance.getUsers().size();
        Integer nbGuest = guestsSubscriptionRepository.countBySeanceId(seance.getId()).orElse(0);
        return nbUser + nbGuest;
    }

    private List<Seance> getWaitingSeanceListForUser(OffsetDateTime startDate, Long usersId){
        return seancesRepository.findByStartDateGreaterThanEqualAndUsersWaiting_userIdOrderByStartDateAsc(startDate,usersId);
    }

}
