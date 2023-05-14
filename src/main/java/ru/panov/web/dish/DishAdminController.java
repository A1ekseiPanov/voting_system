package ru.panov.web.dish;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.panov.model.Dish;
import ru.panov.repository.DishRepository;
import ru.panov.service.DishService;

import static ru.panov.util.validation.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = DishAdminController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
@Tag(name = "Admin Controller", description = "Admin Rest Controller")
public class DishAdminController {
    public static final String REST_URL = "/api/admin";

    private final DishRepository dishRepository;
    private final DishService dishService;

    @DeleteMapping("/dishes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete dish by id")
    @CacheEvict(value = {"all_menus", "menus"}, allEntries = true)
    public void delete(@Parameter(description = "dish_id") @PathVariable int id) {
        log.info("delete dish id={}", id);
        dishRepository.deleteExisted(id);
    }

    @PutMapping(value = "/dishes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @Operation(summary = "Update dish bu id")
    @CacheEvict(value = {"all_menus", "menus"}, allEntries = true)
    public void update(@Parameter(description = "dish_id") @PathVariable int id, @Valid @RequestBody Dish dish) {
        log.info("update dish by id={}", id);
        Dish oldDish = dishRepository.getExisted(id);
        assureIdConsistent(dish, id);
        dish.setMenu(oldDish.getMenu());
        dishRepository.save(dish);
    }

    @PostMapping(value = "/menus/{id}/dishes", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create dish by menu id")
    @CacheEvict(value = {"all_menus", "menus"}, allEntries = true)
    public ResponseEntity<Dish> create(@Parameter(description = "menu_id") @PathVariable int id,
                                       @Valid @RequestBody Dish dish) {
        log.info("create dish for menu id={}", id);
        dishService.create(id, dish);
        return ResponseEntity.ok(dish);
    }
}
