package valejaco.crossfit.lahorie.chunk;

import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.openapitools.jackson.nullable.JsonNullable;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class UsersRequest {

    private JsonNullable<String> username = JsonNullable.undefined();
    private JsonNullable<String> password = JsonNullable.undefined();
    private JsonNullable<List<String>> roleToAddId = JsonNullable.undefined();
    private JsonNullable<List<String>> roleToRemoveId = JsonNullable.undefined();
    private JsonNullable<List<String>> roles = JsonNullable.undefined();
    private JsonNullable<String> forename = JsonNullable.undefined();
    private JsonNullable<String> lastname = JsonNullable.undefined();
    private JsonNullable<Date> lastConnectionDate = JsonNullable.undefined();
    private JsonNullable<Boolean> useSessionNotebook = JsonNullable.undefined();
    private JsonNullable<Integer> availableSessionNumber = JsonNullable.undefined();
    private JsonNullable<String> address = JsonNullable.undefined();
    private JsonNullable<String> zipCode = JsonNullable.undefined();
    private JsonNullable<String> city = JsonNullable.undefined();
    private JsonNullable<Date> subscriptionDate = JsonNullable.undefined();
    private JsonNullable<Date> birthDate = JsonNullable.undefined();
    private JsonNullable<Date> renewalDate = JsonNullable.undefined();
    private JsonNullable<String> paymentMethod = JsonNullable.undefined();
    private JsonNullable<Boolean> freeAccess = JsonNullable.undefined();
    private JsonNullable<String> badgeReference = JsonNullable.undefined();

}
