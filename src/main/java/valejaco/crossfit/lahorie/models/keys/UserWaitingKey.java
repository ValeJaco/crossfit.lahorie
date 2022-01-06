package valejaco.crossfit.lahorie.models.keys;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWaitingKey implements Serializable {
    private Long seanceId;
    private Long userId;
}