package filmorate.storage;

import filmorate.exception.ParameterNotFoundException;
import filmorate.exception.ValidationException;
import filmorate.model.Film;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


import java.time.LocalDate;
import java.util.*;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private int filmId = 1;
    private final Map<Integer, Film> filmData = new HashMap<>();
    private final Set<Film> filmRating = new HashSet<>();
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
    public Film getFilm(int id) throws ParameterNotFoundException {
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
}
