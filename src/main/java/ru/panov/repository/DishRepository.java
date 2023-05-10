package ru.panov.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.panov.model.Dish;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {
}
