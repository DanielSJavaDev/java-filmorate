package filmorate.dao;

import filmorate.model.Mpa;

import java.util.Collection;


public interface MpaDao {

    Collection<Mpa> getMpaList();

    Mpa getMpa(Integer ratingId);
}