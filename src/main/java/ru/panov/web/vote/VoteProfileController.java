package ru.panov.web.vote;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.panov.AuthUser;
import ru.panov.model.Vote;
import ru.panov.repository.VoteRepository;
import ru.panov.service.VoteService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = VoteProfileController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@Tag(name = "Profile Controller", description = "Profile Rest Controller")
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

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete vote auth user by id")
    public void delete(@Parameter(description = "vote_id") @PathVariable int id, @AuthenticationPrincipal AuthUser authUser) {
        log.info("delete vote auth user by id={}", id);
        voteService.delete(id, authUser.getUser());
    }
}