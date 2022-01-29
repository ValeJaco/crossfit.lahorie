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
    @JoinColumn(name = "id")
    @Column(nullable = false)
    private Long dayOfWeek;
    @Column(nullable = false)
    private Integer duration;
    private String location;
    @Column(nullable = false)
    private Integer maxSpot;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Long planningId;
    @Column(nullable = false, columnDefinition = "varchar(255) default 'ROLE_MEMBER'")
    private String seanceType;
    @Column(nullable = false)
    private Time startTime;
    @Column(nullable = false)
    private Integer unsubscriptionHoursLimit;

    public void patchValues(SeancesPlanningRequest patch) {

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
        if (patch.getUnsubscriptionHoursLimit().isPresent()) {
            this.setUnsubscriptionHoursLimit(patch.getUnsubscriptionHoursLimit().get());
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
        if (patch.getSeanceType().isPresent()) {
            this.setSeanceType(patch.getSeanceType().get());
        }
    }
}
