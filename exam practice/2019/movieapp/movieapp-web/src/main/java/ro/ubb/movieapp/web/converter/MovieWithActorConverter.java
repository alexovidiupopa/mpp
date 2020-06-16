package ro.ubb.movieapp.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.movieapp.core.model.Movie;
import ro.ubb.movieapp.web.dto.ActorDto;
import ro.ubb.movieapp.web.dto.MovieWithActorDto;

import java.util.stream.Collectors;

@Component
public class MovieWithActorConverter extends BaseConverter<Movie, MovieWithActorDto> {
    @Override
    public Movie convertDtoToModel(MovieWithActorDto dto) {
        Movie movie = Movie.builder()
                .title(dto.getTitle())
                .year(dto.getYear())
                //.actors(dto.getActors())
                .build();
        movie.setId(dto.getId());
        return movie;
    }

    @Override
    public MovieWithActorDto convertModelToDto(Movie movie) {
        MovieWithActorDto dto = MovieWithActorDto.builder()
                .title(movie.getTitle())
                .year(movie.getYear())
                .actors(movie.getActors().stream().map(actor-> ActorDto.builder()
                        .id(actor.getId())
                        .name(actor.getName())
                        .rating(actor.getRating()).build()).collect(Collectors.toList()))
                .build();
        dto.setId(movie.getId());
        return dto;
    }
}
