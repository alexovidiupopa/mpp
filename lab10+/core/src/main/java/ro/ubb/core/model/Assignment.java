package ro.ubb.core.model;

import lombok.*;
import ro.ubb.core.utils.Pair;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class Assignment extends BaseEntity<Pair<Long, Long>> {
    private Double grade;

    public Assignment(Pair<Long, Long> longLongPair) {
        super(longLongPair);
    }
}
