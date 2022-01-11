package valejaco.crossfit.lahorie.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import valejaco.crossfit.lahorie.chunk.SeancesPlanningRequest;

import javax.persistence.*;
import java.time.OffsetTime;

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

    @JoinColumn(name="id")
    private Long planningId;
    @Column(nullable = false)
    private Long dayInWeek;
    @Column(nullable = false)
    private String typeSemaine;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer maxSpot;
    @Column(nullable = false)
    private OffsetTime startTime;
    @Column(nullable = false)
    private Integer duration;

    private String location;
    private Long coachId;

    public void patchValues( SeancesPlanningRequest patch ) {

        if (patch.getDayInWeek().isPresent()) {
            this.setDayInWeek(patch.getDayInWeek().get());
        }
        if (patch.getName().isPresent()) {
            this.setName(patch.getName().get());
        }
        if (patch.getMaxSpot().isPresent()) {
            this.setMaxSpot(patch.getMaxSpot().get());
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
