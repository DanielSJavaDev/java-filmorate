package filmorate.storage;

import filmorate.exception.ValidationException;
import filmorate.model.User;

import java.util.List;
import java.util.Map;

public interface UserStorage {
    User create(User user) throws ValidationException;
    User put(User user) throws ValidationException;
    User delete(User user);
    User validate(User user) throws ValidationException;
    List<User> getData();
    Map<Integer, User> getUsersData();
}
