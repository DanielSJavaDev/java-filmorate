DELETE FROM FRIENDLIST;
DELETE FROM LIKED_FILMS;
DELETE FROM USERS;
DELETE FROM FILMS;

INSERT INTO USERS (name, login, email, birthday) VALUES ( 'user1', 'user1_login', 'user1@mail.ru', '1990-03-15');
INSERT INTO USERS (name, login, email, birthday) VALUES ( 'user2', 'user2_login', 'user2@mail.ru', '1991-02-19');
INSERT INTO USERS (name, login, email, birthday) VALUES ( 'friend1', 'friend1_login', 'friend1@gmail.com', '1992-06-06');
INSERT INTO USERS (name, login, email, birthday) VALUES ( 'friend2', 'friend2_login', 'friend2@gmail.com', '1991-09-21');

INSERT INTO FRIENDLIST (user_id, friend_id) VALUES (2, 4);
INSERT INTO FRIENDLIST (user_id, friend_id) VALUES (2, 1);
INSERT INTO FRIENDLIST (user_id, friend_id) VALUES (3, 1);
INSERT INTO FRIENDLIST (user_id, friend_id) VALUES (3, 2);

INSERT INTO FILMS (name, release_date, description, duration, mpa)
VALUES ('film1', '1989-08-08', 'description of the film', 139, 1);
INSERT INTO FILMS (name, release_date, description, duration, mpa)
VALUES ('film2', '1949-04-17', 'description of the film2', 170, 3);

INSERT INTO LIKED_FILMS (film_id, user_id) VALUES (1, 1);
INSERT INTO LIKED_FILMS (film_id, user_id) VALUES (1, 2);
INSERT INTO LIKED_FILMS (film_id, user_id) VALUES (2, 1);