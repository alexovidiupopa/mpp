package ro.ubb.web.controller;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.core.model.Exceptions.MyException;
import ro.ubb.core.service.ILabProblemService;
import ro.ubb.web.converter.LabProblemConverter;
import ro.ubb.web.dto.LabProblemDto;
import ro.ubb.web.dto.LabProblemsDto;
import ro.ubb.web.dto.StudentDto;

import java.util.List;

@RestController
public class LabProblemController {

    public static final Logger log= LoggerFactory.getLogger(LabProblemController.class);

    @Autowired
    private ILabProblemService labProblemService;

    @Autowired
    private LabProblemConverter converter;

    @RequestMapping(value="/problems/get-page/pageno={pageNo},size={size}",method=RequestMethod.GET)
    List<LabProblemDto> getStudentsOnPage(@PathVariable Integer pageNo, @PathVariable Integer size){
        log.trace("begin get page={}",pageNo);
        return converter.convertModelsToDtos(labProblemService.getProblemsOnPage(pageNo,size));
    }

    @RequestMapping(value = "/problems", method = RequestMethod.GET)
    List<LabProblemDto> getProblems() {
        log.trace("begin get problems");
        List<LabProblemDto> cpy = new LabProblemsDto(converter
                .convertModelsToDtos(labProblemService.getAllProblems())).getProblems();
        log.trace("end get problems={}",cpy);
        return cpy;
    }

    @RequestMapping(value = "/problems/sort", method = RequestMethod.GET)
    List<LabProblemDto> getProblemsSorted() {
        log.trace("begin sort problems");
        List<LabProblemDto> cpy = new LabProblemsDto(converter
                .convertModelsToDtos(labProblemService.sortProblemsDescendingByScore())).getProblems();
        log.trace("end sort problems={}",cpy);
        return cpy;
    }

    @RequestMapping(value = "/problems/filter/{score}", method = RequestMethod.GET)
    List<LabProblemDto> getProblemsFiltered(@PathVariable String score) {
        log.trace("begin filter problems score={}",score);
        List<LabProblemDto> cpy = new LabProblemsDto(converter
                .convertModelsToDtos(labProblemService.filterProblemsByScore(Integer.parseInt(score)))).getProblems();
        log.trace("end filter problems={}",cpy);
        return cpy;
    }
    @CrossOrigin
    @RequestMapping(value = "/problems", method = RequestMethod.POST)
    ResponseEntity<?> saveProblem(@RequestBody LabProblemDto labProblemDto) {
        log.trace("begin add problem={}", labProblemDto);
        try {
            labProblemService.addProblem(
                    converter.convertDtoToModel(labProblemDto)
            );
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MyException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @CrossOrigin
    @RequestMapping(value = "/problems", method = RequestMethod.PUT)
    ResponseEntity<?> updateProblem(@RequestBody LabProblemDto labProblemDto) {
        log.trace("begin update problem={}", labProblemDto);
        try {
            labProblemService.updateProblem(converter.convertDtoToModel(labProblemDto));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MyException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/problems/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteProblem(@PathVariable Long id){
        log.trace("begin delete problem with id={}", id);
        try {
            labProblemService.deleteProblem(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MyException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @SneakyThrows
    @RequestMapping(value = "/problems/mostassigned", method = RequestMethod.GET)
    LabProblemDto getProblemAssignedMostTimes(){
        log.trace("getProblemAssignedMostTimes - method entered");
        return converter.convertModelToDto(labProblemService.getProblemAssignedMostTimes());
    }
}
