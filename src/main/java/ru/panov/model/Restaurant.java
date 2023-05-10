package ru.panov.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "restaurant")
public class Restaurant extends AbstractBaseEntity {
    @Column(name = "name", nullable = false, unique = true)
    @NotBlank
    @Size(min = 2, max = 128)
    protected String name;

    @Column(name = "description")
    @NotBlank
    @Size(min = 2, max = 120)
    private String description;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<Menu> menu;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Vote> voteList;
}
