package filmorate.controller;

import filmorate.model.Mpa;
import filmorate.service.MpaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(value = "/mpa")
@RequiredArgsConstructor
public class MpaController {

    private final MpaService mpaService;

    @GetMapping
    public Collection<Mpa> findAll(){
        return mpaService.findAllMpa();
    }

    @GetMapping("/{id}")
    public Mpa getMpaRating(@PathVariable int id){
        return mpaService.findMpaById(id);
    }
}