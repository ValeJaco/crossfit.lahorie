package valejaco.crossfit.lahorie.models;

import lombok.*;
import valejaco.crossfit.lahorie.chunk.GuestsRequest;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GuestSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long seanceId;
    private String guestName;
    private String coachName;
    private String comment;

    public void patchValues(GuestsRequest patch) {

        if (patch.getSeanceId().isPresent()){
            this.setSeanceId(patch.getSeanceId().get());
        }
        if (patch.getGuestName().isPresent()){
            this.setGuestName(patch.getGuestName().get());
        }
        if (patch.getCoachName().isPresent()){
            this.setCoachName( patch.getCoachName().get());
        }
        if (patch.getComment().isPresent()){
            this.setComment( patch.getComment().get());
        }
    }
}

