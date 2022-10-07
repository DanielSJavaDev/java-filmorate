package filmorate.controller;

import filmorate.service.UserService;
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
    private final InMemoryUserStorage userStorage;
    private final UserService service;

    @GetMapping
    public List<User> get() {
        return userStorage.getData();
    }
    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable int userId) {
        return service.friends(userId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getOtherFriends(@PathVariable int userId, int otherId) {
        return service.jointFriends(userId, otherId);
    }


    @GetMapping("/{userId}")
    public Optional<User> findById(@PathVariable int userId) {
        return userStorage.getData().stream()
                .filter(x -> x.getId() == userId)
                .findFirst();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) throws ValidationException {
        return userStorage.create(user);
    }

    @PutMapping
    public User put(@Valid @RequestBody User user) throws ValidationException {
        return userStorage.put(user);
    }
    @PutMapping("/{id}/friends/{friendId} ")
    public User addFriend(@PathVariable int userId, int friendId) {
        return service.add(userId, friendId);
    }
    @DeleteMapping("/{id}/friends/{friendId}")
    public User deleteFriend(@PathVariable int userId, int friendId) {
        return service.delete(userId, friendId);
    }

}
