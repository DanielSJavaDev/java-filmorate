package filmorate.storage.user;

import filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public interface DbUser {
    User addUser(User user);
    User updateUser(User user);
    Collection<User> getAllUsers();
    User getUserById(int id);
    boolean addFriend(int id, int friendId);
    boolean removeFriend(int id, int friendId);
    Collection<User> getFriendList(int id);
    Collection<User> getCommonFriends(int id, int otherId);
    Boolean getFriendship(int userId, int friendId);
    boolean setFriendship(int id, int friendId, Boolean status);
    User createUser(ResultSet rs) throws SQLException;

}
