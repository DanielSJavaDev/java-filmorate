package filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import filmorate.exception.ValidationException;
import filmorate.model.User;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    private final Map<Integer, User> usersData = new HashMap<>();

    @GetMapping
    public List<User> get() {
        log.info("Получен запрос");
        return List.copyOf(usersData.values());
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) throws ValidationException {
        User validUser = validate(user);
        if (validUser.getName().isEmpty()) {
            validUser.setName(validUser.getLogin());
            log.info("Логин присвоен имени");
        }
        usersData.put(validUser.getId(), validUser);
        log.info("Пользователь добавлен");
        return validUser;
    }

    @PutMapping
    public User put(@Valid @RequestBody User user) throws ValidationException {
        User validUser = validate(user);
        usersData.put(validUser.getId(), validUser);
        log.info("Пользователь изменён");
        return validUser;
    }

    public User validate(User user) throws ValidationException {
        if (user.getId() < 0) {
            log.info("Айди отрицательный");
            throw new ValidationException("Incorrect id");
        } else if (user.getLogin().contains(" ")) {
            log.info("Введен некорректный логин");
            throw new ValidationException("Incorrect login");
        } else {
            return user;
        }
    }
}
