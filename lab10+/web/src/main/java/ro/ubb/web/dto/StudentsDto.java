package ro.ubb.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Created by radu.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentsDto implements Serializable {
    private List<StudentDto> students;
}
