package ro.ubb.movieapp.core.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.ubb.movieapp.core.model.Movie;

import java.util.List;
import java.util.Optional;

/**
 * author: radu
 */
public interface MovieRepository extends MovieAppRepository<Movie, Long> {
    @Query("select m from Movie m where m.year>:year")
    @EntityGraph(value="movieWithActors", type =
            EntityGraph.EntityGraphType.LOAD)
    List<Movie> findAllWithActorsGreater(@Param("year") int year);

    @Query("select m from Movie m where m.year<=:year")
    @EntityGraph(value="movieWithActors", type =
            EntityGraph.EntityGraphType.LOAD)
    List<Movie> findAllWithActorsLess(@Param("year") int year);

    @Query("select m from Movie m where m.id=:id")
    @EntityGraph(value="movieWithActors", type =
            EntityGraph.EntityGraphType.LOAD)
    Optional<Movie> findByIdQuery(@Param("id") long id);

}
