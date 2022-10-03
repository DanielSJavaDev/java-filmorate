package filmorate.controller;

import filmorate.storage.InMemoryFilmStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import filmorate.exception.ValidationException;
import filmorate.model.Film;

import javax.validation.Valid;
import java.util.*;
 
@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final InMemoryFilmStorage filmStorage;
    public FilmController(InMemoryFilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    @GetMapping
    public List<Film> get() {

        return filmStorage.getData();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) throws ValidationException {

        return filmStorage.create(film);
    }

    @PutMapping
    public Film put(@Valid @RequestBody Film film) throws ValidationException {

        return filmStorage.put(film);
    }

}
