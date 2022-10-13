package filmorate.storage;

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
    List<Film> getData();
    Film getFilm(int id) throws ParameterNotFoundException;
    Map<Integer, Film> getFilmData();
    Set<Film> getFilmRating();
}
