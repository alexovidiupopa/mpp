package ro.ubb.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.core.model.Assignment;
import ro.ubb.core.utils.Pair;
import ro.ubb.web.dto.AssignmentDto;

@Component
public class AssignmentConverter extends BaseConverter<Pair<Long, Long>, Assignment, AssignmentDto> {
    @Override
    public Assignment convertDtoToModel(AssignmentDto dto) {
        Assignment assignment = Assignment.builder()
                .grade(dto.getGrade())
                .build();
        assignment.setId(dto.getId());
        return assignment;
    }

    @Override
    public AssignmentDto convertModelToDto(Assignment assignment) {
        AssignmentDto assignmentDto = AssignmentDto.builder()
                .grade(assignment.getGrade())
                .build();
        assignmentDto.setId(assignment.getId());
        return assignmentDto;
    }
}
