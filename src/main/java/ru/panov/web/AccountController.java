package ru.panov.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.panov.AuthUser;
import ru.panov.model.User;

@RestController
@RequestMapping("api/account")
@Slf4j
public class AccountController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@AuthenticationPrincipal AuthUser authUser) {
        log.debug("get {}", authUser);
        return authUser.getUser();
    }
}
