package ro.ubb.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.core.model.LabProblem;
import ro.ubb.web.dto.LabProblemDto;

@Component
public class LabProblemConverter extends BaseConverter<Long, LabProblem, LabProblemDto> {
    @Override
    public LabProblem convertDtoToModel(LabProblemDto dto) {
        LabProblem problem = LabProblem.builder()
                .description(dto.getDescription())
                .score(dto.getScore())
                .build();
        problem.setId(dto.getId());
        return problem;
    }

    @Override
    public LabProblemDto convertModelToDto(LabProblem labProblem) {
        LabProblemDto problem = LabProblemDto.builder()
                .description(labProblem.getDescription())
                .score(labProblem.getScore())
                .build();
        problem.setId(labProblem.getId());
        return problem;
    }
}
