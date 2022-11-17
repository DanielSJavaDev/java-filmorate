package filmorate.dao;

import filmorate.model.Genre;

import java.util.List;

public interface GenreDao {
    List<Genre> getAll();

    Genre getGenreById(Integer genreId);
}