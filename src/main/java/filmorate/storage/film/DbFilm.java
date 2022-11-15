package filmorate.storage.film;

import filmorate.model.Film;
import filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface DbFilm {
    Film add(Film film);
    Film put(Film film);
    Film remove(Film film);
    Film validate(Film film);
    List<Film> getData();
    Film getFilm(Integer id);
    Film createFilm(ResultSet rs) throws SQLException;
    List<Genre> createGenreList(ResultSet rs) throws SQLException;
    void updateGenre(Film film);
    boolean likeFilm(int filmId, int userId);
    boolean unlikeFilm(int filmId, int userId);
    Integer getLikesCount(int filmId);
    List<Film> getTop(int size);

}
