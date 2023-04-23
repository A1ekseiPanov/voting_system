package ru.panov.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@Table(name = "menu")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Menu extends AbstractNamedEntity{
    @OneToOne(mappedBy = "menu")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "menu")
    private List<Dish> dishes;
}
