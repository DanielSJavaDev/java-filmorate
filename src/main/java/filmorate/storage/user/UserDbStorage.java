package filmorate.storage.user;

import filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface UserDbStorage {
    User addUser(User user);
    User updateUser(User user);
    List<User> getAllUsers();
    User getUserById(int id);
    Boolean addFriend(int id, int friendId);
    Boolean removeFriend(int id, int friendId);
    List<User> getFriendList(int id);
    List<User> getCommonFriends(int id, int otherId);
    Boolean getFriendship(int userId, int friendId);
    Boolean setFriendship(int id, int friendId, Boolean status);
    User createUser(ResultSet rs) throws SQLException;

}
