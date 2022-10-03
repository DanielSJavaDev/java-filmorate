package filmorate.service;

import filmorate.model.User;
import filmorate.storage.InMemoryUserStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class UserService {
    InMemoryUserStorage userStorage;
    public User add(int userId, int friendId) {
        if(!userStorage.usersData.containsKey(userId)) {
            throw new ValidationException("can't find user");
        } else if (!userStorage.usersData.containsKey(friendId)){
            throw new ValidationException("can't find friend");
        } else {
            userStorage.usersData.get(userId).getFriends().add(friendId);
            log.info("friend added");
        }
        return userStorage.usersData.get(friendId);
    }
    public User delete(int userId, int friendId) {
        if(!userStorage.usersData.containsKey(userId)) {
            throw new ValidationException("can't find user");
        } else if (!userStorage.usersData.containsKey(friendId)){
            throw new ValidationException("can't find friend");
        } else {
            userStorage.usersData.get(userId).getFriends().remove(friendId);
            log.info("friend deleted");
        }
        return userStorage.usersData.get(friendId);
    }

    public List<User> jointFriends(int userId, int friendId) {
        List<User> friends = new ArrayList<>();
        User user = userStorage.usersData.get(userId);
        User friend = userStorage.usersData.get(friendId);
        if(!userStorage.usersData.containsKey(userId)) {
            throw new ValidationException("can't find user");
        } else if (!userStorage.usersData.containsKey(friendId)){
            throw new ValidationException("can't find friend");
        } else {
            for (Integer id : friend.getFriends()) {
                if (user.getFriends().contains(id)) {
                    friends.add(userStorage.usersData.get(id));
                }
                log.info("friends returned");
            }
        }
        return friends;
    }
}
