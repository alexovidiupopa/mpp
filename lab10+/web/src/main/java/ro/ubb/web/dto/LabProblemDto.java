package ro.ubb.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class LabProblemDto extends BaseDto<Long> {
    private String description;
    private int score;

    public LabProblemDto(long id, String description, int score) {
        super(id);
        this.description=description;
        this.score=score;
    }
}
