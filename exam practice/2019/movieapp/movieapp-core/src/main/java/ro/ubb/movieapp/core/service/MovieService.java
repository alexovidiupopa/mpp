package ro.ubb.movieapp.core.service;

import org.springframework.stereotype.Service;
import ro.ubb.movieapp.core.model.Movie;

import java.util.List;

/**
 * author: radu
 */

public interface MovieService {
    List<Movie> getMoviesByYear(int year, boolean lessThan);
    List<Movie> getMoviesWithActorsByYear(int year, boolean lessThan);
    void deleteActor(Long movieId, Long actorId);

}
