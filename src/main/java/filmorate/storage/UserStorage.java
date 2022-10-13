package filmorate.storage;

import filmorate.exception.ParameterNotFoundException;
import filmorate.exception.ValidationException;
import filmorate.model.User;

import java.util.List;
import java.util.Map;

public interface UserStorage {
    User create(User user) throws ValidationException;
    User put(User user) throws ValidationException;
    User validate(User user) throws ValidationException;
    List<User> getData();
    Map<Integer, User> getUsersData();

    User getUser(int id) throws ParameterNotFoundException;
}
