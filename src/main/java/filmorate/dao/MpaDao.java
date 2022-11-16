package filmorate.dao;

import filmorate.model.Mpa;

import java.util.List;


public interface MpaDao {

    List<Mpa> getAllMpa();

    Mpa getMpaByRatingId(Integer ratingId);
}