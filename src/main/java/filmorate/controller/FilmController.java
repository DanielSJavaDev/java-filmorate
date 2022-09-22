package filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import filmorate.exception.ValidationException;
import filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;
 
@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final Map<Integer, Film> filmData = new HashMap<>();

    @GetMapping
    public List<Film> get() {
        log.info("Получен запрос");
        return List.copyOf(filmData.values());
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) throws ValidationException {
        Film validFilm = validate(film);
        filmData.put(validFilm.getId(), validFilm);
        log.info("Фильм добавлен");
        return validFilm;
    }

    @PutMapping
    public Film put(@Valid @RequestBody Film film) throws ValidationException {
        Film validFilm = validate(film);
        filmData.put(validFilm.getId(), validFilm);
        log.info("Фильм изменён");
        return validFilm;
    }

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
}
