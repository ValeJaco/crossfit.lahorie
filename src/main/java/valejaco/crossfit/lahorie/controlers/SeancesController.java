package valejaco.crossfit.lahorie.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
import java.util.stream.Collectors;

@CrossOrigin("*")
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

    @PreAuthorize("hasAnyRole('COACH','OFFICE','MEMBER')")
    @GetMapping("/incomingSeances/{userId}")
    public ResponseEntity<?> getIncommingSeancesForUser(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSz") OffsetDateTime startDate,
            @PathVariable Long userId) {
        List<Seance> seanceList = seancesRepository.findByStartDateGreaterThanEqualAndUsers_IdOrderByStartDateAsc(startDate, userId);

        // + Séances en file d'attente
        seanceList.addAll(getWaitingSeanceListForUser(startDate, userId));

        return ResponseEntity.ok(seanceList);
    }

    @PreAuthorize("hasAnyRole('COACH','OFFICE','MEMBER')")
    @GetMapping("/allSeances/{userId}")
    public ResponseEntity<?> getAllSeancesForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(seancesRepository.findByUsers_IdOrderByStartDateAsc(userId));
    }

    @PreAuthorize("hasAnyRole('COACH','OFFICE','MEMBER')")
    @GetMapping("/seances")
    public ResponseEntity<List<Seance>> getSeancesList(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSz") OffsetDateTime startDate,
            @RequestParam(required = false, defaultValue = "15") Long daysToAdd) {


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<String> loggedUserRoles = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        List<Seance> seances = null;

        if (startDate != null) {
            OffsetDateTime maxDate = startDate.plusDays(daysToAdd);
            seances = seancesRepository.findAllByStartDateGreaterThanEqualAndStartDateLessThanEqualOrderByStartDateAsc(startDate, maxDate);
        } else {
            seances = seancesRepository.findAll((Sort.by(Sort.Direction.ASC, "startDate")));
        }

        if (loggedUserRoles.contains("ROLE_OFFICE")) {
            return ResponseEntity.ok(seances);
        } else {
            return ResponseEntity.ok(seances.stream().filter(seance ->
                    loggedUserRoles.contains(seance.getSeanceType())
            ).collect(Collectors.toList()));
        }
    }

    @PreAuthorize("hasAnyRole('COACH','OFFICE')")
    @GetMapping("/seances/{seanceId}")
    public ResponseEntity<?> getSeanceById(@PathVariable Long seanceId) {

        Optional<Seance> seance = seancesRepository.findById(seanceId);

        if (seance.isPresent()) {
            return ResponseEntity.ok(seance.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAnyRole('COACH','OFFICE')")
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

    @PreAuthorize("hasAnyRole('COACH','OFFICE')")
    @PatchMapping("/seances/guests/{guestSubId}")
    public ResponseEntity<?> PatchGuest(@PathVariable Long guestSubId, @RequestBody GuestsRequest payload) {

        Optional<GuestSubscription> guestSub = guestsSubscriptionRepository.findById(guestSubId);

        if (guestSub.isPresent()) {
            guestsSubscriptionRepository.save(guestSub.get());
            return ResponseEntity.ok(guestSub.get());
        }
        return ResponseEntity.badRequest().body("Error while updating guestSubscription " + guestSubId);
    }

    @PreAuthorize("hasAnyRole('COACH','OFFICE')")
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

    @PreAuthorize("hasAnyRole('COACH','OFFICE')")
    @PostMapping("/seances")
    public ResponseEntity<?> createSeance(@RequestBody SeancesRequest payload) {

        Seance seance = new Seance();
        return ResponseEntity.ok(updateAndSaveSeance(seance, payload));
    }

    @PreAuthorize("hasAnyRole('COACH','OFFICE','MEMBER')")
    @PatchMapping("/seances/{seanceId}")
    public ResponseEntity<?> patchSeance(@PathVariable Long seanceId, @RequestBody SeancesRequest payload) {
        Optional<Seance> seance = seancesRepository.findById(seanceId);

        if (seance.isPresent()) {
            return ResponseEntity.ok(updateAndSaveSeance(seance.get(), payload));
        }
        return ResponseEntity.badRequest().body("Error while updating Seance " + seanceId);
    }

    @PreAuthorize("hasAnyRole('COACH','OFFICE')")
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

    private boolean addUserToSeance(Seance seance, Long userToAddId, boolean forceSubscription) {

        if (getFreeSpotNumber(seance) < seance.getMaxSpot() || forceSubscription) {
            Optional<User> userToAdd = usersRepository.findById(userToAddId);
            userToAdd.ifPresent(seance::addUserToSeance);
        } else {
            this.subscribeToWaitingList(seance, userToAddId);
        }
        return true;
    }

    /**
     * @return true only is user was subscribed to seance, and not to waiting queue.
     */
    private boolean removeUserFromSeance(Seance seance, Long userToRemoveId) {

        if (isUserSubscribed(seance, userToRemoveId)) {
            Optional<User> userToRemove = usersRepository.findById(userToRemoveId);
            userToRemove.ifPresent(seance::removeUserFromSeance);
            return true;
        }
        return false;
    }

    private boolean removeUserFromWaitingList(Seance seance, Long userToRemoveId) {
        if (isUSerSubscribedToWaitingList(seance, userToRemoveId)) {
            this.unsubscribeFromWaitingList(seance, userToRemoveId);
            return true;
        }
        return false;
    }

    private User switchFirstWaitingUserIfNeeded(Seance seance) {
        if (seance.getUsersWaiting().size() > 0) {
            Optional<User> userToSwitch = usersRepository.findById(seance.getUsersWaiting().stream().findFirst().get().getUserId());
            if (userToSwitch.isPresent()) {
                if (addUserToSeance(seance, userToSwitch.get().getId(), true)) {
                    if (removeUserFromWaitingList(seance, userToSwitch.get().getId())) {
                        return userToSwitch.get();
                    }
                }
            }
        }
        return null;
    }

    private void updateUsers(Seance seance, SeancesRequest patch) {

        if (patch.getUserToAddId().isPresent()) {
            addUserToSeance(seance, patch.getUserToAddId().get(), false);
        }

        if (patch.getUserToRemoveId().isPresent()) {
            if (removeUserFromSeance(seance, patch.getUserToRemoveId().get())) {
                // on récupère le premier membre de la file d'attente et si il est inscrit, puis averti
                User userToInform = switchFirstWaitingUserIfNeeded(seance);
                if (userToInform != null) {
                    // TODO send mail to userToInform
                    System.out.println(userToInform);
                }
            } else {
                removeUserFromWaitingList(seance, patch.getUserToRemoveId().get());
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

        if (!isUserSubscribed(seance, userId) && !isUSerSubscribedToWaitingList(seance, userId)) {
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

    private List<Seance> getWaitingSeanceListForUser(OffsetDateTime startDate, Long usersId) {
        return seancesRepository.findByStartDateGreaterThanEqualAndUsersWaiting_userIdOrderByStartDateAsc(startDate, usersId);
    }

    private boolean isUserSubscribed(Seance seance, Long userId) {
        return seance.getUsers().stream().map(User::getId).filter(aLong -> Objects.equals(aLong, userId)).toArray().length > 0;
    }

    private boolean isUSerSubscribedToWaitingList(Seance seance, Long userId) {

        return seance.getUsersWaiting().stream().map(UserWaiting::getUserId).filter(aLong -> Objects.equals(aLong, userId)).toArray().length > 0;
    }

}
