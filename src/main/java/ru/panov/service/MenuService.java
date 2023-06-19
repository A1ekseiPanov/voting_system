package ru.panov.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.panov.error.IllegalRequestDataException;
import ru.panov.model.Menu;
import ru.panov.model.Restaurant;
import ru.panov.repository.MenuRepository;
import ru.panov.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.panov.util.validation.ValidationUtil.checkNew;

@Service
@AllArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public void create(int restaurantId, Menu menu, LocalDate date) {
        Restaurant restaurant = restaurantRepository.getExisted(restaurantId);
        List<Menu> oldMenu = menuRepository.getMenuByOfferDateAndRestaurantId(date, restaurant.id());
        if (oldMenu.isEmpty()) {
            checkNew(menu);
            menu.setRestaurant(restaurant);
            menuRepository.save(menu);
        } else {
            throw new IllegalRequestDataException("Menu has already been created today");
        }
    }

    public List<Menu> getMenuByRestaurantIdForToday(int restaurantId) {
        List<Menu> menuList = menuRepository.getMenuByOfferDateAndRestaurantId(LocalDate.now(),
                restaurantRepository.getExisted(restaurantId).id());
        if (menuList.isEmpty()) {
            throw new IllegalRequestDataException("Menu at restaurant(id="+ restaurantId +") has already been compiled to date");
        }
        return menuList;
    }
}
