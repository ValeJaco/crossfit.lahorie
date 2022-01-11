package valejaco.crossfit.lahorie.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import valejaco.crossfit.lahorie.chunk.PlanningsRequest;
import valejaco.crossfit.lahorie.dao.PlanningRepository;
import valejaco.crossfit.lahorie.dao.SeancesPlanningRepository;
import valejaco.crossfit.lahorie.models.Planning;
import valejaco.crossfit.lahorie.models.SeancePlanning;

import java.util.Optional;

@RestController
public class SeancesPlanningController {

    @Autowired
    private SeancesPlanningRepository seancesPlanningRepository;

    @Autowired
    private PlanningRepository planningRepository;

    @GetMapping("/plannings/{planningId}")
    public ResponseEntity<?> getPlaningById(@PathVariable Long planningId) {

        Optional<Planning> planning = planningRepository.findById(planningId);

        if (planning.isPresent()) {
            return ResponseEntity.ok(planning.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/plannings")
    public ResponseEntity<?> createPlanning( @RequestBody PlanningsRequest payload) {
        Planning planning = new Planning();
        planning.patchNameOnly(payload);
        planningRepository.save(planning);
        return ResponseEntity.ok(planning);
    }

    @PatchMapping("/plannings/{planningId}")
    public ResponseEntity<?> addSeanceToPlanning(@PathVariable Long planningId, @RequestBody PlanningsRequest payload) {

        Optional<Planning> planning = planningRepository.findById(planningId);
        if( planning.isPresent() ){
            planning.get().patchValues(payload);
            planningRepository.save(planning.get());
            return ResponseEntity.ok(planning);
        }

        return ResponseEntity.badRequest().body("Error while updating planning " + planningId);
    }

    @DeleteMapping("/plannings/seances/{seancePlanningId}")
    public ResponseEntity<?> addSeanceToPlanning(@PathVariable Long seancePlanningId) {

        Optional<SeancePlanning> seancePlanning = seancesPlanningRepository.findById(seancePlanningId);
        if( seancePlanning.isPresent() ){
            seancesPlanningRepository.delete(seancePlanning.get());
            return ResponseEntity.ok( "SeancePlanning deleted for ID : " + seancePlanningId );
        }

        return ResponseEntity.badRequest().body("Error while deleting seancePlanning " + seancePlanningId);
    }

    /*@PatchMapping("/plannings/{planningId}")
    public ResponseEntity<?> patchPlanning(@PathVariable Long planningId, @RequestBody GuestsRequest payload) {

        Optional<GuestSubscription> guestSub = guestsSubscriptionRepository.findById(guestSubId);

        if (guestSub.isPresent()) {
            guestsSubscriptionRepository.save(guestSub.get());
            return ResponseEntity.ok(guestSub.get());
        }
        return ResponseEntity.badRequest().body("Error while updating guestSubscription " + guestSubId);
    }

    @PatchMapping("/plannings/seances/{seancePlanningId}")
    public ResponseEntity<?> patchSeancePlanning(@PathVariable Long seancePlanningId, @RequestBody GuestsRequest payload) {

        Optional<GuestSubscription> guestSub = guestsSubscriptionRepository.findById(guestSubId);

        if (guestSub.isPresent()) {
            guestsSubscriptionRepository.save(guestSub.get());
            return ResponseEntity.ok(guestSub.get());
        }
        return ResponseEntity.badRequest().body("Error while updating guestSubscription " + guestSubId);
    }*/
}
