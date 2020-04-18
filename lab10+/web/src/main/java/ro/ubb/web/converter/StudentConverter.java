package ro.ubb.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.core.model.Student;
import ro.ubb.web.dto.StudentDto;

/**
 * Created by radu.
 */
@Component
public class StudentConverter extends BaseConverter<Student, StudentDto> {
    @Override
    public Student convertDtoToModel(StudentDto dto) {
        Student student = Student.builder()
                .serialNumber(dto.getSerialNumber())
                .name(dto.getName())
                .groupNumber(dto.getGroupNumber())
                .build();
        student.setId(dto.getId());
        return student;
    }

    @Override
    public StudentDto convertModelToDto(Student student) {
        StudentDto dto = StudentDto.builder()
                .serialNumber(student.getSerialNumber())
                .name(student.getName())
                .groupNumber(student.getGroupNumber())
                .build();
        dto.setId(student.getId());
        return dto;
    }
}
