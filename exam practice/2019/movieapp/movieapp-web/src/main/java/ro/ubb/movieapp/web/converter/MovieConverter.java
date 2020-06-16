package ro.ubb.movieapp.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.movieapp.core.model.BaseEntity;
import ro.ubb.movieapp.core.model.Movie;
import ro.ubb.movieapp.web.dto.ActorDto;
import ro.ubb.movieapp.web.dto.BaseDto;
import ro.ubb.movieapp.web.dto.MovieDto;

import java.util.stream.Collectors;

@Component
public class MovieConverter extends BaseConverter<Movie, MovieDto> {
    @Override
    public Movie convertDtoToModel(MovieDto dto) {
        Movie movie = Movie.builder()
                .title(dto.getTitle())
                .year(dto.getYear())
                //.actors(dto.getActors())
                .build();
        movie.setId(dto.getId());
        return movie;
    }

    @Override
    public MovieDto convertModelToDto(Movie movie) {
        MovieDto dto = MovieDto.builder()
                .title(movie.getTitle())
                .year(movie.getYear())
                .build();
        dto.setId(movie.getId());
        return dto;
    }
}
