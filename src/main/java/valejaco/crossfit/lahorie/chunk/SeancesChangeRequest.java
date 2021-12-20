package valejaco.crossfit.lahorie.chunk;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.openapitools.jackson.nullable.JsonNullable;

@NoArgsConstructor
@Getter
@ToString
public class SeancesChangeRequest {

    private JsonNullable<Long> productId = JsonNullable.undefined();
    private JsonNullable<String> wineNumber = JsonNullable.undefined();
    private JsonNullable<Boolean> forDregs = JsonNullable.undefined();
}
