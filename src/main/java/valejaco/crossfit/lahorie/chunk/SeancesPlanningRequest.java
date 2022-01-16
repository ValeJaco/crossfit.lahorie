package valejaco.crossfit.lahorie.chunk;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.openapitools.jackson.nullable.JsonNullable;

import java.sql.Time;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class SeancesPlanningRequest {

    private JsonNullable<Long> planningId = JsonNullable.undefined();
    private JsonNullable<Long> dayOfWeek = JsonNullable.undefined();
    private JsonNullable<String> name = JsonNullable.undefined();
    private JsonNullable<Integer> maxSpot = JsonNullable.undefined();
    private JsonNullable<Integer> unsubcriptionHoursLimit = JsonNullable.undefined();
    private JsonNullable<Time> startTime = JsonNullable.undefined();
    private JsonNullable<Integer> duration = JsonNullable.undefined();
    private JsonNullable<String> location = JsonNullable.undefined();
    private JsonNullable<Long> coachId = JsonNullable.undefined();
    private JsonNullable<Long> userToAddId = JsonNullable.undefined();
    private JsonNullable<Long> userToRemoveId = JsonNullable.undefined();

}
