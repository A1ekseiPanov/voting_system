package ru.panov.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.panov.model.Restaurant;

import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {
    Optional<Restaurant> findByNameIgnoreCase(String name);
}
