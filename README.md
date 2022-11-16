# java-filmorate
Database diagramm

![This is an image] (https://github.com/DanielSJavaDev/java-filmorate/blob/add-database/database%20diagramm.png)

SQL requests examples

Get all films:

SELECT *
FROM films;


Get all users:

SELECT *
FROM users;


Get top 10 films:

SELECT f.name, COUNT(lf.film_id) AS likes_count
FROM films AS f
LEFT JOIN liked_films as lf ON lf.film_id=films.film_id
ORDER BY likes_count DESC
LIMIT 10;


Get friend list:

SELECT friend_id
FROM friendlist
WHERE user_id = 1
      AND status = 1;
