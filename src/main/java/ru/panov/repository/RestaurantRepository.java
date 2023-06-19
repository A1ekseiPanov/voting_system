package ru.panov.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.panov.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {
    Optional<Restaurant> findByNameIgnoreCase(String name);

    @Query("Select r from Restaurant r left join fetch r.menu m where m.offerDate=:date")
    List<Restaurant> getAllRestaurantAndMenu(LocalDate date);
}
