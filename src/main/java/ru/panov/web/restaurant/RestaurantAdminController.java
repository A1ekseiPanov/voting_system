package ru.panov.web.restaurant;

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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.panov.model.Restaurant;
import ru.panov.repository.RestaurantRepository;
import ru.panov.util.validation.RestaurantValidator;

import java.net.URI;

import static ru.panov.util.validation.ValidationUtil.assureIdConsistent;
import static ru.panov.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = RestaurantAdminController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
@Tag(name = "Restaurant Admin Controller", description = "Restaurant Admin Rest Controller")
public class RestaurantAdminController {
    public static final String REST_URL = "/api/admin/restaurants";

    private final RestaurantRepository restaurantRepository;
    private final RestaurantValidator uniqueNameValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(uniqueNameValidator);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete restaurant by id")
    @CacheEvict(value = "restaurants", allEntries = true)
    public void delete(@Parameter(description = "restaurant_id") @PathVariable int id) {
        log.info("delete restaurant id={}", id);
        restaurantRepository.deleteExisted(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create restaurant")
    @CacheEvict(value = "restaurants", allEntries = true)
    public ResponseEntity<Restaurant> create(@Valid @RequestBody Restaurant restaurant) {
        log.info("create restaurant");
        checkNew(restaurant);
        restaurantRepository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(RestaurantController.REST_URL + "/{id}")
                .buildAndExpand(restaurant.id()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(restaurant);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @Operation(summary = "Update restaurant by id")
    @CacheEvict(value = "restaurants", allEntries = true)
    public void update(@Parameter(description = "restaurant_id") @PathVariable int id,
                       @Valid @RequestBody Restaurant restaurant) {
        log.info("update restaurant by id={}", id);
        assureIdConsistent(restaurant, id);
        restaurantRepository.getExisted(id);
        restaurantRepository.save(restaurant);
    }
}
