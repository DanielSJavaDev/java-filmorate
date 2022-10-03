package filmorate.storage;

import filmorate.exception.ValidationException;
import filmorate.model.Film;

import java.util.List;


public interface FilmStorage {
    Film create(Film film) throws ValidationException;
    Film put(Film film) throws ValidationException;
    Film delete(Film film);
    Film validate(Film film) throws ValidationException;
    List<Film> getData();
}
