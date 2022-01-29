package valejaco.crossfit.lahorie.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;
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

    @Column(nullable = false)
    private Boolean isActive = false;
    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false,columnDefinition = "integer default 4")
    private Integer postponedWeekNumber;

    @OrderBy("dayOfWeek ASC")
    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "planningId",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @PrimaryKeyJoinColumn(name="planningId", referencedColumnName="planningId")
    private Set<SeancePlanning> seancesPlanning = new HashSet<>();

    public void addSeanceToPlanning( SeancePlanning seancePlanning ) { seancesPlanning.add( seancePlanning );}

    public void patchNameOnly( PlanningsRequest patch ) {
        if (patch.getName().isPresent()) {
            this.setName(patch.getName().get());
        }
    }

    public void patchValues( PlanningsRequest patch ) {
        if (patch.getName().isPresent()) {
            this.setName(patch.getName().get());
        }
        if (patch.getIsActive().isPresent() ) {
            this.setIsActive( patch.getIsActive().get() );
        }
        if (patch.getPostponedWeekNumber().isPresent() ) {
            this.setPostponedWeekNumber( patch.getPostponedWeekNumber().get() );
        }
    }
}
