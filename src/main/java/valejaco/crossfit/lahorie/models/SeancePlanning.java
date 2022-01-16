package valejaco.crossfit.lahorie.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import valejaco.crossfit.lahorie.chunk.SeancesPlanningRequest;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeancePlanning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    private Long coachId;
    @JoinColumn(name="id")
    @Column(nullable = false)
    private Long dayOfWeek;
    @Column(nullable = false)
    private Integer duration;
    @Column(nullable = false)
    private Integer unsubcriptionHoursLimit;
    private String location;
    @Column(nullable = false)
    private Integer maxSpot;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Long planningId;
    @Column(nullable = false)
    private Time startTime;

    public void patchValues( SeancesPlanningRequest patch ) {

        if (patch.getPlanningId().isPresent()) {
            this.setPlanningId(patch.getPlanningId().get());
        }
        if (patch.getDayOfWeek().isPresent()) {
            this.setDayOfWeek(patch.getDayOfWeek().get());
        }
        if (patch.getName().isPresent()) {
            this.setName(patch.getName().get());
        }
        if (patch.getMaxSpot().isPresent()) {
            this.setMaxSpot(patch.getMaxSpot().get());
        }
        if (patch.getUnsubcriptionHoursLimit().isPresent()) {
            this.setUnsubcriptionHoursLimit(patch.getUnsubcriptionHoursLimit().get());
        }
        if (patch.getStartTime().isPresent()) {
            this.setStartTime(patch.getStartTime().get());
        }
        if (patch.getDuration().isPresent()) {
            this.setDuration(patch.getDuration().get());
        }
        if (patch.getLocation().isPresent()) {
            this.setLocation(patch.getLocation().get());
        }
        if (patch.getCoachId().isPresent()) {
            this.setCoachId(patch.getCoachId().get());
        }
    }
}
