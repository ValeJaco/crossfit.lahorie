package valejaco.crossfit.lahorie.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserWaiting {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;


    @JoinColumn(name="seanceId")
    private Long seanceId;
    private Long userId;
    private OffsetDateTime subscriptionTime;
}
