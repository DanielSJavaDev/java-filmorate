package filmorate.storage.film;

import filmorate.exception.ParameterNotFoundException;
import filmorate.exception.ValidationException;
import filmorate.model.Film;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface FilmStorage {
    Film create(Film film) throws ValidationException;
    Film put(Film film) throws ValidationException;
    Film validate(Film film) throws ValidationException;
    Film remove(Film film);
    List<Film> getData();
    Film getFilm(Integer id) throws ParameterNotFoundException;
    Map<Integer, Film> getFilmData();
    Set<Film> getFilmRating();
    Film findFilmById(Long id);
    Film saveFilm(Film film);
}
