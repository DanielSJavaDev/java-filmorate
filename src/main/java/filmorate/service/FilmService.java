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


    public Film addLike(int filmId, int userId) {
        if (!filmStorage.getFilmData().containsKey(filmId)) {
            throw new ParameterNotFoundException("can't find film");
        } else if (filmStorage.getFilmData().get(filmId).getLiked().contains(userId)) {
            throw new ParameterNotFoundException("like already exist");
        } else {
            int filmLikes = filmStorage.getFilmData().get(filmId).getRate();
            filmStorage.getFilmData().get(filmId).setRate(filmLikes + 1);
            filmStorage.getFilmData().get(filmId).getLiked().add(userId);
            log.info("like added");
        }
        return filmStorage.getFilmData().get(filmId);
    }
    public String deleteLike(int filmId, int userId) {
        if (!filmStorage.getFilmData().containsKey(filmId)) {
            throw new ParameterNotFoundException("can't find film");
        } else if (!filmStorage.getFilmData().get(filmId).getLiked().contains(userId)) {
            throw new ParameterNotFoundException("can't find like");
        } else {
            int likes = filmStorage.getFilmData().get(filmId).getRate();
            filmStorage.getFilmData().get(filmId).setRate(likes - 1);
            filmStorage.getFilmData().get(filmId).getLiked().remove(userId);
            log.info("like deleted");
        }
        return "like deleted";
    }

    public List<Film> getRating(int size) {

        return filmStorage.getFilmRating().stream().limit(size)
                .sorted(Comparator.comparingInt(Film::getRate).reversed()).collect(Collectors.toList());
    }
}