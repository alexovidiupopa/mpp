package ro.ubb.catalog.core.model;


import lombok.*;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="movie")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true)
@Builder
@NamedEntityGraphs({
        @NamedEntityGraph(name = "movieWithActors",
                attributeNodes = @NamedAttributeNode(value = "actors"))
})
public class Movie extends BaseEntity<Long>{


    @Column(name = "title", unique = true)
    private String title;
    @Column(name = "year")
    private int year;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="movie_id", referencedColumnName="id")
    private List<Actor> actors;



}
