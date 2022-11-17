package filmorate.controller;

import filmorate.model.Genre;
import filmorate.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(value = "/genres")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService service;

    @GetMapping("/{genreId}")
    public Genre getGenreById(@PathVariable Integer genreId){
        return service.getGenreById(genreId);
    }

    @GetMapping
    public Collection<Genre> findAll(){
        return  service.findAll();
    }
}