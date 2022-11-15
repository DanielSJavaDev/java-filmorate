package filmorate.storage.film;

import filmorate.dao.GenreDao;
import filmorate.dao.MpaDaoImp;
import filmorate.exception.ParameterNotFoundException;
import filmorate.exception.ValidationException;
import filmorate.model.Film;
import filmorate.model.Genre;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class FilmDbStorage implements DbFilm {

    private final GenreDao genreDao;
    private final JdbcTemplate jdbcTemplate;
    private final MpaDaoImp mpa;


    public Film add(Film film) throws ValidationException {
        Film validFilm = validate(film);

        String sqlQuery = "insert into films (name, description, release_date, duration, mpa)" +
                "values (?, ?, ?, ?, ?);";

        jdbcTemplate.update(sqlQuery, validFilm.getName(), validFilm.getDescription(), validFilm.getReleaseDate(),
                validFilm.getDuration(), validFilm.getMpa().getId());
        Integer id = jdbcTemplate.queryForObject("SELECT id from films order by id desc limit 1;", Integer.class);
        validFilm.setId(id);
        String sqlQuery2 = "insert into film_genre (film_id, genre_id) values (?, ?);";

        if (validFilm.getGenres() == null || validFilm.getGenres().size() == 0) {
            return validFilm;
        }

        validFilm.getGenres().forEach(genre -> jdbcTemplate.update(sqlQuery2, validFilm.getId(), genre.getId()));
        return validFilm;
    }

    @Override
    public Film put(Film film) throws ValidationException {
        Film validFilm = validate(film);

        String sqlQuery = "update films set name = ?, release_date = ?, description = ?," +
                " duration = ?, mpa = ? where id = ?;";

        jdbcTemplate.update(sqlQuery, validFilm.getName(), validFilm.getReleaseDate(), validFilm.getDescription(),
                validFilm.getDuration(), validFilm.getMpa().getId(),  validFilm.getId());

        updateGenre(validFilm);
        return validFilm;
    }

    @Override
    public Film remove(Film film) {
        Film validFilm = validate(film);
        String sqlQuery = "delete from films where id = ?";
        jdbcTemplate.update(sqlQuery, validFilm.getId());
        log.info("film removed");
        return validFilm;
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
                .genres(new ArrayList<>(createGenreList(rs)))
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

        film.getGenres().forEach(genre -> jdbcTemplate.update(sqlQuery2, film.getId(), genre.getId()));
    }

    @Override
    public boolean likeFilm(int filmId, int userId) {
        String sqlQuery = "insert into likes (film_id, user_id) values (?, ?);";
        jdbcTemplate.update(sqlQuery, filmId, userId);
        return true;
    }

    @Override
    public boolean unlikeFilm(int filmId, int userId) {
        String sqlQuery = "delete from likes where film_id = ? and user_id = ?";
        jdbcTemplate.update(sqlQuery, filmId, userId);
        return true;
    }

    @Override
    public Integer getLikesCount(int filmId) {
        String sqlQuery = "select count(*) from likes where film_id = ?;";
        return jdbcTemplate.queryForObject(sqlQuery, Integer.class, filmId);
    }

    @Override
    public List<Film> getTop(int size) {
        return getData().stream().limit(size)
                .sorted(Comparator.comparingInt(Film::getRate).reversed()).collect(Collectors.toList());
    }
}
