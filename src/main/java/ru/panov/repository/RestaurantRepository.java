package ru.panov.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.panov.model.Restaurant;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {
}
