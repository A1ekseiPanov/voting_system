package ru.panov.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.panov.model.User;
import ru.panov.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
    Optional<Vote> findByUserAndDateVote(User user, LocalDate localDate);

    Optional<Vote> getVoteByIdAndUser(int id, User user);

    List<Vote> getAllByUser(User user);

    List<Vote> getAllByRestaurantId(int restaurantId);
}
