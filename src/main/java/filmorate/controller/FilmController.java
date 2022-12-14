package filmorate.controller;

import filmorate.exception.ParameterNotFoundException;
import filmorate.storage.film.FilmDbStorageImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import filmorate.exception.ValidationException;
import filmorate.model.Film;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {
    private final FilmDbStorageImpl filmStorage;

    @GetMapping
    public List<Film> get() {
        return filmStorage.getAllFilms();
    }
    @GetMapping("/{filmId}")
    public Film findById(@PathVariable("filmId") int filmId) throws ParameterNotFoundException {
        return filmStorage.getFilmById(filmId);
    }

    @GetMapping("/popular")
    public List<Film> getPopular(@RequestParam(name = "count", defaultValue = "10", required = false) Integer size) {
        return filmStorage.getTop(size);
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) throws ValidationException {
        return filmStorage.add(film);
    }

    @PutMapping
    public Film put(@Valid @RequestBody Film film) throws ValidationException, ParameterNotFoundException {
        return filmStorage.put(film);
    }
    @PutMapping("/{filmId}/like/{userId}")
    public Boolean like(@PathVariable("filmId") int filmId,
                     @PathVariable("userId") int userId) throws ParameterNotFoundException {
        return filmStorage.likeFilm(filmId, userId);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public Boolean delete(@PathVariable("filmId") int filmId,
                         @PathVariable("userId") int userId) throws ParameterNotFoundException {
        return filmStorage.unlikeFilm(filmId, userId);
    }
}