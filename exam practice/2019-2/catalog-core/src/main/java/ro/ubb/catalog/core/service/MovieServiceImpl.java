package ro.ubb.catalog.core.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Actor;
import ro.ubb.catalog.core.model.Movie;
import ro.ubb.catalog.core.model.MovieDto;
import ro.ubb.catalog.core.repository.ActorRepository;
import ro.ubb.catalog.core.repository.MovieRepository;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Override
    public List<Movie> getAllMovies()
    {
        List<Movie> movies =movieRepository.findAll();
        return movies;
    }
    @Override
    public Movie getMovieWithActors(Long movieID)
    {
        Movie movie=movieRepository.findMovieWithActors(movieID);
        return movie;
    }

    @Override
    @Transactional
    public Movie addActor(Long movieID, Long actorID)
    {
        log.trace(movieID.toString());
        log.trace(actorID.toString());
        Actor actor=actorRepository.getOne(actorID);
        Movie movie=movieRepository.getOne(movieID);
        movie.getActors().add(actor);
        log.trace(movie.toString());
        log.trace("returning movie");
        return movie;
    }


}
