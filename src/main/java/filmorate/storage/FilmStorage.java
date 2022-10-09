package filmorate.storage;

import filmorate.exception.ValidationException;
import filmorate.model.Film;

import java.util.List;
import java.util.Map;


public interface FilmStorage {
    Film create(Film film) throws ValidationException;
    Film put(Film film) throws ValidationException;
    Film validate(Film film) throws ValidationException;
    List<Film> getData();
    Map<Integer, Film> getFilmData();
}
