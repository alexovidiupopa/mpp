package ro.ubb.web.dto;

import lombok.*;

import java.io.Serializable;

/**
 * Created by radu.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class StudentDto extends BaseDto<Long> implements Serializable {
    private String serialNumber;
    private String name;
    private int groupNumber;

    public StudentDto(long id, String serialNumber, String name, int group) {
        super(id);
        this.serialNumber = serialNumber;
        this.name = name;
        this.groupNumber = group;
    }
}
