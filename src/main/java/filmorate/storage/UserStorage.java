package filmorate.storage;

import filmorate.exception.ValidationException;
import filmorate.model.User;

import java.util.List;

public interface UserStorage {
    User create(User user) throws ValidationException;
    User put(User user) throws ValidationException;
    User delete(User user);
    User validate(User user) throws ValidationException;
    List<User> getData();
}
