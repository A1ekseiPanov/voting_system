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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.panov.AuthUser;
import ru.panov.model.Vote;
import ru.panov.repository.VoteRepository;
import ru.panov.service.VoteService;

import java.util.List;

import static ru.panov.util.validation.ValidationUtil.assureIdConsistent;

@RestController
@AllArgsConstructor
@RequestMapping(value = VoteProfileController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@Tag(name = "Vote Profile Controller", description = " Vote Profile Rest Controller")
public class VoteProfileController {
    public static final String REST_URL = "/api/profile/votes";

    private final VoteRepository voteRepository;
    private final VoteService voteService;

    @GetMapping
    @Operation(summary = "Get all vote by auth user")
    public List<Vote> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get all vote auth user");
        return voteRepository.getAllByUser(authUser.getUser());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Vote for a restaurant")
    public ResponseEntity<Vote> createVote(@Parameter(description = "restaurant_id") @RequestParam int restaurant_id,
                                           @AuthenticationPrincipal AuthUser authUser,
                                           @Valid @RequestBody Vote vote) {
        log.info("create vote for restaurant id={}", restaurant_id);
        voteService.create(restaurant_id, authUser.getUser(), vote);
                return ResponseEntity.ok(vote);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @Operation(summary = "Update vote")
    public void update(@Parameter(description = "vote_id") @PathVariable int id,
                       @Parameter(description = "restaurant_id")@RequestParam int restaurant_id,
                       @AuthenticationPrincipal AuthUser authUser,
                       @Valid @RequestBody Vote vote) {
        log.info("update vote by id={}", id);
        assureIdConsistent(vote, id);
        voteService.update(id, restaurant_id, authUser.getUser(), vote);
    }
}