package ru.panov.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.panov.HasId;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "menu", uniqueConstraints = {@UniqueConstraint
        (name = "UniqueRestaurantIdAndName", columnNames = {"date_create_menu", "restaurant_id"})})
public class Menu extends AbstractNamedEntity implements HasId {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Restaurant restaurant;

    @Column(name = "date_create_menu", nullable = false,
            columnDefinition = "date default now()", updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate dateCreateMenu = LocalDate.now();

    @OneToMany(mappedBy = "menu", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Dish> dishes;
}
