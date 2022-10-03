package filmorate.controller;

import filmorate.storage.InMemoryUserStorage;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class UserController {
    InMemoryUserStorage userStorage;

    @GetMapping
    public List<User> get() {

        return userStorage.getData();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) throws ValidationException {

        return userStorage.create(user);
    }

    @PutMapping
    public User put(@Valid @RequestBody User user) throws ValidationException {

        return userStorage.put(user);
    }

}
