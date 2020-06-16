package ro.ubb.movieapp.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.movieapp.core.model.Actor;
import ro.ubb.movieapp.core.model.Movie;
import ro.ubb.movieapp.core.repository.MovieRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * author: radu
 */

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieRepository repository;

    @Override
    public List<Movie> getMoviesByYear(int year, boolean lessThan) {
        return repository.findAll(Sort.by("year").ascending())
                .stream()
                .filter(movie -> {
                    if (lessThan)
                        return movie.getYear()<=year;
                    return movie.getYear()>year;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Movie> getMoviesWithActorsByYear(int year, boolean lessThan) {
        if (lessThan)
            return repository.findAllWithActorsLess(year);
        return repository.findAllWithActorsGreater(year);
    }

    @Override
    @Transactional
    public void deleteActor(Long movieId, Long actorId) {
        Optional<Movie> movie = repository.findByIdQuery(movieId);
        movie.ifPresent(elem->{
            Actor actor=elem.getActors().stream().filter(filtering->filtering.getId()==actorId).findFirst().get();
            elem.getActors().remove(actor);
        });
    }
}
