package ru.panov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;
import ru.panov.model.User;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User,Integer> {

    @RestResource(rel = "by_email", path = "by_email")
    Optional<User> findByEmailIgnoreCase(String email);

    @RestResource(rel = "by_surname", path = "by_surname")
    List<User> findBySurnameContainingIgnoreCase(String surname);
}
