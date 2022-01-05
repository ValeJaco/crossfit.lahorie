package valejaco.crossfit.lahorie.models;

import lombok.Getter;
import lombok.Setter;
import valejaco.crossfit.lahorie.models.keys.SeanceWaitingKey;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.time.Instant;
import java.util.Date;

@Entity
@Getter
@Setter
@IdClass(SeanceWaitingKey.class)
public class SeanceWaiting {

    @Id
    private Long seanceId;
    @Id
    private Long userId;

    private Date subscriptionTime;
}

