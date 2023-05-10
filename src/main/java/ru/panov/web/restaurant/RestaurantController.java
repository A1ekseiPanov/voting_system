package ru.panov.web.restaurant;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.panov.AuthUser;
import ru.panov.model.Menu;
import ru.panov.model.Restaurant;
import ru.panov.model.Vote;
import ru.panov.repository.MenuRepository;
import ru.panov.repository.RestaurantRepository;
import ru.panov.service.VoteService;
import ru.panov.web.VoteController;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class RestaurantController {
    public static final String REST_URL = "/api/restaurants";
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final VoteService voteService;

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("get all restaurants");
        return restaurantRepository.findAll();
    }

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        log.info("get restaurant id={}", id);
        return restaurantRepository.getExisted(id);
    }

    @GetMapping("/{id}/menus")
    public List<Menu> getMenus(@PathVariable int id) {
        log.info("get menu restaurant id {}", id);
        return menuRepository.getMenuByRestaurantIdAndDateCreateMenu(id, LocalDate.now());
    }

    @PostMapping(value = "/{id}/votes", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Vote> vote(@PathVariable int id,
                                           @AuthenticationPrincipal AuthUser authUser,
                                           @Valid @RequestBody Vote vote) {
        log.info("create vote {} for restaurant id={}", vote, id);
        voteService.vote(id,authUser.getUser(),vote);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(VoteController.REST_URL + "/{id}")
                .buildAndExpand(vote.id()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(vote);
    }
}
