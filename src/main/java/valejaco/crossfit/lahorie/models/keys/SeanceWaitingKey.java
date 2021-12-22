package valejaco.crossfit.lahorie.models.keys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class SeanceWaitingKey implements Serializable {
    private Long seanceId;
    private Long userId;
}
