package filmorate.storage;

import filmorate.exception.ValidationException;
import filmorate.model.Film;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> filmData = new HashMap<>();
    @Override
    public Film create(Film film) throws ValidationException {
        Film validFilm = validate(film);
        filmData.put(validFilm.getId(), validFilm);
        log.info("Фильм добавлен");
        return validFilm;
    }

    @Override
    public Film put(Film film) throws ValidationException {
        Film validFilm = validate(film);
        filmData.put(validFilm.getId(), validFilm);
        log.info("Фильм изменён");
        return validFilm;
    }

    @Override
    public Film delete(Film film) {
        return null;
    }

    @Override
    public Film validate(Film film) throws ValidationException {
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

}
