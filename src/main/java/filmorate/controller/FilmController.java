package filmorate.controller;

import filmorate.exception.ParameterNotFoundException;
import filmorate.service.FilmService;
import filmorate.storage.InMemoryFilmStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import filmorate.exception.ValidationException;
import filmorate.model.Film;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequestMapping("/films")

public class FilmController {
    private final InMemoryFilmStorage filmStorage;
    private final FilmService service;
    public FilmController(InMemoryFilmStorage filmStorage, FilmService service) {
        this.filmStorage = filmStorage;
        this.service = service;
    }

    @GetMapping
    public List<Film> get() {
        return filmStorage.getData();
    }
    @GetMapping("/{filmId}")
    public Film findById(@PathVariable("filmId") int filmId) throws ParameterNotFoundException {
        return filmStorage.getFilm(filmId);
    }

    @GetMapping("/popular")
    public List<Film> getPopular(@RequestParam(name = "count", defaultValue = "10", required = false) Integer size) {
        return service.getRating(size);
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) throws ValidationException {
        return filmStorage.create(film);
    }

    @PutMapping
    public Film put(@Valid @RequestBody Film film) throws ValidationException, ParameterNotFoundException {
        return filmStorage.put(film);
    }
    @PutMapping("/{filmId}/like/{userId}")
    public Film like(@PathVariable("filmId") int filmId,
                     @PathVariable("userId") int userId) throws ParameterNotFoundException {
        return service.addLike(filmId, userId);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public String delete(@PathVariable("filmId") int filmId,
                         @PathVariable("userId") int userId) throws ParameterNotFoundException {
        return service.deleteLike(filmId, userId);
    }
}
