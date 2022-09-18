package ru.yandex.practicum.filmorate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import ru.yandex.practicum.controller.FilmController;
import ru.yandex.practicum.controller.UserController;

@ComponentScan(basePackageClasses = UserController.class)
@ComponentScan(basePackageClasses = FilmController.class)
@SpringBootApplication
public class FilmorateApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilmorateApplication.class, args);
	}

}
