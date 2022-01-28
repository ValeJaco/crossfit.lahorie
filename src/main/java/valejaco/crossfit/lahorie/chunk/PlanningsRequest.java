package valejaco.crossfit.lahorie.chunk;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.openapitools.jackson.nullable.JsonNullable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class PlanningsRequest {

        private JsonNullable<String> name = JsonNullable.undefined();
        private JsonNullable<Boolean> isActive = JsonNullable.undefined();
        private JsonNullable<Integer> postponedWeekNumber = JsonNullable.undefined();
}
