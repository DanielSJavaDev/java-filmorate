package filmorate.controller;

import filmorate.exception.ParameterNotFoundException;
//import filmorate.service.UserService;
//import filmorate.storage.user.InMemoryUserStorage;
import filmorate.storage.user.UserDbStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final UserDbStorage userStorage;
    //private final UserService service;

    @GetMapping
    public Collection<User> get() {
        return userStorage.getAllUsers();
    }
    @GetMapping("/{id}/friends")
    public Collection<User> getFriends(@PathVariable("id") int userId) {
        return userStorage.getFriendList(userId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getOtherFriends(@PathVariable("id") int userId,
                                      @PathVariable("otherId") int otherId) {
        return userStorage.getCommonFriends(userId, otherId);
    }

    @GetMapping("/{userId}")
    public User findById(@PathVariable("userId") int userId) throws ParameterNotFoundException {
        return userStorage.getUserById(userId);
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) throws ValidationException {
        return userStorage.addUser(user);
    }

    @PutMapping
    public User put(@Valid @RequestBody User user) throws ValidationException, ParameterNotFoundException {
        return userStorage.updateUser(user);
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public boolean addFriend(@PathVariable("userId") int userId,
                          @PathVariable("friendId") int friendId) throws ParameterNotFoundException {
        return userStorage.addFriend(userId, friendId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public boolean deleteFriend(@PathVariable("userId") int userId,
                             @PathVariable("friendId") int friendId) throws ParameterNotFoundException {
        return userStorage.removeFriend(userId, friendId);
    }
    @PutMapping("/{id}/friends/{friendId}/")
    public boolean setMutualFriendship(@PathVariable int id,
                                       @PathVariable int friendId,
                                       @RequestParam("status") Boolean status){
        return userStorage.setFriendship(id, friendId, status);
    }
    @GetMapping("/friends/")
    public boolean getFriendshipStatus(@RequestParam("userId") int userId, @RequestParam("friendId") int friendId){
        log.info(String.valueOf(getFriendshipStatus(userId, friendId)));
        return userStorage.getFriendship(userId, friendId);
    }
}
