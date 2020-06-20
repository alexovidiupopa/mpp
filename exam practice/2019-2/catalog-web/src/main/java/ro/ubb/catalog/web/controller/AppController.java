package ro.ubb.catalog.web.controller;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.ubb.catalog.core.model.Actor;
import ro.ubb.catalog.core.model.Movie;

import ro.ubb.catalog.core.model.MovieDto;
import ro.ubb.catalog.core.service.ActorService;
import ro.ubb.catalog.core.service.MovieService;


import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class AppController {


    @Autowired
    private MovieService movieService;
    @Autowired
    private ActorService actorService;

    @CrossOrigin
    @RequestMapping(value = "/movies",method = RequestMethod.GET)
    private List<MovieDto> getAll() {

        List<Movie> movies=movieService.getAllMovies();
        List<MovieDto> moviesDto=movies.stream().map(movie->{ MovieDto movieDto=MovieDto.builder()
                .year(movie.getYear())
                .title(movie.getTitle()).build();
                movieDto.setId(movie.getID());
                return movieDto;
        }).collect(Collectors.toList());
        return moviesDto;
    }


    @CrossOrigin
    @RequestMapping(value = "/movie/detail/{id}",method = RequestMethod.GET)
    private Movie getMovieDetail(@PathVariable Long id)
    {
        Movie movie=movieService.getMovieWithActors(id);
        return movie;
    }

    @CrossOrigin
    @RequestMapping(value = "/availableActors",method = RequestMethod.GET)
    private List<Actor> getAvailableActors()
    {
        return actorService.getAllAvailableActors();
    }

    @CrossOrigin
    @RequestMapping(value = "/addActor/{movieId}/{actorId}",method = RequestMethod.GET)
    private MovieDto addActor(@PathVariable Long movieId, @PathVariable Long actorId)
    {
        log.trace("add={}, and {} ",actorId,movieId);

        Movie movie=movieService.addActor(movieId,actorId);
        return MovieDto.builder().title(movie.getTitle()).year(movie.getYear()).id(movie.getID()).build();
    }
//
//    @CrossOrigin
//    @RequestMapping(value = "/kill/{name}",method=RequestMethod.GET)
//    public void kill(@PathVariable String name) {
//        System.out.println("i wanna kill " + name);
//        try (Socket socket = new Socket(HOST, PORT);
//             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
//        ) {
//            out.println("kill");
//            out.println(name);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
