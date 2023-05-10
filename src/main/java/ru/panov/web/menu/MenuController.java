package ru.panov.web.menu;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.panov.model.Menu;
import ru.panov.repository.MenuRepository;

@RestController
@RequestMapping(value = MenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class MenuController {
    public static final String REST_URL = "/api/menus";
    private final MenuRepository menuRepository;

    @GetMapping("/{id}")
    public Menu get(@PathVariable int id) {
        log.info("get menu id={}", id);
        return menuRepository.getExisted(id);
    }
}
