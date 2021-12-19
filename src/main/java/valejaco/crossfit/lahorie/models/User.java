package valejaco.crossfit.lahorie.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@Getter
@Setter
public class User {

    @Id
    private Integer id;
    private String userName;
    private String password;

}
