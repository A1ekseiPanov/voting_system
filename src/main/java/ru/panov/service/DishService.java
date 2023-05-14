package ru.panov.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.panov.error.IllegalRequestDataException;
import ru.panov.model.Dish;
import ru.panov.model.Menu;
import ru.panov.repository.DishRepository;
import ru.panov.repository.MenuRepository;

import java.util.List;

import static ru.panov.util.validation.ValidationUtil.checkNew;

@Service
@AllArgsConstructor
public class DishService {
    private final DishRepository dishRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public void create(int id, Dish dish) {
        Menu menu = menuRepository.getExisted(id);
        List<Dish> dishOld = dishRepository.getDishByMenuId(menu.id());
        if (dishOld.stream().anyMatch(d -> d.getName().equalsIgnoreCase(dish.getName()))) {
            throw new IllegalRequestDataException("Dish with this name already exists on the menu id=" + id);
        } else {
            checkNew(dish);
            dish.setMenu(menu);
            dishRepository.save(dish);
        }
    }
}
