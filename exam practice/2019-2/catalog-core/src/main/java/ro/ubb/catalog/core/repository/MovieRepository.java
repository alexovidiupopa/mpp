package ro.ubb.catalog.core.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.ubb.catalog.core.model.Movie;

import java.util.List;


public interface MovieRepository extends JpaRepository<Movie,Long> {
    @Query("select movie from Movie movie where movie.ID=:movieID")
    @EntityGraph(value = "movieWithActors", type =
            EntityGraph.EntityGraphType.LOAD)
    Movie findMovieWithActors(@Param("movieID") long movieID);
}
