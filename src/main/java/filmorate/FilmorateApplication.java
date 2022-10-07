package filmorate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*
 storage - перенос логики из контроллеров +
 service - реализация лайков, друзей, других взаимодействий пользователь - пользователь и пользователь - фильм
 dependencies - настроить спринговые зависимости через аннотации
 endpoints - добавить в контролееры эндпоинты на все действия
 exceptionHandler - настроить обработку ошибок


 Убедитесь, что ваше приложение возвращает корректные HTTP-коды.
 400 — если ошибка валидации: ValidationException;
 404 — для всех ситуаций, если искомый объект не найден;
 500 — если возникло исключение.

 Настройте ExceptionHandler для централизованной обработки ошибок.

 
 */

@SpringBootApplication
public class FilmorateApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilmorateApplication.class, args);
	}

}
