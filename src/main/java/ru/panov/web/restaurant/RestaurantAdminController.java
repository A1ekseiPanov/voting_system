package ru.panov.web.restaurant;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.panov.model.Menu;
import ru.panov.model.Restaurant;
import ru.panov.repository.MenuRepository;
import ru.panov.repository.RestaurantRepository;
import ru.panov.web.menu.MenuController;

import java.net.URI;

import static ru.panov.util.validation.ValidationUtil.assureIdConsistent;
import static ru.panov.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = RestaurantAdminController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class RestaurantAdminController {
    public static final String REST_URL = "/api/admin/restaurants";
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete restaurant id={}", id);
        restaurantRepository.deleteExisted(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Restaurant> create(@Valid @RequestBody Restaurant restaurant) {
        log.info("create restaurant {}", restaurant);
        checkNew(restaurant);
        restaurantRepository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(RestaurantController.REST_URL + "/{id}")
                .buildAndExpand(restaurant.id()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(restaurant);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@PathVariable int id, @Valid @RequestBody Restaurant restaurant) {
        log.info("update restaurant {} with id={}", restaurant, id);
        assureIdConsistent(restaurant, id);
        restaurantRepository.save(restaurant);
    }


    @PostMapping(value = "/{id}/menus", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Menu> createMenu(@PathVariable int id, @Valid @RequestBody Menu menu) {
        log.info("create menu {} for restaurant id={}", menu, id);
        checkNew(menu);
        menu.setRestaurant(restaurantRepository.getReferenceById(id));
        menuRepository.save(menu);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(MenuController.REST_URL + "/{id}")
                .buildAndExpand(menu.id()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(menu);
    }

}
