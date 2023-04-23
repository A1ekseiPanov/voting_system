package ru.panov.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "dish")
@Entity
public class Dish extends AbstractNamedEntity {
    @Column(name = "price")
    @NotNull
    private Double price;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;
}
