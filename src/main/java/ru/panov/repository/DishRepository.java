package ru.panov.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.panov.model.Dish;

import java.util.List;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {
    List<Dish> getDishByMenuId(int id);
}
