package ro.ubb.web.controller;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.core.service.IAssignmentService;
import ro.ubb.web.converter.AssignmentConverter;
import ro.ubb.web.dto.AssignmentDto;
import ro.ubb.web.dto.AssignmentsDto;

@RestController
public class AssignmentController {
    public static final Logger log= LoggerFactory.getLogger(AssignmentController.class);
    @Autowired
    private IAssignmentService assignmentService;

    @Autowired
    private AssignmentConverter converter;

    @RequestMapping(value = "/assignments", method = RequestMethod.GET)
    AssignmentsDto getAssignments(){
        log.trace("begin get assignments");
        AssignmentsDto cpy = new AssignmentsDto(converter.convertModelsToDtos(
                assignmentService.getAllAssignments()
        ));
        log.trace("end get assignments={}", cpy);
        return cpy;
    }

    @RequestMapping(value = "/assignments/sort", method = RequestMethod.GET)
    AssignmentsDto getAssignmentsSorted(){
        log.trace("begin sort assignments");
        AssignmentsDto cpy = new AssignmentsDto(converter.convertModelsToDtos(
                assignmentService.sortAssignmentsAscendingById()
        ));
        log.trace("end sort assignments={}", cpy);
        return cpy;
    }
    @RequestMapping(value = "/assignments/filter/{grade}", method = RequestMethod.GET)
    AssignmentsDto getAssignmentsFiltered(@PathVariable String grade){
        log.trace("begin filter assignments grade={}",grade);
        AssignmentsDto cpy = new AssignmentsDto(converter.convertModelsToDtos(
                assignmentService.filterAssignmentsByGrade(Integer.parseInt(grade))
        ));
        log.trace("end filter assignments={}", cpy);
        return cpy;
    }

    @SneakyThrows
    @RequestMapping(value="/assignments", method = RequestMethod.POST)
    ResponseEntity<?> saveAssignment(@RequestBody AssignmentDto assignmentDto){
        log.trace("begin add assignment={}", assignmentDto);
        assignmentService.addAssignment(
                converter.convertDtoToModel(assignmentDto)
        );
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @SneakyThrows
    @RequestMapping(value = "/assignments", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteAssignment(@RequestBody AssignmentDto assignmentDto){
        log.trace("begin delete assignment={}", assignmentDto);
        assignmentService.deleteAssignment(
                converter.convertDtoToModel(assignmentDto)
        );
        return new ResponseEntity<>(HttpStatus.OK);

    }
    @SneakyThrows
    @RequestMapping(value = "/assignments", method = RequestMethod.PUT)
    ResponseEntity<?> updateAssignment(@RequestBody AssignmentDto assignmentDto){
        log.trace("begin update assignment={}", assignmentDto);
        assignmentService.updateAssignment(
                converter.convertDtoToModel(assignmentDto)
        );
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
