package ru.panov.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.util.CollectionUtils;
import ru.panov.HasIdAndEmail;

import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true, exclude = {"password"})
@Entity
@Table(name = "users")
public class User extends AbstractNamedEntity implements Serializable, HasIdAndEmail {
    @Column(name = "surname")
    @Size(max = 128)
    private String surname;

    @Size(max = 128)
    @Column(name = "password", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Email
    @NotBlank
    @Size(max = 128)
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()", updatable = false)
    @NotNull
    private Date registered = new Date();

    public boolean hasRole(Role role) {
        return roles != null && roles.contains(role);
    }

    public User(User u) {
        this(u.id, u.name, u.surname, u.email, u.password, u.registered, u.roles);
    }

    public User(Integer id, String name, String surname, String email, String password, Role... roles) {
        this(id, name , surname, email, password, new Date(), Arrays.asList(roles));
    }

    public User(Integer id, String name, String surname, String email, String password, Date registered, Collection<Role> roles) {
        super(id, name);
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.registered = registered;
        setRoles(roles);
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }
}
