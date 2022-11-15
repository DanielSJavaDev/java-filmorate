package filmorate.service;

import filmorate.dao.GenreDao;
import filmorate.model.Genre;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class GenreService {
    private final GenreDao genreDao;

    GenreService(GenreDao genreDao){
        this.genreDao = genreDao;
    }

    public Collection<Genre> findAll(){
        return genreDao.getAll();
    }

    public Genre getGenreById(Integer genreId){
        return genreDao.getGenreById(genreId);
    }
}