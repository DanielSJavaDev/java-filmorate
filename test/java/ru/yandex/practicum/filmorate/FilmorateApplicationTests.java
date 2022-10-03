package ru.yandex.practicum.filmorate;

import filmorate.FilmorateApplication;
import filmorate.storage.InMemoryFilmStorage;
import filmorate.storage.InMemoryUserStorage;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import filmorate.controller.FilmController;
import filmorate.controller.UserController;
import filmorate.exception.ValidationException;
import filmorate.model.Film;
import filmorate.model.User;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = FilmorateApplication.class)
class FilmorateApplicationTests { // класс тестов эндпоинтов

	@Test
	void contextLoads() {
	}

	@Test
	public void testFilmController() throws ValidationException {
		InMemoryFilmStorage filmStorage = new InMemoryFilmStorage();
		FilmController filmController = new FilmController(filmStorage);
		Film film = new Film();
		film.setId(1);
		film.setDescription("I'm tired boss");
		film.setReleaseDate(LocalDate.of(1895, 12, 28));
		film.setDuration(90L);
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
				() -> filmController.create(film)
		);
		assertEquals("Incorrect date", exception.getMessage()); // валидация даты
	}

	@Test
	public void testUserController() throws ValidationException {
		InMemoryUserStorage userStorage = new InMemoryUserStorage();
		UserController userController = new UserController(userStorage);
		User user = new User();
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
		assertEquals("Incorrect login", exception.getMessage()); // валидация логина

		user.setLogin("Svin");
		user.setName("Borov");
		User valid = userController.put(user);
		assertEquals(valid, user, "Изменение юзера не работает"); // изменение юзера

	}
}
