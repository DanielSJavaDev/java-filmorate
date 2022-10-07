package filmorate.controller;

import filmorate.service.FilmService;
import filmorate.storage.InMemoryFilmStorage;
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
    private final InMemoryFilmStorage filmStorage;
    private final FilmService service;

    @GetMapping
    public List<Film> get() {
        return filmStorage.getData();
    }

    @GetMapping("/popular?count={count}")
    public List<Film> getPopular(@RequestParam(defaultValue = "10", required = false) Integer size) {
        return service.getRating(size);
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) throws ValidationException {

        return filmStorage.create(film);
    }

    @PutMapping
    public Film put(@Valid @RequestBody Film film) throws ValidationException {

        return filmStorage.put(film);
    }
    @PutMapping("/{id}/like/{userId}")
    public Film like(@PathVariable int filmId, int userId) {
        return service.addLike(filmId, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film delete(@PathVariable int filmId, int userId) {
        return service.deleteLike(filmId, userId);
    }

}
