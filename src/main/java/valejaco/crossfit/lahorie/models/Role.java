package valejaco.crossfit.lahorie.models;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Getter
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }

    public Role setName(String name) {
        this.name = name;
        return this;
    }
}



