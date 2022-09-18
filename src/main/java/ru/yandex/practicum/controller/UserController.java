package ru.yandex.practicum.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.exception.ValidationException;
import ru.yandex.practicum.model.User;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    private final Map<Integer, User> usersData = new HashMap<>();

    @GetMapping
    public Collection<User> get() {
        log.info("Получен запрос.");
        return usersData.values();
    }

    @PostMapping
    public User post(@Valid @RequestBody User user) throws ru.yandex.practicum.exception.ValidationException {
            if (user.getId() < 0) {
            log.info("Айди отрицательный");
                throw new ru.yandex.practicum.exception.ValidationException("Incorrect id");
            }

            if (user.getLogin().contains(" ")) {
                log.info("Введен некорректный логин");
                throw new ru.yandex.practicum.exception.ValidationException("Incorrect login");
            }
            if (user.getName().isEmpty()) {
                user.setName(user.getLogin());
                log.info("Логин присвоен имени.");
            }

                usersData.put(user.getId(), user);
                log.info("Пользователь добавлен.");
                return user;
    }

    @PutMapping
    public User put(@Valid @RequestBody User user) throws ValidationException {
        if (user.getId() < 0) {
            log.info("Айди отрицательный");
            throw new ru.yandex.practicum.exception.ValidationException("Incorrect id");
        }
        if (user.getLogin().contains(" ")) {
            log.info("Введен некорректный логин");
            throw new ru.yandex.practicum.exception.ValidationException("Incorrect login");
        }

            usersData.put(user.getId(), user);
            log.info("Пользователь изменён.");
            return user;
    }
}
