import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import filmorate.model.Film;
import filmorate.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;



class ValidateRequestBodyControllerTest { // класс тестов по @Valid
    private static ValidatorFactory validatorFactory;
    private static Validator validator;
    private Film film;
    private User user;
    @BeforeEach
    void models() {
        film = Film.builder().build();
        user = User.builder().build();
    }

    @BeforeAll
    static void validators() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    @AfterAll
    static void close() {
        validatorFactory.close();
    }

    @Test
    void whenInputIsInvalid_thenReturnsStatus400forFilm() {

        film.setId(1);
        film.setName("Green mile");
        film.setDescription("I'm tired bosssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
                "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
                "ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
                "ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
                "ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
        film.setReleaseDate(LocalDate.of(1895, 12, 28));
        film.setDuration(90);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(violations.size(), 1); // тест описания
        film.setDescription("I'm tired boss");


        film.setId(1);
        film.setReleaseDate(LocalDate.of(2023, 12, 27));
        violations = validator.validate(film);
        assertEquals(violations.size(), 1); // тест даты
        film.setReleaseDate(LocalDate.of(1990, 12, 27));

        film.setName(" ");
        violations = validator.validate(film);
        assertEquals(violations.size(), 1); // тест имени на бланк
        film.setName("Green mile");

        film.setName("");
        violations = validator.validate(film);
        assertEquals(violations.size(), 1); // тест имени на null
        film.setName("Green mile");

        film.setDuration(-1);
        violations = validator.validate(film);
        assertEquals(violations.size(), 1); // тест имени на null
        film.setDuration(90);

        violations = validator.validate(film);
        assertEquals(violations.size(), 0); // контрольный(все поля валидны)
    }

    @Test
    void whenInputIsInvalid_thenReturnsStatus400forUser() {

        user.setId(1);
        user.setEmail("user@email.com");
        user.setLogin("Svin");
        user.setName("Borov");
        user.setId(1);

        user.setEmail("notEmail");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(violations.size(), 1); // тест email'a
        user.setEmail("user@email.com");

        user.setLogin("");
        violations = validator.validate(user);
        assertEquals(violations.size(), 1); // тест логина
        user.setLogin("Svin");

        user.setBirthday(LocalDate.of(2030, 12, 28));
        violations = validator.validate(user);
        assertEquals(violations.size(), 1); // тест дня рождения
        user.setBirthday(LocalDate.of(1990, 12, 28));

        violations = validator.validate(user);
        assertEquals(violations.size(), 0); // контрольный(все поля валидны)

    }
}