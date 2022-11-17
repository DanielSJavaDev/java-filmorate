import filmorate.FilmorateApplication;
import filmorate.controller.FilmController;
import filmorate.controller.MpaController;
import filmorate.controller.UserController;
import filmorate.model.Film;
import filmorate.model.User;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = FilmorateApplication.class)
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmorateDbTest {

    private final UserController userController;
    private final FilmController filmController;
    private final MpaController mpaController;


        @Test
        @Sql(scripts = "/testdata.sql")
        public void shouldGetUserById() {
            User user = userController.findById(1);
            assertThat(user).hasFieldOrPropertyWithValue("id", 1);
            assertThat(user).hasFieldOrPropertyWithValue("name", "user1");
            assertThat(user).hasFieldOrPropertyWithValue("login", "user1_login");
            assertThat(user).hasFieldOrPropertyWithValue("email", "user1@mail.ru");
            assertThat(user).hasFieldOrPropertyWithValue("birthday",
                    LocalDate.of(1990, 3, 15));
        }

        @Test
        public void shouldGetAllUsers(){
            List<User> users = new ArrayList<>(userController.get());
            assertThat(users.size()).isEqualTo(5);

            assertThat(users.get(0)).hasFieldOrPropertyWithValue("id", 1);
            assertThat(users.get(0)).hasFieldOrPropertyWithValue("name", "user1");
            assertThat(users.get(0)).hasFieldOrPropertyWithValue("login", "user1_login");
            assertThat(users.get(0)).hasFieldOrPropertyWithValue("email", "user1@mail.ru");
            assertThat(users.get(0)).hasFieldOrPropertyWithValue("birthday",
                    LocalDate.of(1990, 3, 15));

            assertThat(users.get(1)).hasFieldOrPropertyWithValue("id", 2);
            assertThat(users.get(1)).hasFieldOrPropertyWithValue("name", "user2_updated");
            assertThat(users.get(1)).hasFieldOrPropertyWithValue("login", "new_login");
            assertThat(users.get(1)).hasFieldOrPropertyWithValue("email", "new@gmail.com");
            assertThat(users.get(1)).hasFieldOrPropertyWithValue("birthday",
                    LocalDate.of(1987, 4, 11));

            assertThat(users.get(2)).hasFieldOrPropertyWithValue("id", 3);
            assertThat(users.get(2)).hasFieldOrPropertyWithValue("name", "friend1");
            assertThat(users.get(2)).hasFieldOrPropertyWithValue("login", "friend1_login");
            assertThat(users.get(2)).hasFieldOrPropertyWithValue("email", "friend1@gmail.com");
            assertThat(users.get(2)).hasFieldOrPropertyWithValue("birthday",
                    LocalDate.of(1992, 6, 6));

            assertThat(users.get(3)).hasFieldOrPropertyWithValue("id", 4);
            assertThat(users.get(3)).hasFieldOrPropertyWithValue("name", "friend2");
            assertThat(users.get(3)).hasFieldOrPropertyWithValue("login", "friend2_login");
            assertThat(users.get(3)).hasFieldOrPropertyWithValue("email", "friend2@gmail.com");
            assertThat(users.get(3)).hasFieldOrPropertyWithValue("birthday",
                    LocalDate.of(1991, 9, 21));
        }

        @Test
        public void shouldAddUser(){
            User user = User.builder()
                    .name("new_user")
                    .login("new_login")
                    .email("new@email.com")
                    .birthday(LocalDate.of(1987, 9, 10))
                    .build();

            userController.create(user);

            User uploadedUser = userController.findById(5);

            assertThat(uploadedUser).hasFieldOrPropertyWithValue("id", 5);
            assertThat(uploadedUser).hasFieldOrPropertyWithValue("name", "new_user");
            assertThat(uploadedUser).hasFieldOrPropertyWithValue("login", "new_login");
            assertThat(uploadedUser).hasFieldOrPropertyWithValue("birthday",
                    LocalDate.of(1987, 9, 10));
        }

        @Test
        public void shouldUpdateUser(){
            User user = User.builder()
                    .id(2)
                    .name("user2_updated")
                    .login("new_login")
                    .email("new@gmail.com")
                    .birthday(LocalDate.of(1987, 4, 11))
                    .build();
            userController.put(user);

            User userUpdated = userController.findById(2);

            assertThat(userUpdated).hasFieldOrPropertyWithValue("id", 2);
            assertThat(userUpdated).hasFieldOrPropertyWithValue("name", "user2_updated");
            assertThat(userUpdated).hasFieldOrPropertyWithValue("login", "new_login");
            assertThat(userUpdated).hasFieldOrPropertyWithValue("birthday",
                    LocalDate.of(1987, 4, 11));
        }

        @Test
        public void shouldAddFriend(){
            userController.addFriend(1, 3);
            List<User> friends = new ArrayList<>(userController.getFriends(1));

            assertThat(friends.size()).isEqualTo(1);
            assertThat(friends.get(0)).hasFieldOrPropertyWithValue("id", 3);
            assertThat(friends.get(0)).hasFieldOrPropertyWithValue("name", "friend1");
            assertThat(friends.get(0)).hasFieldOrPropertyWithValue("login", "friend1_login");
            assertThat(friends.get(0)).hasFieldOrPropertyWithValue("birthday",
                    LocalDate.of(1992, 6, 6));
        }

        @Test
        public void shouldRemoveFriend(){
            List<User> friendsBefore = new ArrayList<>(userController.getFriends(2));

            assertThat(friendsBefore.size()).isEqualTo(2);

            userController.deleteFriend(2, 4);

            List<User> friendsAfter = new ArrayList<>(userController.getFriends(2));

            assertThat(friendsAfter.size()).isEqualTo(1);
        }

        @Test
        public void shouldGetCommonFriends(){
            List<User> friends = new ArrayList<>(userController.getOtherFriends(2, 3));
            assertThat(friends.size()).isEqualTo(1);
            assertThat(friends.get(0)).hasFieldOrPropertyWithValue("id", 1);
            assertThat(friends.get(0)).hasFieldOrPropertyWithValue("name", "user1");
            assertThat(friends.get(0)).hasFieldOrPropertyWithValue("login", "user1_login");
            assertThat(friends.get(0)).hasFieldOrPropertyWithValue("birthday",
                    LocalDate.of(1990, 3, 15));
        }

        @Test
        void shouldGetFilmById(){

            Film film = filmController.findById(1);

            assertThat(film).hasFieldOrPropertyWithValue("id", 1);
            assertThat(film).hasFieldOrPropertyWithValue("name", "film1");
            assertThat(film).hasFieldOrPropertyWithValue("releaseDate",
                    LocalDate.of(1989, 8, 8));
            assertThat(film).hasFieldOrPropertyWithValue("description", "description of the film");
            assertThat(film).hasFieldOrPropertyWithValue("duration", 139);
            assertThat(film.getMpa()).hasFieldOrPropertyWithValue("id", 1);



        }

        @Test
        void shouldGetAllFilms(){
            List<Film> films = new ArrayList<>(filmController.get());
            assertThat(films.size()).isEqualTo(3);

            assertThat(films.get(0)).hasFieldOrPropertyWithValue("id", 1);
            assertThat(films.get(0)).hasFieldOrPropertyWithValue("name", "film1");
            assertThat(films.get(0)).hasFieldOrPropertyWithValue("releaseDate",
                    LocalDate.of(1989, 8, 8));
            assertThat(films.get(0)).hasFieldOrPropertyWithValue("description", "description of the film");
            assertThat(films.get(0)).hasFieldOrPropertyWithValue("duration", 139);
            assertThat(films.get(0).getMpa()).hasFieldOrPropertyWithValue("id", 1);


            assertThat(films.get(1)).hasFieldOrPropertyWithValue("id", 2);
            assertThat(films.get(1)).hasFieldOrPropertyWithValue("name", "new_film_updated");
            assertThat(films.get(1)).hasFieldOrPropertyWithValue("releaseDate",
                    LocalDate.of(2001, 10, 10));
            assertThat(films.get(1)).hasFieldOrPropertyWithValue("description", "txt");
            assertThat(films.get(1)).hasFieldOrPropertyWithValue("duration", 212);
            assertThat(films.get(1).getMpa()).hasFieldOrPropertyWithValue("id", 1);

        }

        @Test
        public void shouldCreateFilm() throws SQLException {
            Film film = Film.builder()
                    .name("new_film")
                    .releaseDate(LocalDate.of(2000, 10, 10))
                    .description("text")
                    .duration(222)
                    .Mpa(mpaController.getMpaRating(5))
                    .build();

            filmController.create(film);

            Film film_uploaded = filmController.findById(3);

            assertThat(film_uploaded).hasFieldOrPropertyWithValue("id", 3);
            assertThat(film_uploaded).hasFieldOrPropertyWithValue("name", "new_film");
            assertThat(film_uploaded).hasFieldOrPropertyWithValue("releaseDate",
                    LocalDate.of(2000, 10, 10));
            assertThat(film_uploaded).hasFieldOrPropertyWithValue("description", "text");
            assertThat(film_uploaded).hasFieldOrPropertyWithValue("duration", 222);
            assertThat(film_uploaded.getMpa()).hasFieldOrPropertyWithValue("id", 5);



        }

        @Test
        public void shouldUpdateFilm(){
            Film film = Film.builder()
                    .id(2)
                    .name("new_film_updated")
                    .releaseDate(LocalDate.of(2001, 10, 10))
                    .description("txt")
                    .duration(212)
                    .Mpa(mpaController.getMpaRating(1))
                    .build();

            filmController.put(film);

            Film film_uploaded = filmController.findById(2);

            assertThat(film_uploaded).hasFieldOrPropertyWithValue("id", 2);
            assertThat(film_uploaded).hasFieldOrPropertyWithValue("name", "new_film_updated");
            assertThat(film_uploaded).hasFieldOrPropertyWithValue("releaseDate",
                    LocalDate.of(2001, 10, 10));
            assertThat(film_uploaded).hasFieldOrPropertyWithValue("description", "txt");
            assertThat(film_uploaded).hasFieldOrPropertyWithValue("duration", 212);
            assertThat(film_uploaded.getMpa()).hasFieldOrPropertyWithValue("id", 1);


        }

        @Test
        public void shouldRemoveFilm(){
            int count0 = filmController.get().size();
            assertThat(count0).isEqualTo(2);

            filmController.delete(1,2);

            int count = filmController.get().size();

            assertThat(count).isEqualTo(2);


        }



        @Test
        public void shouldUnlikeFilm(){
            Integer preUnlikeCount = filmController.findById(1).getLikesCount();
            assertThat(preUnlikeCount).isEqualTo(Integer.valueOf(2));
            filmController.delete(1, 2);
            Integer unlikeCount = filmController.findById(1).getLikesCount();
            assertThat(unlikeCount).isEqualTo(Integer.valueOf(2));


        }

        @Test
        public void shouldLikeFilm(){
            Integer prelikeCount = filmController.findById(1).getLikesCount();

            assertThat(prelikeCount).isEqualTo(Integer.valueOf(1));

            filmController.like(1, 4);

            Integer likeCount = filmController.findById(1).getLikesCount();

            assertThat(likeCount).isEqualTo(Integer.valueOf(2));


        }

        @Test
        public void shouldGetTopFilmsWithoutCountValue(){

            List<Film> topFilms = new ArrayList<>(filmController.getPopular(10));

            assertThat(topFilms.size()).isEqualTo(2);

            assertThat(topFilms.get(0)).hasFieldOrPropertyWithValue("id", 2);
            assertThat(topFilms.get(0)).hasFieldOrPropertyWithValue("name", "film2");
            assertThat(topFilms.get(0)).hasFieldOrPropertyWithValue("releaseDate",
                    LocalDate.of(1949, 4, 17));
            assertThat(topFilms.get(0)).hasFieldOrPropertyWithValue("description", "description of the film2");
            assertThat(topFilms.get(0)).hasFieldOrPropertyWithValue("duration", 170);
            assertThat(topFilms.get(0).getMpa()).hasFieldOrPropertyWithValue("id", 3);
            assertThat(topFilms.get(0).getLikesCount()).isEqualTo(1);


            assertThat(topFilms.get(1)).hasFieldOrPropertyWithValue("id", 1);
            assertThat(topFilms.get(1)).hasFieldOrPropertyWithValue("name", "film1");
            assertThat(topFilms.get(1)).hasFieldOrPropertyWithValue("releaseDate",
                    LocalDate.of(1989, 8, 8));
            assertThat(topFilms.get(1)).hasFieldOrPropertyWithValue("description", "description of the film");
            assertThat(topFilms.get(1)).hasFieldOrPropertyWithValue("duration", 139);
            assertThat(topFilms.get(1).getMpa()).hasFieldOrPropertyWithValue("id", 1);
            assertThat(topFilms.get(0).getLikesCount()).isEqualTo(1);

        }

        @Test
        public void shouldGetTopFilmsWithCountValue(){

            List<Film> topFilms = new ArrayList<>(filmController.getPopular(1));

            assertThat(topFilms.size()).isEqualTo(1);

            assertThat(topFilms.get(0)).hasFieldOrPropertyWithValue("id", 1);
            assertThat(topFilms.get(0)).hasFieldOrPropertyWithValue("name", "film1");
            assertThat(topFilms.get(0)).hasFieldOrPropertyWithValue("releaseDate",
                    LocalDate.of(1989, 8, 8));
            assertThat(topFilms.get(0)).hasFieldOrPropertyWithValue("description", "description of the film");
            assertThat(topFilms.get(0)).hasFieldOrPropertyWithValue("duration", 139);
            assertThat(topFilms.get(0).getMpa()).hasFieldOrPropertyWithValue("id", 1);
        }
    }