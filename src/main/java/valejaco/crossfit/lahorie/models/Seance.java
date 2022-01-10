package valejaco.crossfit.lahorie.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import valejaco.crossfit.lahorie.chunk.SeancesRequest;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.*;

@Entity
@Getter
@Setter
public class Seance {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique=true)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer maxSpot;
    @Column(nullable = false)
    private OffsetDateTime startDate;
    @Column(nullable = false)
    private Integer duration;

    private String location;
    private Long coachId;

    @ManyToMany(fetch = FetchType.LAZY)
    @OrderBy("forename, lastname ASC")
    @JsonIgnoreProperties("seances")
    private Set<User> users = new HashSet<>();

    public void addUserToSeance( User user ) { users.add( user );}
    public void removeUserFromSeance( User user) { users.remove( user );}

    @OrderBy("subscriptionTime ASC")
    @OneToMany(
            mappedBy = "seanceId",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @PrimaryKeyJoinColumn(name="seanceId", referencedColumnName="seanceId")
    private Set<UserWaiting> usersWaiting = new HashSet<>();

    public void addUserToWaitingSeance( UserWaiting userWaiting ) { usersWaiting.add( userWaiting );}
    public void removeUserFromWaitingSeance( UserWaiting userWaiting ) { usersWaiting.remove( userWaiting );}

    @OrderBy("guestName ASC")
    @OneToMany(
            mappedBy = "seanceId",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @PrimaryKeyJoinColumn(name="seanceId", referencedColumnName="seanceId")
    private Set<GuestSubscription> guests = new HashSet<>();

    public void addGuestToSeance( GuestSubscription guest ) { guests.add( guest );}
    public void removeGuestFromSeance( GuestSubscription guest) { guests.remove( guest );}

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
