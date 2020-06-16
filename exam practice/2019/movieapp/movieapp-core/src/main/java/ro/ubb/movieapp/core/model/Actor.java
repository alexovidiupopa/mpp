package ro.ubb.movieapp.core.model;

import ch.qos.logback.core.net.server.Client;
import lombok.*;

import javax.persistence.*;

/**
 * author: radu
 */


@Entity
@Table(name="actor")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {"movie"})
@Data
@Builder
@ToString(callSuper = true, exclude = {"movie"})
public class Actor extends BaseEntity<Long> {
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "mid")
    private Movie movie;

    @Column(name="name", nullable = false,unique = true)
    private String name;

    @Column(name = "rating",nullable = false)
    private int rating;


}
