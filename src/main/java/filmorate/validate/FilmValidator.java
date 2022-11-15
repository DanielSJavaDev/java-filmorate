package filmorate.validate;

import filmorate.exception.ParameterNotFoundException;
import filmorate.exception.ValidationException;
import filmorate.model.Film;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@Component
public class FilmValidator {

    private final JdbcTemplate jdbcTemplate;

    public void validateFilmToCreate(Film film) {
        validateReleaseDate(film);
    }

    public void validateFilmToUpdate(Film film) {
        validateReleaseDate(film);
        identifyFilm(film);
    }
    public void identifyFilm(Film film) {
        String sqlQuery = "select count(*) from films where id = ?;";
        boolean isValid = jdbcTemplate.queryForObject(sqlQuery, Integer.class, film.getId()) == 1;
        if (!isValid) throw new ParameterNotFoundException("Film with ID " + film.getId() + " is not found");
    }

    public void identifyFilmId(int id) {
        String sqlQuery = "select count(*) from films where id = ?;";
        boolean isValid = jdbcTemplate.queryForObject(sqlQuery, Integer.class, id)==1;
        if (!isValid) throw new ParameterNotFoundException("Film with ID " + id + " is not found");
    }

    public void validateReleaseDate(Film film){
        boolean isReleaseDateValid = film.getReleaseDate().isAfter(LocalDate.of(1895, 12, 28));
        log.info("Release date validation: {}", isReleaseDateValid);
        if (!isReleaseDateValid) throw new ValidationException("Film release date should not be earlier " +
                "than December 28th of 1895");
    }

}
