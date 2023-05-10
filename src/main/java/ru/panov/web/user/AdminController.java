package ru.panov.web.user;

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
public class AdminController extends AbstractUserController {
    static final String ADMIN_REST_URL = "/api/admin/profile";

    @GetMapping
    public List<User> getUsers() {
        log.info("get all users");
        return repository.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        log.info("delete user by id={}", id);
        super.delete(id);
    }
}
