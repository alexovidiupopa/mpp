package ro.ubb.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.core.model.Exceptions.MyException;
import ro.ubb.core.service.IAssignmentService;
import ro.ubb.core.utils.Pair;
import ro.ubb.web.converter.AssignmentConverter;
import ro.ubb.web.dto.AssignmentDto;
import ro.ubb.web.dto.AssignmentsDto;
import ro.ubb.web.dto.StudentDto;

import java.util.List;

@RestController
public class AssignmentController {
    public static final Logger log= LoggerFactory.getLogger(AssignmentController.class);
    @Autowired
    private IAssignmentService assignmentService;

    @Autowired
    private AssignmentConverter converter;

    @RequestMapping(value="/assignments/get-page/pageno={pageNo},size={size}",method=RequestMethod.GET)
    List<AssignmentDto> getStudentsOnPage(@PathVariable Integer pageNo, @PathVariable Integer size){
        log.trace("begin get page={}",pageNo);
        return converter.convertModelsToDtos(assignmentService.getAssignmentsOnPage(pageNo,size));
    }

    @RequestMapping(value = "/assignments", method = RequestMethod.GET)
    List<AssignmentDto> getAssignments(){
        log.trace("begin get assignments");
        AssignmentsDto cpy = new AssignmentsDto(converter.convertModelsToDtos(
                assignmentService.getAllAssignments()
        ));
        log.trace("end get assignments={}", cpy);
        return cpy.getAssignments();
    }

    @RequestMapping(value = "/assignments/sort", method = RequestMethod.GET)
    List<AssignmentDto> getAssignmentsSorted(){
        log.trace("begin sort assignments");
        AssignmentsDto cpy = new AssignmentsDto(converter.convertModelsToDtos(
                assignmentService.sortAssignmentsAscendingById()
        ));
        log.trace("end sort assignments={}", cpy);
        return cpy.getAssignments();
    }
    @RequestMapping(value = "/assignments/filter/{grade}", method = RequestMethod.GET)
    List<AssignmentDto> getAssignmentsFiltered(@PathVariable String grade){
        log.trace("begin filter assignments grade={}",grade);
        AssignmentsDto cpy = new AssignmentsDto(converter.convertModelsToDtos(
                assignmentService.filterAssignmentsByGrade(Integer.parseInt(grade))
        ));
        log.trace("end filter assignments={}", cpy);
        return cpy.getAssignments();
    }
    @CrossOrigin
    @RequestMapping(value="/assignments", method = RequestMethod.POST)
    ResponseEntity<?> saveAssignment(@RequestBody AssignmentDto assignmentDto){
        log.trace("begin add assignment={}", assignmentDto);
        try {
            assignmentService.addAssignment(
                    converter.convertDtoToModel(assignmentDto)
            );
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MyException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "/assignments/{sid}/{aid}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteAssignment(@PathVariable Long sid, @PathVariable Long aid){
        log.trace("begin delete assignment with sid={} and aid={}", sid, aid);
        try {
            assignmentService.deleteAssignment(new Pair<>(sid, aid));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MyException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @CrossOrigin
    @RequestMapping(value = "/assignments/{sid}/{aid}", method = RequestMethod.PUT)
    ResponseEntity<?> updateAssignment(@RequestBody AssignmentDto assignmentDto, @PathVariable String sid, @PathVariable String aid){
        log.trace("begin update assignment={}", assignmentDto);
        try {
            assignmentService.updateAssignment(
                    converter.convertDtoToModel(assignmentDto)
            );
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MyException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
