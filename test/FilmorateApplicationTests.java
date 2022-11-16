import filmorate.FilmorateApplication;
import filmorate.dao.GenreDaoImp;
import filmorate.dao.MpaDaoImp;
import filmorate.model.Film;
import filmorate.storage.film.FilmDbStorage;
import filmorate.storage.user.UserDbStorage;
import filmorate.validate.FilmValidator;
import filmorate.validate.UserValidator;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import filmorate.controller.FilmController;
import filmorate.controller.UserController;
import filmorate.exception.ValidationException;

import filmorate.model.User;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
@SpringBootTest(classes = FilmorateApplication.class)
class FilmorateApplicationTests { // класс тестов эндпоинтов
	private Film film;
	private User user;

	@Test
	void contextLoads() {
	}
	@BeforeEach
	void createFilm(){
		film = Film.builder().build();
		user = User.builder().build();
	}

	@Test
	public void testFilmController() throws ValidationException {
		JdbcTemplate jdbc = new JdbcTemplate();
		GenreDaoImp gdao = new GenreDaoImp(jdbc);
		MpaDaoImp mdao = new MpaDaoImp(jdbc);
		FilmValidator fv = new FilmValidator(jdbc);
		UserValidator uv = new UserValidator(jdbc);
		FilmDbStorage filmStorage = new FilmDbStorage(gdao, jdbc, mdao, fv, uv);

		FilmController filmController = new FilmController(filmStorage);

		film.setId(1);
		film.setDescription("I'm tired boss");
		film.setReleaseDate(LocalDate.of(1895, 12, 28));
		film.setDuration(90);
		film.setName("Green mile");
		Film result = filmController.create(film);
		assertNotNull(filmController.get(), "Фильмы не возвращаются"); // тест гет запроса
		assertEquals(result, film, "Фильмы не возвращаются"); // тест пост запроса
		film.setName("Psycho");
		Film put = filmController.put(film);
		assertEquals(put, film, "Фильмы не меняются"); // тест пут запроса
		film.setReleaseDate(LocalDate.of(1895, 12, 27));
		final ValidationException exception = assertThrows(
				ValidationException.class,
				() -> filmStorage.add(film)
		);
		assertEquals("Incorrect date", exception.getParameter()); // валидация даты
	}

	@Test
	public void testUserController() throws ValidationException {
		JdbcTemplate jdbc = new JdbcTemplate();
		UserValidator uv = new UserValidator(jdbc);
		UserDbStorage userStorage = new UserDbStorage(jdbc, uv);
		UserController userController = new UserController(userStorage);

		user.setId(1);
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
		assertEquals("Incorrect login", exception.getParameter()); // валидация логина

		user.setLogin("Svin");
		user.setName("Borov");
		User valid = userController.put(user);
		assertEquals(valid, user, "Изменение юзера не работает"); // изменение юзера

		boolean create = false;

		boolean friend = userStorage.addFriend(1,3);
		assertEquals(friend, create, "Добавление в друзья не работает");

	}
}
