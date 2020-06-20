package ro.ubb.catalog.core.service;

import ro.ubb.catalog.core.model.Movie;

import java.util.List;

public interface MovieService {

        List<Movie> getAllMovies();
        Movie getMovieWithActors(Long movieID);
        Movie addActor(Long movieID,Long actorId);
}
