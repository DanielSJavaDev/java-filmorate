package filmorate.service;

import filmorate.exception.ParameterNotFoundException;
import filmorate.model.Film;
import filmorate.storage.film.FilmStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
/*
@Slf4j
@RequiredArgsConstructor
@Service
public class FilmService {
    private final FilmStorage filmStorage;

    public Film addLike(int filmId, int userId) {
        if (!filmStorage.getFilmData().containsKey(filmId)) {
            throw new ParameterNotFoundException("can't find film");
        } else if (filmStorage.getFilmData().get(filmId).getLiked().contains(userId)) {
            throw new ParameterNotFoundException("like already exist");
        } else {
            add(filmId, userId);
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
            delete(filmId, userId);
            log.info("like deleted");
        }
        return "like deleted";
    }

    public List<Film> getRating(int size) {
        return filmStorage.getFilmRating().stream().limit(size)
                .sorted(Comparator.comparingInt(Film::getRate).reversed()).collect(Collectors.toList());
    }

    private void add(int filmId, int userId) {
        int filmLikes = filmStorage.getFilmData().get(filmId).getRate();
        filmStorage.getFilmData().get(filmId).setRate(filmLikes + 1);
        filmStorage.getFilmData().get(filmId).getLiked().add(userId);
    }

    private void delete(int filmId, int userId) {
        int likes = filmStorage.getFilmData().get(filmId).getRate();
        filmStorage.getFilmData().get(filmId).setRate(likes - 1);
        filmStorage.getFilmData().get(filmId).getLiked().remove(userId);
    }
}
*/