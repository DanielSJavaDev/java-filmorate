package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.controller.FilmController;
import ru.yandex.practicum.controller.UserController;
import ru.yandex.practicum.exception.ValidationException;
import ru.yandex.practicum.model.Film;
import ru.yandex.practicum.model.User;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class FilmorateApplicationTests { // класс тестов эндпоинтов

	@Test
	void contextLoads() {
	}

	@Test
	public void testFilmController() throws ValidationException {
		FilmController filmController = new FilmController();
		Film film = new Film();
		film.setId(1);
		film.setDescription("I'm tired boss");
		film.setReleaseDate(LocalDate.of(1895, 12, 28));
		film.setDuration(90L);
		film.setName("Green mile");
		Film result = filmController.post(film);
		assertNotNull(filmController.get(), "Фильмы не возвращаются"); // тест гет запроса
		assertEquals(result, film, "Фильмы не возвращаются"); // тест пост запроса
		film.setName("Psycho");
		Film put = filmController.put(film);
		assertEquals(put, film, "Фильмы не меняются"); // тест пут запроса
		film.setReleaseDate(LocalDate.of(1895, 12, 27));
		final ValidationException exception = assertThrows(
				ValidationException.class,
				() -> filmController.post(film)
		);
		assertEquals("Incorrect date", exception.getMessage()); // валидация даты
	}

	@Test
	public void testUserController() throws ValidationException {
		UserController userController = new UserController();
		User user = new User();
		user.setId(1);
		user.setEmail("user@email.com");
		user.setLogin("Svin");
		user.setName("Borov");
		user.setBirthday(LocalDate.of(1895, 12, 28));
		User logName = userController.post(user);
		assertEquals(logName, user, "Замена имени на логин не работает"); // добавление без имени
		user.setLogin("Sv in");
		final ValidationException exception = assertThrows(
				ValidationException.class,
				() -> userController.post(user)
		);
		assertEquals("Incorrect login", exception.getMessage()); // валидация логина

		user.setLogin("Svin");
		user.setName("Borov");
		User valid = userController.put(user);
		assertEquals(valid, user, "Изменение юзера не работает"); // изменение юзера

	}
}
