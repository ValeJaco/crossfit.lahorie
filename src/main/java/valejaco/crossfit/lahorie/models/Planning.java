package valejaco.crossfit.lahorie.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import valejaco.crossfit.lahorie.chunk.PlanningsRequest;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Planning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OrderBy("dayInWeek ASC")
    @OneToMany(
            mappedBy = "planningId",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @PrimaryKeyJoinColumn(name="planningId", referencedColumnName="planningId")
    private Set<SeancePlanning> seancesPlanning = new HashSet<>();

    public void addSeanceToPlanning( SeancePlanning seancePlanning ) { seancesPlanning.add( seancePlanning );}
    public void removeSeanceFromPlanning( SeancePlanning seancePlanning) { seancesPlanning.remove( seancePlanning );}

    public void patchNameOnly( PlanningsRequest patch ) {
        if (patch.getName().isPresent()) {
            this.setName(patch.getName().get());
        }
    }

    public void patchValues( PlanningsRequest patch ) {
        if (patch.getName().isPresent()) {
            this.setName(patch.getName().get());
        }
        if (patch.getSeancePlanningToAdd().isPresent() ) {
            this.addSeanceToPlanning( patch.getSeancePlanningToAdd().get() );
        }
    }
}
