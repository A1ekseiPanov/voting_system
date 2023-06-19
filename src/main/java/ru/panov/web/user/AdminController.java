package ru.panov.web.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.panov.model.User;

import java.util.List;

@RestController
@RequestMapping(value = AdminController.ADMIN_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
@Tag(name = "Users Admin Controller", description = "Users Admin Rest Controller")
public class AdminController extends AbstractUserController {
    static final String ADMIN_REST_URL = "/api/admin/users";

    @GetMapping
    @Operation(summary = "Get all users")
    public List<User> getUsers() {
        log.info("get all users");
        return repository.findAll();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user by id")
    public void delete(@PathVariable int id) {
        log.info("delete user by id={}", id);
        super.delete(id);
    }
}
