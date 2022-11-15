package filmorate.storage.user;

import filmorate.exception.ValidationException;
import filmorate.exception.ValidationOfUserFound;
import filmorate.model.User;
import filmorate.validate.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;


@RequiredArgsConstructor
@Component
@Slf4j
public class UserDbStorage implements DbUser {

    private final JdbcTemplate jdbcTemplate;
    private final UserValidator validator;

    @Override
    public User addUser(User user) {
        validator.validateUserToCreate(user);

        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
            log.info("Логин присвоен имени");
        }

        String sqlQuery = "insert into users (email, login, name, birthday) values (?, ?, ?, ?);";

        jdbcTemplate.update(sqlQuery, user.getEmail(), user.getLogin(),
                user.getName(), user.getBirthday());

        Integer id = jdbcTemplate.queryForObject("select id from users where email = ? and login = ? and birthday = ?;",
                Integer.class, user.getEmail(), user.getLogin(), user.getBirthday());

            user.setId(id);

        return user;
    }

    @Override
    public User updateUser(User user) throws ValidationOfUserFound {
        validator.validateUserToUpdate(user);

        String sqlQuery = "update users set email = ?, login = ?, name = ?, birthday = ? where id = ?;";

        jdbcTemplate.update(sqlQuery, user.getEmail(), user.getLogin(), user.getName(),
                user.getBirthday(),  user.getId());
        return user;
    }

    @Override
    public Collection<User> getAllUsers() {
        String sqlQuery = "select * from users;";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> createUser(rs));
    }

    @Override
    public User getUserById(int id) {
        validator.identifyUserId(id);
        String sqlQuery = "select * from users where id = ?;";
        return jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> createUser(rs), id);
    }

    @Override
    public boolean addFriend(int id, int friendId) {
        validator.identifyUserId(friendId);
        validator.identifyUserId(id);
        String sqlQuery = "insert into friendlist (user_id, friend_id) values(?, ?);";
        jdbcTemplate.update(sqlQuery, id, friendId);
        return true;
    }

    @Override
    public boolean removeFriend(int id, int friendId) {
        validator.identifyUserId(friendId);
        validator.identifyUserId(id);
        String sqlQuery = "delete from friendlist where user_id = ? and friend_id = ?;";
        jdbcTemplate.update(sqlQuery,id, friendId);
        return true;
    }

    @Override
    public Collection<User> getFriendList(int id) {
        validator.identifyUserId(id);
        String sqlQuery = "select * from users where id in(select friend_id from friendlist where user_id =?)";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> createUser(rs), id);
    }

    @Override
    public Collection<User> getCommonFriends(int id, int otherId) {
        String sqlQuery = "select * from users where id in (select friend_id from friendlist where user_id in (?, ?) " +
                "group by friend_id having count(*) = ?);";

        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> createUser(rs), id, otherId, 2);
    }

    @Override
    public Boolean getFriendship(int userId, int friendId) {
        String sqlQuery = "select status from friendship_status where user_id = ? and friend_id = ?;";
        return jdbcTemplate.queryForObject(sqlQuery, Boolean.class, userId, friendId);
    }

    @Override
    public boolean setFriendship(int id, int friendId, Boolean status) {
        String sqlQuery = "update friendship_status set status = ? where user_id = ? and friend_id = ?;";
        jdbcTemplate.update(sqlQuery, status, id, friendId);
        return true;
    }

    @Override
    public User createUser(ResultSet rs) throws SQLException {
        return User.builder()
                .id(rs.getInt("id"))
                .email(rs.getString("email"))
                .login(rs.getString("login"))
                .name(rs.getString("name"))
                .birthday(LocalDate.parse(rs.getString("birthday")))
                .build();
    }
}