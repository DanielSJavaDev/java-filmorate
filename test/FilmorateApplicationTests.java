import filmorate.FilmorateApplication;
import filmorate.model.Film;
import filmorate.model.Genre;
import filmorate.model.Mpa;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import filmorate.controller.FilmController;
import filmorate.controller.UserController;
import filmorate.exception.ValidationException;

import filmorate.model.User;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@AutoConfigureTestDatabase
@SpringBootTest(classes = FilmorateApplication.class)
class FilmorateApplicationTests { // класс тестов эндпоинтов
	private final FilmController filmController;
	private final UserController userController;
	private Film film;
	private User user;
	private Mpa mpa;

	@Test
	void contextLoads() {
	}

	@BeforeEach
	void createFilm(){
		film = Film.builder().build();
		user = User.builder().build();
		mpa = Mpa.builder().build();
	}

	@Sql(scripts = "/testdata.sql")
	@Test
	public void testFilmController() throws ValidationException {
		List<Genre> genres = new ArrayList<>();

		mpa.setId(1);
		mpa.setName("G");
		film.setReleaseDate(LocalDate.of(1895, 12, 29));
		film.setDuration(0);
		film.setMpa(mpa);
		Film result = filmController.create(film);
		assertNotNull(result, "Фильмы не возвращаются");
		assertEquals(result, film, "Фильмы не возвращаются");
		film.setName("Psycho");
		Film put = filmController.put(film);
		put.setLikesCount(0);
		put.setGenres(genres);
		film.setLikesCount(0);
		film.setGenres(genres);
		assertEquals(put, film, "Фильмы не меняются"); // тест пут запроса
		film.setReleaseDate(LocalDate.of(1895, 12, 27));
		final ValidationException exception = assertThrows(
				ValidationException.class,
				() -> filmController.create(film)
		);
		assertEquals("Film release date should not be earlier than December 28th of 1895",
				exception.getParameter()); // валидация даты

	}

	@Test
	public void testUserController() throws ValidationException {

		user.setEmail("user@email.com");
		user.setLogin("Svin");
		user.setName("Borov");
		user.setBirthday(LocalDate.of(1895, 12, 28));
		User logName = userController.create(user);
		assertEquals(logName, user, "Замена имени на логин не работает"); // добавление без имени
		user.setLogin("Sv in");
		final ValidationException exception = assertThrows(
				ValidationException.class,
				() -> userController.create(user)
		);
		assertEquals("Login should not be empty or contain spaces", exception.getParameter()); // валидация логина

		user.setLogin("Svin");
		user.setName("Borov");
		User valid = userController.put(user);
		assertEquals(valid, user, "Изменение юзера не работает"); // изменение юзера


	}
}
