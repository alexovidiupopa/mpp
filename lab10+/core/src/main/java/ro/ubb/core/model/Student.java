package ro.ubb.core.model;

import lombok.*;

import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class Student extends BaseEntity<Long>{
    @NonNull
    private String serialNumber;
    @NonNull
    private String name;
    @Min(1)
    @Max(7)
    private int groupNumber;
}
