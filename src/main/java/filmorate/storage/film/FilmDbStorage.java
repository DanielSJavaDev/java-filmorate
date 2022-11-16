package filmorate.storage.film;

import filmorate.model.Film;
import filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public interface FilmDbStorage {
    Film add(Film film);
    Film put(Film film);
    Film remove(Film film);
    List<Film> getAllFilms();
    Film getFilmById(Integer id);
    Film createFilm(ResultSet rs) throws SQLException;
    List<Genre> createGenreList(ResultSet rs) throws SQLException;
    void updateGenre(Film film);
    Boolean likeFilm(int filmId, int userId);
    Boolean unlikeFilm(int filmId, int userId);
    Integer getLikesCount(int filmId);
    List<Film> getTop(int size);
}
