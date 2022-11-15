package filmorate.storage.film;

import filmorate.exception.ParameterNotFoundException;
import filmorate.exception.ValidationException;
import filmorate.model.Film;
import filmorate.model.Genre;
import filmorate.service.GenreService;
import filmorate.service.MpaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;


import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
/*
@Slf4j
@Component
@RequiredArgsConstructor
public class InMemoryFilmStorage implements FilmStorage {
    private Integer filmId = 1;
    private final Map<Integer, Film> filmData = new HashMap<>();
    private final Set<Film> filmRating = new HashSet<>();
    JdbcTemplate jdbcTemplate;
    MpaService mpaService;
    GenreService genreService;

    @Override
    public Film create(Film film) throws ValidationException {
        Film validFilm = validate(film);
        if (filmData.containsKey(validFilm.getId())) {
            put(validFilm);
            log.info("Фильм изменён");
            return validFilm;
        }
        if (validFilm.getId() == 0) {
            validFilm.setId(filmId++);
        }
        filmData.put(validFilm.getId(), validFilm);
        filmRating.add(validFilm);
        log.info("Фильм добавлен");
        return validFilm;
    }

    @Override
    public Film getFilm(Integer id) throws ParameterNotFoundException {
        if (!filmData.containsKey(id)) {
            throw new ParameterNotFoundException("user not found");
        } else {
            return filmData.get(id);
        }
    }

    @Override
    public Film put(Film film) throws ValidationException, ParameterNotFoundException {
        if (!filmData.containsKey(film.getId())) {
            throw new ParameterNotFoundException("film not found");
        } else {
            Film validFilm = validate(film);
            filmRating.remove(filmData.get(validFilm.getId()));
            filmData.put(validFilm.getId(), validFilm);
            filmRating.add(validFilm);
            log.info("Фильм изменён");
            return validFilm;
        }
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
        log.info("Получен запрос");
        return List.copyOf(filmData.values());
    }

    @Override
    public Map<Integer, Film> getFilmData() {
        return filmData;
    }
    @Override
    public Set<Film> getFilmRating() {
        return filmRating;
    }

    @Override
    public Film findFilmById(Long id) {
        String sqlQuery = "select * from films where id = ?";
        Film film;
        try {
            film = jdbcTemplate.queryForObject(sqlQuery, this::mapRowToFilm, id);
        } catch (EmptyResultDataAccessException e) {
            throw new ObjectNotFoundException(1, "cant found " + id);
        }

        return film;
    }

    @Override
    public Film saveFilm(Film film) {
        String sqlQuery = "insert into FILMS(NAME, RELEASE_DATE, DESCRIPTION, DURATION, MPA)" +
                " values (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, film.getName());
            ps.setDate(2, Date.valueOf(film.getReleaseDate()));
            ps.setString(3, film.getDescription());
            ps.setInt(4, film.getDuration());
            ps.setInt(5, Math.toIntExact(film.getMpa().getId()));
            return ps;
        }, keyHolder);

        long id = Objects.requireNonNull(keyHolder.getKey()).longValue();

        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                String sql = "insert into FILM_GENRE values(?, ?)";
                jdbcTemplate.update(sql, id, genre.getId());
                log.info("genre added");
            }
        }
        return findFilmById(id);
    }


}*/
