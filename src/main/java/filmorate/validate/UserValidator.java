package filmorate.validate;

import filmorate.exception.ParameterNotFoundException;
import filmorate.exception.ValidationException;
import filmorate.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserValidator {
    private final JdbcTemplate jdbcTemplate;

    public void validateUserToCreate(User user) {
        validateLogin(user);
        validateEmail(user);
        validateBirthday(user);
    }

    public void validateUserToUpdate(User user) throws ParameterNotFoundException {
        identifyUser(user);
        validateLogin(user);
        validateEmail(user);
        validateBirthday(user);
        validateUserId(user);
    }

    public void validateEmail(User user) {
        boolean isValid = user.getEmail().contains("@")
                && user.getEmail().contains(".")
                && !user.getEmail().contains(" ");
        log.info("Email validation: {}", isValid);
        if (!isValid) throw new ValidationException("Email is not correct");
    }

    public void validateLogin(User user){
        boolean isCorrectLogin = !user.getLogin().isBlank() && !user.getLogin().contains(" ");
        log.info("Login validation: {}", isCorrectLogin);
        if (!isCorrectLogin) throw new ValidationException("Login should not be empty or contain spaces");
    }

    public void validateBirthday(User user){
        boolean isCorrectBirthday = user.getBirthday().isBefore(LocalDate.now());
        log.info("Birthday validation: {}", user.getBirthday());
        if (!isCorrectBirthday) throw new ValidationException("Birthday cannot follow by current date");
    }


    public void identifyUser(User user) {
        String sqlQuery = "select count(*) from users where id = ?;";
        boolean isValid = jdbcTemplate.queryForObject(sqlQuery, Integer.class, user.getId())==1;
        log.info("User identification: "+isValid);
        if (!isValid) throw new ParameterNotFoundException("User with ID " + user.getId() + " is not found");

    }

    public void validateUserId(User user){
        Integer userId = user.getId();
        boolean isValid = userId != null
                && userId > 0;
        if (!isValid) throw new ValidationException("User ID is not correct");
    }

    public void identifyUserId(int id) {
        String sqlQuery = "select count(*) from users where id = ?;";
        boolean isValid = jdbcTemplate.queryForObject(sqlQuery, Integer.class, id)==1;
        log.info("User identification by ID: "+isValid);
        if (!isValid) throw new ParameterNotFoundException("User with ID " + id + " is not found");

    }
}
