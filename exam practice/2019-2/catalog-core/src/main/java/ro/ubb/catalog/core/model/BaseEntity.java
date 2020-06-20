package ro.ubb.catalog.core.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@MappedSuperclass
@NoArgsConstructor
@Data
@ToString
public abstract class BaseEntity<ID extends Serializable>
        implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long ID;
}
