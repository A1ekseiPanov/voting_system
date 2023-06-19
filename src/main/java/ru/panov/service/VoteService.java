package ru.panov.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.panov.error.IllegalRequestDataException;
import ru.panov.error.NotFoundException;
import ru.panov.model.Restaurant;
import ru.panov.model.User;
import ru.panov.model.Vote;
import ru.panov.repository.RestaurantRepository;
import ru.panov.repository.VoteRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static ru.panov.util.TimeUtil.TRANSITION_TIME;
import static ru.panov.util.validation.ValidationUtil.checkNew;

@Service
@AllArgsConstructor
public class VoteService {
    private final RestaurantRepository restaurantRepository;
    private final VoteRepository voteRepository;

    @Transactional
    public void create(int restaurantId, User user, Vote vote) {
        Restaurant restaurant = restaurantRepository.getExisted(restaurantId);
        Optional<Vote> oldVote = voteRepository.findByUserAndDateVote(user, LocalDate.now());
        if (oldVote.isEmpty()) {
            checkNew(vote);
            vote.setRestaurant(restaurant);
            vote.setUser(user);
            voteRepository.save(vote);
        } else {
            throw new IllegalRequestDataException("You can only vote once a day");
        }
    }

    @Transactional
    public void update(int id, int restaurantId, User user, Vote vote) {
        Restaurant restaurant = restaurantRepository.getExisted(restaurantId);
        Optional<Vote> oldVote = voteRepository.findByIdAndUserAndDateVote(id, user, LocalDate.now());
        if (oldVote.isPresent()) {
            if (LocalTime.now().isBefore(TRANSITION_TIME)) {
                vote.setId(oldVote.get().getId());
                vote.setRestaurant(restaurant);
                vote.setUser(user);
                voteRepository.save(vote);
            } else {
                throw new IllegalRequestDataException("Vote change time expired at " + TRANSITION_TIME);
            }
        } else {
            throw new NotFoundException("Vote not found");
        }
    }
}
