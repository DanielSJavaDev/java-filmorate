package ru.yandex.practicum.filmorate;

import filmorate.FilmorateApplication;
import filmorate.service.FilmService;
import filmorate.service.UserService;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = FilmorateApplication.class)
class FilmorateApplicationTests { // класс тестов эндпоинтов

	@Test
	void contextLoads() {
	}

	@Test
	public void testFilmController() throws ValidationException {
		InMemoryFilmStorage filmStorage = new InMemoryFilmStorage();
		InMemoryUserStorage userStorage = new InMemoryUserStorage();
		FilmService service = new FilmService(filmStorage, userStorage);
		FilmController filmController = new FilmController(filmStorage, service);
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
				() -> filmStorage.create(film)
		);
		assertEquals("Incorrect date", exception.getParameter()); // валидация даты
	}

	@Test
	public void testUserController() throws ValidationException {
		InMemoryUserStorage userStorage = new InMemoryUserStorage();
		UserService service = new UserService(userStorage);
		UserController userController = new UserController(userStorage, service);
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
		assertEquals("Incorrect login", exception.getParameter()); // валидация логина

		user.setLogin("Svin");
		user.setName("Borov");
		User valid = userController.put(user);
		assertEquals(valid, user, "Изменение юзера не работает"); // изменение юзера

		User user1 = new User();
		user1.setId(3);
		user1.setEmail("user1@email.com");
		user1.setLogin("Svin1");
		user1.setName("Borov1");
		user1.setBirthday(LocalDate.of(1895, 12, 28));
		userController.create(user1);
		User friend = service.add(1,3);
		assertEquals(friend, user1, "Добавление в друзья не работает");

	}
}
