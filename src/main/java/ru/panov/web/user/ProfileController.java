package ru.panov.web.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.panov.AuthUser;
import ru.panov.model.Role;
import ru.panov.model.User;

import java.net.URI;
import java.util.Set;

import static ru.panov.util.validation.ValidationUtil.assureIdConsistent;
import static ru.panov.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = ProfileController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@Tag(name = "Profile Controller", description = "Profile Rest Controller")
public class ProfileController extends AbstractUserController {
    static final String REST_URL = "/api/profile";

    @GetMapping
    @Operation(summary = "Get auth user")
    public User get(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get {}", authUser);
        return authUser.getUser();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete auth user")
    @CacheEvict(value = {"users", "admin_users"}, key = "#authUser.username")
    public void delete(@AuthenticationPrincipal AuthUser authUser) {
        log.info("delete {}", authUser);
        super.delete(authUser.id());
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register profile")
    @CacheEvict(value = {"users", "admin_users"},allEntries = true)
    public ResponseEntity<User> register(@Valid @RequestBody User user) {
        log.info("register {}", user);
        checkNew(user);
        user.setRoles(Set.of(Role.USER));
        repository.prepareAndSave(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(user.id()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(user);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @Operation(summary = "Update auth user")
    @CacheEvict(value = {"users", "admin_users"}, key = "#authUser.username")
    public void update(@RequestBody @Valid User user, @AuthenticationPrincipal AuthUser authUser) {
        log.info("update {} with id={}", user, authUser.id());
        assureIdConsistent(user, authUser.id());
        if (user.getPassword() == null) {
            user.setPassword(authUser.getUser().getPassword());
        }
        user.setRoles(authUser.getUser().getRoles());
        repository.prepareAndSave(user);
    }
}
