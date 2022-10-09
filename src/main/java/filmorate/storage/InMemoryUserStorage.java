package filmorate.storage;

import filmorate.exception.ParameterNotFoundException;
import filmorate.exception.ValidationException;
import filmorate.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> usersData = new HashMap<>();
    @Override
    public User create(User user) throws ValidationException {
        User validUser = validate(user);
        if (validUser.getName().isEmpty()) {
            validUser.setName(validUser.getLogin());
            log.info("Логин присвоен имени");
        }
        usersData.put(validUser.getId(), validUser);
        log.info("Пользователь добавлен");
        return validUser;
    }

    @Override
    public User put(User user) throws ValidationException, ParameterNotFoundException {
        if (!usersData.containsKey(user.getId())) {
            throw new ParameterNotFoundException("user not found");
        } else {
            User validUser = validate(user);
            usersData.put(validUser.getId(), validUser);
            log.info("Пользователь изменён");
            return validUser;
        }

    }

    @Override
    public List<User> getData() {
        log.info("Получен запрос");
        return List.copyOf(usersData.values());
    }

    @Override
    public User getUser(int id) throws ParameterNotFoundException {
        if (!usersData.containsKey(id)) {
            throw new ParameterNotFoundException("user not found");
        } else {
            return usersData.get(id);
        }
    }

    @Override
    public User validate(User user) throws ValidationException {
        if (user.getId() < 0) {
            log.info("Айди отрицательный");
            throw new ValidationException("Incorrect id");
        } else if (user.getLogin().contains(" ")) {
            log.info("Введен некорректный логин");
            throw new ValidationException("Incorrect login");
        } else {
            return user;
        }
    }
    @Override
    public Map<Integer, User> getUsersData() {
        return usersData;
    }
}
