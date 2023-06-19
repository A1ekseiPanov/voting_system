package ru.panov.web.menu;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.panov.model.Menu;
import ru.panov.repository.MenuRepository;
import ru.panov.service.MenuService;

import java.net.URI;

import static ru.panov.util.validation.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = MenuAdminController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
@Tag(name = "Menu Admin Controller", description = "Menu Admin Rest Controller")
public class MenuAdminController {
    public static final String REST_URL = "/api/admin";

    private final MenuRepository menuRepository;
    private final MenuService menuService;

    @GetMapping("/menus/{id}")
    @Operation(summary = "Get menu by id")
    public Menu get(@Parameter(description = "menu_id") @PathVariable int id) {
        log.info("get menu by id={}", id);
        return menuRepository.getExisted(id);
    }

    @DeleteMapping("/menus/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete menu by id")
    @CacheEvict(value = {"all_menus"}, allEntries = true)
    public void delete(@Parameter(description = "menu_id") @PathVariable int id) {
        log.info("delete menu id={}", id);
        menuRepository.deleteExisted(id);
    }

    @PutMapping(value = "/menus/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @Operation(summary = "Update menu by id")
    @CacheEvict(value = {"all_menus"}, allEntries = true)
    public void update(@Parameter(description = "menu_id") @PathVariable int id,
                       @Valid @RequestBody Menu menu) {
        log.info("update menu by id={}", id);
        assureIdConsistent(menu, id);
        Menu oldMenu = menuRepository.getExisted(id);
        menu.setRestaurant(oldMenu.getRestaurant());
        menuRepository.save(menu);
    }

    @PostMapping(value = "/restaurants/{id}/menus", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create menu by restaurant id")
    @CacheEvict(value = {"all_menus"}, allEntries = true)
    public ResponseEntity<Menu> create(@Parameter(description = "restaurant_id") @PathVariable("id") int restaurant_id,
                                       @Valid @RequestBody Menu menu) {
        log.info("create menu for restaurant id={}", restaurant_id);
        menuService.create(restaurant_id, menu, menu.getOfferDate());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(MenuAdminController.REST_URL + "menus/{id}")
                .buildAndExpand(menu.id()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(menu);
    }
}
