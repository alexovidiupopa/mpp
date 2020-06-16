package ro.ubb.movieapp.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.movieapp.core.service.MovieService;
import ro.ubb.movieapp.web.converter.MovieConverter;
import ro.ubb.movieapp.web.converter.MovieWithActorConverter;
import ro.ubb.movieapp.web.dto.MovieDto;
import ro.ubb.movieapp.web.dto.MovieWithActorDto;

import java.util.Set;

@RestController
@RequestMapping("/api/movies")
@Slf4j
public class Controller {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieConverter movieConverter;

    @Autowired
    private MovieWithActorConverter movieWithActorConverter;

    @CrossOrigin
    @RequestMapping(value = "/findAll/year={year}/{less}",method = RequestMethod.GET)
    Set<MovieDto> getAllAfterYear(@PathVariable String year, @PathVariable Boolean less){
        return movieConverter.convertModelsToDtos(movieService.getMoviesByYear(Integer.parseInt(year), less));

    }

    @CrossOrigin
    @RequestMapping(value = "/findAllWithActors/year={year}/{less}", method = RequestMethod.GET)
    Set<MovieWithActorDto> getAllWithActors(@PathVariable String year, @PathVariable Boolean less){
        return movieWithActorConverter.convertModelsToDtos(movieService.getMoviesWithActorsByYear(Integer.parseInt(year), less));
    }

    @CrossOrigin
    @RequestMapping(value = "/delete/movieID={movieID}/actorID={actorID}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteActor(@PathVariable Long movieID,@PathVariable Long actorID ){
        movieService.deleteActor(movieID,actorID);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
