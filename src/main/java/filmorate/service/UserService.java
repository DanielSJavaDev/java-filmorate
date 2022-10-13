package filmorate.service;

import filmorate.exception.ParameterNotFoundException;
import filmorate.model.User;
import filmorate.storage.InMemoryUserStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final InMemoryUserStorage userStorage;

    public User add(int userId, int friendId) {
        if(!userStorage.getUsersData().containsKey(userId)) {
            throw new ParameterNotFoundException("can't find user");
        } else if (!userStorage.getUsersData().containsKey(friendId)){
            throw new ParameterNotFoundException("can't find friend");
        } else {
            addLike(userId, friendId);
            log.info("friend added");
        }
        return userStorage.getUsersData().get(friendId);
    }

    public User delete(int userId, int friendId) {
        if(!userStorage.getUsersData().containsKey(userId)) {
            throw new ParameterNotFoundException("can't find user");
        } else if (!userStorage.getUsersData().containsKey(friendId)){
            throw new ParameterNotFoundException("can't find friend");
        } else {
            deleteLike(userId, friendId);
            log.info("friend deleted");
        }
        return userStorage.getUsersData().get(friendId);
    }

    public List<User> jointFriends(int userId, int friendId) {
        List<User> friends = new ArrayList<>();
        User user = userStorage.getUsersData().get(userId);
        User friend = userStorage.getUsersData().get(friendId);
        if(!userStorage.getUsersData().containsKey(userId)) {
            throw new ParameterNotFoundException("can't find user");
        } else if (!userStorage.getUsersData().containsKey(friendId)){
            throw new ParameterNotFoundException("can't find friend");
        } else {
            for (Integer id : friend.getFriends()) {
                if (user.getFriends().contains(id)) {
                    friends.add(userStorage.getUsersData().get(id));
                }
                log.info("friends returned");
            }
        }
        return friends;
    }

    public List<User> friends(int userId) {
        List<User> friends = new ArrayList<>();
        User user = userStorage.getUsersData().get(userId);
        if (!userStorage.getUsersData().containsKey(userId)) {
            throw new ParameterNotFoundException("can't find user");
        } else {
            for (Integer id : userStorage.getUsersData().keySet()) {
                if (user.getFriends().contains(id)) {
                    friends.add(userStorage.getUsersData().get(id));
                }
                log.info("friends returned");
            }
        }
        return friends;
    }

    private void addLike(int userId, int friendId) {
        userStorage.getUsersData().get(userId).getFriends().add(friendId);
        userStorage.getUsersData().get(friendId).getFriends().add(userId);
    }

    private void deleteLike(int userId, int friendId) {
        userStorage.getUsersData().get(userId).getFriends().remove(friendId);
    }
}
