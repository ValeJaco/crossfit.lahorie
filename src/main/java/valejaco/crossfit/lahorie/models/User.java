package valejaco.crossfit.lahorie.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import valejaco.crossfit.lahorie.chunk.UsersRequest;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;
    private String forename;
    private String lastname;
    private OffsetDateTime lastConnectionDate;
    private Boolean useSessionNotebook;
    private Integer availableSessionNumber;
    private String address;
    private String zipCode;
    private String city;
    private LocalDate subscriptionDate;
    private LocalDate birthDate;
    private LocalDate renewalDate;
    private String paymentMethod;
    private Boolean freeAccess;
    private String badgeReference;

    public Collection<String> getRoles() {
        return roles.stream().map(Role::getName).collect(Collectors.toList());
    }

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @ManyToMany(mappedBy = "users")
    @JsonIgnore
    private Set<Seance> seances = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    public void addRoleToUser(Role role) {
        roles.add(role);
    }

    public void removeRoleFromUser(Role role) {
        roles.remove(role);
    }

    public void removeAllRolesFromUser() {
        roles.clear();
    }

    public void patchValues(UsersRequest patch) {

        if (patch.getAddress().isPresent()){
            this.setAddress(patch.getAddress().get());
        }
        if (patch.getAvailableSessionNumber().isPresent()){
            this.setAvailableSessionNumber(patch.getAvailableSessionNumber().get());
        }
        if (patch.getBadgeReference().isPresent()){
            this.setBadgeReference( patch.getBadgeReference().get());
        }
        if (patch.getBirthDate().isPresent()){
            this.setBirthDate(patch.getBirthDate().get());
        }
        if (patch.getCity().isPresent()){
            this.setCity(patch.getCity().get());
        }
        if (patch.getForename().isPresent()){
            this.setForename(patch.getForename().get());
        }
        if (patch.getFreeAccess().isPresent()){
            this.setFreeAccess(patch.getFreeAccess().get());
        }
        if (patch.getLastname().isPresent()){
            this.setLastname(patch.getLastname().get());
        }
        if (patch.getPassword().isPresent()) {
            this.setPassword(patch.getPassword().get());
        }
        if (patch.getPaymentMethod().isPresent()){
            this.setPaymentMethod(patch.getPaymentMethod().get());
        }
        if (patch.getRenewalDate().isPresent()){
            this.setRenewalDate(patch.getRenewalDate().get());
        }
        if (patch.getSubscriptionDate().isPresent()){
            this.setSubscriptionDate(patch.getSubscriptionDate().get());
        }
        if (patch.getUsername().isPresent()) {
            this.setUsername(patch.getUsername().get());
        }
        if (patch.getUseSessionNotebook().isPresent()){
            this.setUseSessionNotebook(patch.getUseSessionNotebook().get());
        }
        if (patch.getZipCode().isPresent()){
            this.setZipCode(patch.getZipCode().get());
        }
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
