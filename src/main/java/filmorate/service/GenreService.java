package filmorate.service;

import filmorate.dao.GenreDao;
import filmorate.model.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreDao genreDao;

    public List<Genre> findAll(){
        return genreDao.getAll();
    }

    public Genre getGenreById(Integer genreId){
        return genreDao.getGenreById(genreId);
    }
}