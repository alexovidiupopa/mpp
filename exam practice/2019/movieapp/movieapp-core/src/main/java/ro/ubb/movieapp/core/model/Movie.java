package ro.ubb.movieapp.core.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * author: radu
 */

@Entity
@Table(name = "movie")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {"actors"})
@Data
@Builder
@ToString(callSuper = true, exclude = {"actors"})
@NamedEntityGraphs({
        @NamedEntityGraph(name="movieWithActors",
        attributeNodes = @NamedAttributeNode(value="actors"))
})
public class Movie extends BaseEntity<Long> {
    @Column(name = "title", unique = true,nullable = false)
    private String title;

    @Column(name="year", nullable = false)
    private int year;

    //actors
    @OneToMany(mappedBy = "movie", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Actor> actors;


}
