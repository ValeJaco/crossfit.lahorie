package valejaco.crossfit.lahorie.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.Instant;
import java.util.*;

@Entity
@Getter
@Setter
@JsonIdentityInfo( generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Seance {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique=true)
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

    @ManyToMany
    private Set<User> users = new HashSet<>();

    public void addUser( User user) { users.add( user );}

}
