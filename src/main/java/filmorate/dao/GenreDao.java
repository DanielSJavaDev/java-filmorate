package filmorate.dao;

import filmorate.model.Genre;

import java.util.Collection;

public interface GenreDao {
    Collection<Genre> getAll();

    Genre getGenreById(Integer genreId);
}