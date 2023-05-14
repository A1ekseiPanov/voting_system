package ru.panov.web.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.panov.model.User;

import java.util.List;

@RestController
@RequestMapping(value = AdminController.ADMIN_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
@Tag(name = "Admin Controller", description = "Admin Rest Controller")
public class AdminController extends AbstractUserController {
    static final String ADMIN_REST_URL = "/api/admin/profile";

    @GetMapping
    @Operation(summary = "Get all users")
    @Cacheable("admin_users")
    public List<User> getUsers() {
        log.info("get all users");
        return repository.findAll();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user by id")
    @CacheEvict(value = "admin_users", allEntries = true)
    public void delete(@PathVariable int id) {
        log.info("delete user by id={}", id);
        super.delete(id);
    }
}
