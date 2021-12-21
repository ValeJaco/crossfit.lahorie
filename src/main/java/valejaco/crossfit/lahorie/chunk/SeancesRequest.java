package valejaco.crossfit.lahorie.chunk;

import lombok.*;
import org.openapitools.jackson.nullable.JsonNullable;

import javax.persistence.Column;
import java.math.BigInteger;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class SeancesRequest {

    private JsonNullable<String> name = JsonNullable.undefined();
    private JsonNullable<String> maxSpot = JsonNullable.undefined();
    private JsonNullable<Instant> startDate = JsonNullable.undefined();
    private JsonNullable<Integer> duration = JsonNullable.undefined();
    private JsonNullable<String> location = JsonNullable.undefined();
    private JsonNullable<Long> coachId = JsonNullable.undefined();
    private JsonNullable<Long> userToAddId = JsonNullable.undefined();
    private JsonNullable<Long> userToRemoveId = JsonNullable.undefined();

}
