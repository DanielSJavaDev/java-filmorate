package filmorate.service;

import filmorate.model.Film;
import filmorate.storage.FilmStorage;
import filmorate.storage.UserStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {
    FilmStorage filmStorage;
    UserStorage userStorage;
    TreeSet<Film> filmRating = new TreeSet<>(Comparator.comparing(Film::getLikes,
            Comparator.nullsLast(Comparator.naturalOrder())));
    public Film addLike(int filmId, int userId) {
        if (!filmStorage.getFilmData().containsKey(filmId)) {
            throw new ValidationException("can't find film");
        } else if (filmStorage.getFilmData().get(filmId).getLiked().contains(userStorage.getUsersData().get(userId))) {
            throw new ValidationException("like already exist");
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
            throw new ValidationException("can't find film");
        } else if (!filmStorage.getFilmData().get(filmId).getLiked().contains(userStorage.getUsersData().get(userId))) {
            throw new ValidationException("can't find like");
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