package ro.ubb.movieapp.web.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class MovieWithActorDto extends BaseDto {
    private String title;
    private int year;
    private List<ActorDto> actors;
}
