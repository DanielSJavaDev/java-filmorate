package filmorate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*
 storage - перенос логики из контроллеров +
 service - реализация лайков, друзей, других взаимодействий пользователь - пользователь и пользователь - фильм
 dependencies - настроить спринговые зависимости через аннотации
 endpoints - добавить в контролееры эндпоинты на все действия
 exceptionHandler - настроить обработку ошибок


 @Component — аннотация, которая определяет класс как управляемый Spring. Такой класс будет добавлен в контекст
 приложения при сканировании. @Service не отличается по поведению, но обозначает более узкий спектр классов — такие,
 которые содержат в себе бизнес-логику и, как правило, не хранят состояние.

 С помощью аннотации @PathVariable добавьте возможность получать каждый фильм и данные о пользователях по их
 уникальному идентификатору: GET .../users/{id}.
 Добавьте методы, позволяющие пользователям добавлять друг друга в друзья, получать список общих друзей и лайкать фильмы.
 Проверьте, что все они работают корректно.
 PUT /users/{id}/friends/{friendId} — добавление в друзья.
 DELETE /users/{id}/friends/{friendId} — удаление из друзей.
 GET /users/{id}/friends — возвращаем список пользователей, являющихся его друзьями.
 GET /users/{id}/friends/common/{otherId} — список друзей, общих с другим пользователем.
 PUT /films/{id}/like/{userId} — пользователь ставит лайк фильму.
 DELETE /films/{id}/like/{userId} — пользователь удаляет лайк.
 GET /films/popular?count={count} — возвращает список из первых count фильмов по количеству лайков. Если значение
 параметра count не задано, верните первые 10.
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
