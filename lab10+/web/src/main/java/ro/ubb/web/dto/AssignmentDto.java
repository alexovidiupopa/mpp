package ro.ubb.web.dto;

import lombok.*;
import ro.ubb.core.utils.Pair;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class AssignmentDto extends BaseDto<Pair<Long, Long>> {
    private Double grade;
}
