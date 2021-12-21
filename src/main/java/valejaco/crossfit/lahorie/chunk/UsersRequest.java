package valejaco.crossfit.lahorie.chunk;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class UsersRequest {

    private JsonNullable<String> username = JsonNullable.undefined();
    private JsonNullable<String> password = JsonNullable.undefined();
    private JsonNullable<List<Long>> roleToAddId = JsonNullable.undefined();
    private JsonNullable<List<Long>> roleToRemoveId = JsonNullable.undefined();

}
