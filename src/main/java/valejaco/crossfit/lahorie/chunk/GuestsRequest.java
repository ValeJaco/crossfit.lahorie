package valejaco.crossfit.lahorie.chunk;

import lombok.*;
import org.openapitools.jackson.nullable.JsonNullable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class GuestsRequest {

    private JsonNullable<Long> seanceId = JsonNullable.undefined();
    private JsonNullable<String> guestName = JsonNullable.undefined();
    private JsonNullable<String> coachName = JsonNullable.undefined();
    private JsonNullable<String> comment = JsonNullable.undefined();

}
