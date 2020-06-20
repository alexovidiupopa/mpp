package ro.ubb.catalog.core.model;


import lombok.*;
import org.springframework.data.jpa.repository.EntityGraph;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="actor")
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Data
@Builder

public class Actor extends BaseEntity<Long> {
    @Column(unique = true,name="name")
    private String name;

    @Column(name="rating")
    private int rating;
    @Column(name = "movie_id")
    private Long movie_id;


}
