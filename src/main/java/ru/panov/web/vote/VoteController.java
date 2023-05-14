package ru.panov.web.vote;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.panov.AuthUser;
import ru.panov.model.Vote;
import ru.panov.repository.RestaurantRepository;
import ru.panov.repository.VoteRepository;
import ru.panov.service.VoteService;
import ru.panov.web.restaurant.RestaurantController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@Tag(name = "Restaurant Controller", description = "Restaurant Rest Controller")
public class VoteController {
    private final RestaurantRepository restaurantRepository;
    private final VoteRepository voteRepository;
    private final VoteService voteService;

    @PostMapping(value = "/{id}/votes", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Vote for a restaurant")
    public ResponseEntity<Vote> createVote(@Parameter(description = "restaurant_id") @PathVariable int id,
                                           @AuthenticationPrincipal AuthUser authUser,
                                           @Valid @RequestBody Vote vote) {
        log.info("create vote for restaurant id={}", id);
        voteService.vote(id, authUser.getUser(), vote);
        return ResponseEntity.ok(vote);
    }

    @GetMapping("/{id}/votes")
    @Operation(summary = "Get all vote at restaurant id")
    public List<Vote> getAllVoteAtRestaurant(@PathVariable int id) {
        log.info("get all vote at restaurant id={}", id);
        return voteRepository.getAllByRestaurantId(restaurantRepository.getExisted(id).id());
    }
}
