package ru.panov.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant extends AbstractNamedEntity{
    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;
}
