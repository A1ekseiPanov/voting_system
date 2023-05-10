package ru.panov.web.dish;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.panov.model.Dish;
import ru.panov.repository.DishRepository;

@RestController
@RequestMapping(value = DishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class DishController {
    public static final String REST_URL = "/api/dishes";
    private final DishRepository dishRepository;

    @GetMapping("/{id}")
    public Dish get(@PathVariable int id) {
        log.info("get dish id={}", id);
        return dishRepository.getExisted(id);
    }
}
