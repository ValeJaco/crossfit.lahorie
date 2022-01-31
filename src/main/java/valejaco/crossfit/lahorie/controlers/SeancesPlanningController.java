package valejaco.crossfit.lahorie.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import valejaco.crossfit.lahorie.chunk.PlanningsRequest;
import valejaco.crossfit.lahorie.chunk.SeancesPlanningRequest;
import valejaco.crossfit.lahorie.dao.PlanningRepository;
import valejaco.crossfit.lahorie.dao.SeancesPlanningRepository;
import valejaco.crossfit.lahorie.models.Planning;
import valejaco.crossfit.lahorie.models.SeancePlanning;

import java.util.Optional;

@CrossOrigin("*")
@RestController
public class SeancesPlanningController {

    @Autowired
    private SeancesPlanningRepository seancesPlanningRepository;

    @Autowired
    private PlanningRepository planningRepository;

    @PreAuthorize("hasAnyRole('COACH','OFFICE')")
    @GetMapping("/plannings/{planningId}")
    public ResponseEntity<?> getPlanningById(@PathVariable Long planningId) {

        Optional<Planning> planning = planningRepository.findById(planningId);

        if (planning.isPresent()) {
            return ResponseEntity.ok(planning.get());
        }
        return ResponseEntity.badRequest().body("No planning exists with ID " + planningId);
    }

    @PreAuthorize("hasAnyRole('COACH','OFFICE')")
    @GetMapping("/plannings")
    public ResponseEntity<?> getPlannings() {
        return ResponseEntity.ok(planningRepository.findAll());
    }

    @PreAuthorize("hasAnyRole('COACH','OFFICE')")
    @PostMapping("/plannings")
    public ResponseEntity<?> createPlanning( @RequestBody PlanningsRequest payload) {

        Planning planning = new Planning();
        planning.patchValues(payload);
        Planning planningWithName = planningRepository.findByName(planning.getName());

        if( planningWithName == null ){
            planningRepository.save(planning);
            return ResponseEntity.ok(planning);
        }

        return ResponseEntity.badRequest().body("Planning already exists with name : " + planning.getName());
    }

    @PreAuthorize("hasAnyRole('COACH','OFFICE')")
    @PatchMapping("/plannings/{planningId}")
    public ResponseEntity<?> patchPlanning(@PathVariable Long planningId, @RequestBody PlanningsRequest payload) {

        if( payload.getIsActive().isPresent() ){
            if( payload.getIsActive().get() ){
                //TODO uncomment if only one planning must be active at one time
                // this.inactiveAllPlanning();
            }
        }

        Optional<Planning> planning = planningRepository.findById(planningId);
        if( planning.isPresent() ){
            planning.get().patchValues(payload);
            planningRepository.save(planning.get());
            return ResponseEntity.ok(planning);
        }

        return ResponseEntity.badRequest().body("Error while updating planning " + planningId);
    }

    @PreAuthorize("hasAnyRole('COACH','OFFICE')")
    @DeleteMapping("/plannings/{planningId}")
    public ResponseEntity<?> deletePlanning(@PathVariable Long planningId) {

        Optional<Planning> planning = planningRepository.findById(planningId);
        if( planning.isPresent() ){
            planningRepository.delete(planning.get());
            return ResponseEntity.ok( "Planning deleted for ID : " + planningId);
        }

        return ResponseEntity.badRequest().body("Error while deleting planning " + planningId);
    }


    @PreAuthorize("hasAnyRole('COACH','OFFICE')")
    @PostMapping("/plannings/seances/")
    public ResponseEntity<?> createSeanceForPlanning(@RequestBody SeancesPlanningRequest payload) {

        SeancePlanning seancePlanning = new SeancePlanning();
        seancePlanning.patchValues( payload );

        if( seancePlanning.getPlanningId() > 0 ){

            Optional<Planning> planning = planningRepository.findById(seancePlanning.getPlanningId());
            if( planning.isPresent() ){
                planning.get().addSeanceToPlanning(seancePlanning);
                seancesPlanningRepository.save(seancePlanning);
                planningRepository.save(planning.get());
                return ResponseEntity.ok(seancePlanning);
            }
        }

        return ResponseEntity.badRequest().body("Error while creating seancePlanning for planning ID : " + seancePlanning.getPlanningId());
    }

    @PreAuthorize("hasAnyRole('COACH','OFFICE')")
    @PatchMapping("/plannings/seances/{seancePlanningId}")
    public ResponseEntity<?> patchSeanceFromPlanning(@PathVariable Long seancePlanningId, @RequestBody SeancesPlanningRequest payload) {

        Optional<SeancePlanning> seancePlanning = seancesPlanningRepository.findById(seancePlanningId);
        if( seancePlanning.isPresent() ){
            seancePlanning.get().patchValues(payload);
            seancesPlanningRepository.save(seancePlanning.get());
            return ResponseEntity.ok( seancePlanning.get() );
        }

        return ResponseEntity.badRequest().body("Error while deleting seancePlanning " + seancePlanningId);
    }

    @PreAuthorize("hasAnyRole('COACH','OFFICE')")
    @DeleteMapping("/plannings/seances/{seancePlanningId}")
    public ResponseEntity<?> removeSeanceFromPlanning(@PathVariable Long seancePlanningId) {

        Optional<SeancePlanning> seancePlanning = seancesPlanningRepository.findById(seancePlanningId);
        if( seancePlanning.isPresent() ){
            seancesPlanningRepository.delete(seancePlanning.get());
            return ResponseEntity.ok( seancePlanning.get() );
        }

        return ResponseEntity.badRequest().body("Error while deleting seancePlanning " + seancePlanningId);
    }

    private void inactiveAllPlanning(){
        this.planningRepository.findAll().forEach(planning -> {
            planning.setIsActive(false);
            this.planningRepository.save(planning);
        });
    }
}
