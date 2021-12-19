package valejaco.crossfit.lahorie.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

@Entity
@Data
@Getter
@Setter
public class Seance {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String maxSpot;
    @Column(nullable = false)
    private Instant startDate;
    @Column(nullable = false)
    private Integer duration;

    private String location;
    private BigInteger coachId;

    @OneToMany
    private List<User> users;

    public void addUser( User user) { users.add( user );}

}
