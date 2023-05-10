package ru.panov.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "dish", uniqueConstraints = {@UniqueConstraint(name = "UniqueMenuIdAndName", columnNames = {"menu_id", "name"})})
public class Dish extends AbstractNamedEntity {
    @Column(name = "price", nullable = false)
    @NotNull
    private Double price;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Menu menu;
}
