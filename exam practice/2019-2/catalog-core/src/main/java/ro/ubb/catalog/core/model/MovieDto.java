package ro.ubb.catalog.core.model;

import lombok.*;

import javax.persistence.Column;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true)
@Builder
public class MovieDto {
    private long id;
    private String title;
    private int year;
    private List<Actor> actors;
}
