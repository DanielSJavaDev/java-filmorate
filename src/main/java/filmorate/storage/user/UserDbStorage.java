package filmorate.storage.user;

import filmorate.exception.ValidationException;
import filmorate.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;


@RequiredArgsConstructor
@Component
@Slf4j
public class UserDbStorage implements DbUser {

    private final JdbcTemplate jdbcTemplate;


    @Override
    public User addUser(User user) {
        User validUser = validate(user);
        String sqlQuery = "insert into users (email, login, name, birthday) values (?, ?, ?, ?, ?);";

        jdbcTemplate.update(sqlQuery, validUser.getEmail(), validUser.getLogin(),
                validUser.getName(), validUser.getBirthday());

        Integer id = jdbcTemplate.queryForObject("select id from users where email = ? and login = ? and birthday = ?;",
                Integer.class, validUser.getEmail(), validUser.getLogin(), validUser.getBirthday());
        validUser.setId(id);

        return validUser;
    }

    @Override
    public User updateUser(User user) {
        User validUser = validate(user);
        String sqlQuery = "update users set email = ?, login = ?, name = ?, birthday = ? where id = ?;";

        jdbcTemplate.update(sqlQuery, validUser.getEmail(), validUser.getLogin(), validUser.getName(),
                validUser.getBirthday(),  validUser.getId());
        return validUser;
    }

    @Override
    public Collection<User> getAllUsers() {
        String sqlQuery = "select * from users;";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> createUser(rs));
    }

    @Override
    public User getUserById(int id) {
        String sqlQuery = "select * from users where id = ?;";
        return jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> createUser(rs), id);
    }

    @Override
    public boolean addFriend(int id, int friendId) {
        String sqlQuery = "insert into friendship_status (user_id, friend_id, status)" +
                "values(?, ?, ?, ?);";
        jdbcTemplate.update(sqlQuery,id, friendId, false);
        return true;
    }

    @Override
    public boolean removeFriend(int id, int friendId) {
        String sqlQuery = "delete from friendship_status where user_id = ? and friend_id = ?;";
        jdbcTemplate.update(sqlQuery,id, friendId);
        return true;
    }

    @Override
    public Collection<User> getFriendList(int id) {
        String sqlQuery = "select * from users where id in(select friend_id from friendship_status where user_id =?)";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> createUser(rs), id);
    }

    @Override
    public Collection<User> getCommonFriends(int id, int otherId) {
        String sqlQuery = "select * from users where id in (select friend_id from friendship_status" +
                " where user_id in (?, ?) group by friend_id having count(*) = ?);";

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
}
