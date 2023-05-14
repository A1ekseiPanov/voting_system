package ru.panov.web.restaurant;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.panov.model.Menu;
import ru.panov.model.Restaurant;
import ru.panov.repository.RestaurantRepository;
import ru.panov.service.MenuService;

import java.util.List;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
@Tag(name = "Restaurant Controller", description = "Restaurant Rest Controller")
public class RestaurantController {
    public static final String REST_URL = "/api/restaurants";

    private final RestaurantRepository restaurantRepository;
    private final MenuService menuService;

    @GetMapping
    @Operation(summary = "Get all restaurants")
    @Cacheable("restaurants")
    public List<Restaurant> getAll() {
        log.info("get all restaurants");
        return restaurantRepository.findAll();
    }

    @GetMapping("/{id}/menus")
    @Operation(summary = "Get menu by restaurant id")
    @Cacheable("all_menus")
    public List<Menu> getTodayMenus(@Parameter(description = "restaurant_id") @PathVariable int id) {
        log.info("get menu by restaurant id={}", id);
        return menuService.getAllTodayByRestaurant(id);
    }
}
