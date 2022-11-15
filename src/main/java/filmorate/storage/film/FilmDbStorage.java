package filmorate.storage.film;

import filmorate.dao.GenreDao;
import filmorate.dao.MpaDaoImp;
import filmorate.exception.ParameterNotFoundException;
import filmorate.exception.ValidationException;
import filmorate.model.Film;
import filmorate.model.Genre;
import filmorate.validate.FilmValidator;
import filmorate.validate.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmDbStorage implements DbFilm {

    private final GenreDao genreDao;
    private final JdbcTemplate jdbcTemplate;
    private final MpaDaoImp mpa;

    private final FilmValidator validator;

    private final UserValidator userValidator;


    public Film add(Film film) throws ValidationException {
        validator.validateFilmToCreate(film);
        String sqlQuery = "insert into films (name, description, release_date, duration, mpa)" +
                "values (?, ?, ?, ?, ?);";

        jdbcTemplate.update(sqlQuery, film.getName(), film.getDescription(), film.getReleaseDate(),
                film.getDuration(), film.getMpa().getId());
        Integer id = jdbcTemplate.queryForObject("SELECT id from films order by id desc limit 1;", Integer.class);
        film.setId(id);
        String sqlQuery2 = "insert into film_genre (film_id, genre_id) values (?, ?);";

        if (film.getGenres() == null || film.getGenres().size() == 0) {
            return film;
        }

        film.getGenres().forEach(genre -> jdbcTemplate.update(sqlQuery2, film.getId(), genre.getId()));
        return film;
    }

    @Override
    public Film put(Film film) throws ValidationException {
        validator.validateFilmToUpdate(film);

        String sqlQuery = "update films set name = ?, release_date = ?, description = ?," +
                " duration = ?, mpa = ? where id = ?;";

        jdbcTemplate.update(sqlQuery, film.getName(), film.getReleaseDate(), film.getDescription(),
                film.getDuration(), film.getMpa().getId(),  film.getId());

        updateGenre(film);
        return film;
    }

    @Override
    public Film remove(Film film) {
        validator.identifyFilm(film);
        String sqlQuery = "delete from films where id = ?";
        jdbcTemplate.update(sqlQuery, film.getId());
        log.info("film removed");
        return film;
    }

    @Override
    public Film validate(Film film) throws ValidationException, ParameterNotFoundException {
        if (film.getId() < 0) {
            log.info("Айди отрицательный");
            throw new ValidationException("Incorrect id");
        } else if (film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
            log.info("Введена некорректна дата");
            throw new ValidationException("Incorrect date");
        } else {
            return film;
        }
    }

    @Override
    public List<Film> getData() {
        String sqlQuery = "select * from films;";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> createFilm(rs));
    }

    @Override
    public Film getFilm(Integer id) throws ParameterNotFoundException {
        validator.identifyFilmId(id);
        String sqlQuery = "select * from films where id = ?;";
        return jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> createFilm(rs), id);
    }

    @Override
    public Film createFilm(ResultSet rs) throws SQLException {
        return Film.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .duration(rs.getInt("duration"))
                .Mpa(mpa.getMpa(rs.getInt("mpa")))
                .genres(new HashSet<>(createGenreList(rs)))
                .releaseDate(LocalDate.parse(rs.getString("release_date")))
                .likesCount(getLikesCount(rs.getInt("id")))
                .build();
    }
    @Override
    public List<Genre> createGenreList(ResultSet rs) throws SQLException {
        String sqlQuery = "select genre_id from film_genre where film_id = ?;";
        return new ArrayList<>(jdbcTemplate.query(sqlQuery,
                (rsInGenres, rowNum) -> genreDao.getGenreById(rsInGenres.getInt("genre_id")),
                rs.getLong("id")));
    }

    @Override
    public void updateGenre(Film film) {
        String delete = "delete from film_genre where film_id =?;";
        jdbcTemplate.update(delete, film.getId());
        String sqlQuery2 = "insert into film_genre (film_id, genre_id) values (?, ?);";
        if (film.getGenres() == null || film.getGenres().size() == 0) return;
        film.getGenres().forEach(genre -> jdbcTemplate.update(sqlQuery2, film.getId(), genre.getId()));
    }

    @Override
    public boolean likeFilm(int filmId, int userId) {
        validator.identifyFilmId(filmId);
        userValidator.identifyUserId(userId);
        String sqlQuery = "insert into liked_films (film_id, user_id) values (?, ?);";
        jdbcTemplate.update(sqlQuery, filmId, userId);

        return true;
    }

    @Override
    public boolean unlikeFilm(int filmId, int userId) {
        validator.identifyFilmId(filmId);
        userValidator.identifyUserId(userId);
        String sqlQuery = "delete from liked_films where film_id = ? and user_id = ?";
        jdbcTemplate.update(sqlQuery, filmId, userId);

        return true;
    }

    @Override
    public Integer getLikesCount(int filmId) {
        validator.identifyFilmId(filmId);
        String sqlQuery = "select count(*) from liked_films where film_id = ?;";
        return jdbcTemplate.queryForObject(sqlQuery, Integer.class, filmId);
    }

    @Override
    public List<Film> getTop(int size) {
        List<Film> films = getData()
                .stream()
                .sorted((film2, film1) -> film1.getLikesCount().compareTo(film2.getLikesCount()))
                .limit(size)
                .collect(Collectors.toList());

        Collections.reverse(films);

        return new ArrayList<>(films);
    }
}
