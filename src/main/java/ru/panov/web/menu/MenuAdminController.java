package ru.panov.web.menu;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.panov.model.Dish;
import ru.panov.model.Menu;
import ru.panov.repository.DishRepository;
import ru.panov.repository.MenuRepository;
import ru.panov.web.dish.DishController;

import java.net.URI;

import static ru.panov.util.validation.ValidationUtil.assureIdConsistent;
import static ru.panov.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = MenuAdminController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class MenuAdminController {
    public static final String REST_URL = "/api/admin/menus";
    private final MenuRepository menuRepository;
    private final DishRepository dishRepository;

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete menu id={}", id);
        menuRepository.deleteExisted(id);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@PathVariable int id, @Valid @RequestBody Menu menu) {
        log.info("update menu {} id={}", menu, id);
        Menu oldMenu = menuRepository.getExisted(id);
        assureIdConsistent(menu, id);
        menu.setRestaurant(oldMenu.getRestaurant());
        menuRepository.save(menu);
    }

    @PostMapping(value = "/{id}/dishes", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Dish> createDish(@PathVariable int id, @Valid @RequestBody Dish dish) {
        log.info("create dish {} for menu id= {}", dish, id);
        checkNew(dish);
        dish.setMenu(menuRepository.getReferenceById(id));
        dishRepository.save(dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(DishController.REST_URL + "/{id}")
                .buildAndExpand(dish.id()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(dish);
    }
}
