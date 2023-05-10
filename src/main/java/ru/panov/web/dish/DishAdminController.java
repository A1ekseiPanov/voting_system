package ru.panov.web.dish;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.panov.model.Dish;
import ru.panov.repository.DishRepository;

import static ru.panov.util.validation.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = DishAdminController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class DishAdminController {
    public static final String REST_URL = "/api/admin/dishes";
    private final DishRepository dishRepository;

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete dish id={}", id);
        dishRepository.deleteExisted(id);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@PathVariable int id, @Valid @RequestBody Dish dish) {
        log.info("update dish {} id={}", dish, id);
        Dish oldDish = dishRepository.getExisted(id);
        assureIdConsistent(dish, id);
        dish.setMenu(oldDish.getMenu());
        dishRepository.save(dish);
    }
}
