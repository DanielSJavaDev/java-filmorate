package filmorate.service;

import filmorate.exception.ParameterNotFoundException;
import filmorate.model.Film;
import filmorate.storage.FilmStorage;
import filmorate.storage.UserStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {
    FilmStorage filmStorage;
    UserStorage userStorage;
    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    TreeSet<Film> filmRating = new TreeSet<>(Comparator.comparing(Film::getLikes,
            Comparator.nullsLast(Comparator.naturalOrder())));
    public Film addLike(int filmId, int userId) {
        if (!filmStorage.getFilmData().containsKey(filmId)) {
            throw new ParameterNotFoundException("can't find film");
        } else if (filmStorage.getFilmData().get(filmId).getLiked().contains(userStorage.getUsersData().get(userId))) {
            throw new ParameterNotFoundException("like already exist");
        } else {
            filmStorage.getFilmData().get(filmId).setLikes(filmStorage.getFilmData().get(filmId).getLikes() + 1);
            filmRating.add(filmStorage.getFilmData().get(filmId));
            filmStorage.getFilmData().get(filmId).getLiked().add(userStorage.getUsersData().get(userId));
            log.info("like added");
        }
        return filmStorage.getFilmData().get(filmId);
    }
    public Film deleteLike(int filmId, int userId) {
        if (!filmStorage.getFilmData().containsKey(filmId)) {
            throw new ParameterNotFoundException("can't find film");
        } else if (!filmStorage.getFilmData().get(filmId).getLiked().contains(userStorage.getUsersData().get(userId))) {
            throw new ParameterNotFoundException("can't find like");
        } else {
            filmStorage.getFilmData().get(filmId).setLikes(filmStorage.getFilmData().get(filmId).getLikes() - 1);
            log.info("like deleted");
        }
        return filmStorage.getFilmData().get(filmId);
    }

    public List<Film> getRating(int size) {
        return filmRating.stream().limit(size).collect(Collectors.toList());
    }
}