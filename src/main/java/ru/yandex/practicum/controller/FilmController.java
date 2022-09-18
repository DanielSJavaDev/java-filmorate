package ru.yandex.practicum.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.exception.ValidationException;
import ru.yandex.practicum.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;
 
@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final Map<Integer, Film> filmData = new HashMap<>();

    @GetMapping
    public Collection<Film> get() {
        log.info("Получен запрос.");
        return filmData.values();
    }

    @PostMapping
    public Film post(@Valid @RequestBody Film film) throws ru.yandex.practicum.exception.ValidationException {
        if (film.getId() < 0) {
            log.info("Айди отрицательный");
            throw new ru.yandex.practicum.exception.ValidationException("Incorrect id");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
            log.info("Введена некорректна дата");
            throw new ru.yandex.practicum.exception.ValidationException("Incorrect date");
        }

            filmData.put(film.getId(), film);
            log.info("Фильм добавлен.");
            return film;
    }

    @PutMapping
    public Film put(@Valid @RequestBody Film film) throws ValidationException {
        if (film.getId() < 0) {
            log.info("Айди отрицательный");
            throw new ru.yandex.practicum.exception.ValidationException("Incorrect id");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
            log.info("Введена некорректна дата");
            throw new ru.yandex.practicum.exception.ValidationException("Incorrect date");
        }

            filmData.put(film.getId(), film);
            log.info("Фильм изменён.");
            return film;
    }
}
