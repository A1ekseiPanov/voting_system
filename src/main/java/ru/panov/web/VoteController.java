package ru.panov.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.panov.model.Vote;
import ru.panov.repository.VoteRepository;

@RestController
@AllArgsConstructor
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class VoteController {

    public static final String REST_URL = "/api/votes";

    private final VoteRepository voteRepository;

    @GetMapping("/{id}")
    public Vote get(@PathVariable int id) {
        log.info("get vote id={}", id);
        return voteRepository.getExisted(id);
    }
}
