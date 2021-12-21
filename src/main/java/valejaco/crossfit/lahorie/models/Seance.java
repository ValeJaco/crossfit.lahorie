package valejaco.crossfit.lahorie.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import valejaco.crossfit.lahorie.chunk.SeancesRequest;

import javax.persistence.*;
import java.time.Instant;
import java.util.*;

@Entity
@Getter
@Setter
@JsonIdentityInfo( generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Seance {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique=true)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String maxSpot;
    @Column(nullable = false)
    private Instant startDate;
    @Column(nullable = false)
    private Integer duration;

    private String location;
    private Long coachId;

    @ManyToMany
    private Set<User> users = new HashSet<>();

    public void addUserToSeance( User user ) { users.add( user );}

    public void removeUserFromSeance( User user) { users.remove( user );}

    public void patchValues( SeancesRequest patch ) {

        if (patch.getName().isPresent()) {
            this.setName(patch.getName().get());
        }

        if (patch.getMaxSpot().isPresent()) {
            this.setMaxSpot(patch.getMaxSpot().get());
        }

        if (patch.getStartDate().isPresent()) {
            this.setStartDate(patch.getStartDate().get());
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
