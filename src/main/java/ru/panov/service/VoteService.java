package ru.panov.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.panov.error.IllegalRequestDataException;
import ru.panov.model.Restaurant;
import ru.panov.model.User;
import ru.panov.model.Vote;
import ru.panov.repository.RestaurantRepository;
import ru.panov.repository.VoteRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static ru.panov.util.TimeUtil.transitionTime;
import static ru.panov.util.validation.ValidationUtil.checkNew;

@Service
@AllArgsConstructor
public class VoteService {
    private final RestaurantRepository restaurantRepository;
    private final VoteRepository voteRepository;

    @Transactional
    public void vote(int restaurantId, User user, Vote vote) {
        Restaurant restaurant = restaurantRepository.getReferenceById(restaurantId);
        Optional<Vote> oldVote = voteRepository.findByUserAndDateVote(user, LocalDate.now());
        if (oldVote.isPresent()) {
            if (LocalTime.now().isBefore(transitionTime)) {
                vote.setId(oldVote.get().getId());
                vote.setRestaurant(restaurant);
                vote.setUser(user);
                voteRepository.save(vote);
            } else {
                throw new IllegalRequestDataException("Vote change time expired at 11:00 AM");
            }
        } else {
            checkNew(vote);
            vote.setRestaurant(restaurant);
            vote.setUser(user);
            voteRepository.save(vote);
        }
    }
}
