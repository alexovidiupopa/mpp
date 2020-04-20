package ro.ubb.web.controller;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.core.service.ILabProblemService;
import ro.ubb.web.converter.LabProblemConverter;
import ro.ubb.web.dto.LabProblemDto;
import ro.ubb.web.dto.LabProblemsDto;

@RestController
public class LabProblemController {

    public static final Logger log= LoggerFactory.getLogger(LabProblemController.class);

    @Autowired
    private ILabProblemService labProblemService;

    @Autowired
    private LabProblemConverter converter;

    @RequestMapping(value = "/problems", method = RequestMethod.GET)
    LabProblemsDto getProblems() {
        log.trace("begin get problems");
        LabProblemsDto cpy = new LabProblemsDto(converter
                .convertModelsToDtos(labProblemService.getAllProblems()));
        log.trace("end get problems={}",cpy);
        return cpy;
    }

    @RequestMapping(value = "/problems/sort", method = RequestMethod.GET)
    LabProblemsDto getProblemsSorted() {
        log.trace("begin sort problems");
        LabProblemsDto cpy = new LabProblemsDto(converter
                .convertModelsToDtos(labProblemService.sortProblemsDescendingByScore()));
        log.trace("end sort problems={}",cpy);
        return cpy;
    }

    @RequestMapping(value = "/problems/filter/{score}", method = RequestMethod.GET)
    LabProblemsDto getProblemsFiltered(@PathVariable String score) {
        log.trace("begin filter problems score={}",score);
        LabProblemsDto cpy = new LabProblemsDto(converter
                .convertModelsToDtos(labProblemService.filterProblemsByScore(Integer.parseInt(score))));
        log.trace("end filter problems={}",cpy);
        return cpy;
    }
    @SneakyThrows
    @RequestMapping(value = "/problems", method = RequestMethod.POST)
    ResponseEntity<?> saveProblem(@RequestBody LabProblemDto labProblemDto) {
        log.trace("begin add problem={}", labProblemDto);
        labProblemService.addProblem(
                converter.convertDtoToModel(labProblemDto)
        );
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @SneakyThrows
    @RequestMapping(value = "/problems", method = RequestMethod.PUT)
    ResponseEntity<?> updateProblem(@RequestBody LabProblemDto labProblemDto) {
        log.trace("begin update problem={}", labProblemDto);
        labProblemService.updateProblem(converter.convertDtoToModel(labProblemDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @SneakyThrows
    @RequestMapping(value = "/problems", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteProblem(@RequestBody LabProblemDto labProblemDto){
        log.trace("begin delete problem={}", labProblemDto);
        labProblemService.deleteProblem(converter.convertDtoToModel(labProblemDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @SneakyThrows
    @RequestMapping(value = "/problems/mostassigned", method = RequestMethod.GET)
    LabProblemDto getProblemAssignedMostTimes(){
        log.trace("getProblemAssignedMostTimes - method entered");
        return converter.convertModelToDto(labProblemService.getProblemAssignedMostTimes());
    }
}