package ru.panov.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.panov.model.Menu;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<Menu> {
    List<Menu> getMenuByOfferDateAndRestaurantId(LocalDate date, int id);
}
