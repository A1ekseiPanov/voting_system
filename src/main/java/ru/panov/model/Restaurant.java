package ru.panov.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.panov.HasId;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true, exclude = {"menu","vote"})
@Table(name = "restaurant")
public class Restaurant extends AbstractBaseEntity implements HasId {
    @Column(name = "name", nullable = false, unique = true)
    @NotBlank
    @Size(min = 2, max = 128)
    private String name;

    @Column(name = "description")
    @NotBlank
    @Size(min = 2, max = 120)
    private String description;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Menu> menu;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonIgnore
    private List<Vote> vote = new ArrayList<>();

    public Restaurant(Integer id, String name, String description) {
        super(id);
        this.name = name;
        this.description = description;
    }
}
