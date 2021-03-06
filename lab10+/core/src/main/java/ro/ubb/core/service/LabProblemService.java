package ro.ubb.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.core.model.Assignment;
import ro.ubb.core.model.Exceptions.MyException;
import ro.ubb.core.model.Exceptions.RepositoryException;
import ro.ubb.core.model.LabProblem;
import ro.ubb.core.model.Student;
import ro.ubb.core.model.Validators.LabProblemValidator;
import ro.ubb.core.repository.LabProblemRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class LabProblemService implements ILabProblemService {

    public static final Logger log = LoggerFactory.getLogger(LabProblemService.class);

    @Autowired
    private LabProblemValidator validator;

    @Autowired
    private LabProblemRepository labProblemRepository;

    @Autowired
    private IAssignmentService assignmentService;

    @Override
    @Transactional
    public void addProblem(LabProblem problem) throws MyException {
        log.trace("addProblem - method entered problem={}",problem);
        validator.validate(problem);
        if (labProblemRepository.existsById(problem.getId()))
            throw new RepositoryException("Lab problem already exists");
        LabProblem prb = labProblemRepository.save(problem);
        log.trace("addProblem - method finished problem={}", prb);
    }

    @Override
    @Transactional
    public void deleteProblem(Long id) throws MyException {
        log.trace("deleteProblem - method entered={}",id);
        if (!labProblemRepository.existsById(id))
            throw new RepositoryException("Lab problem doesn't exist");
        labProblemRepository.delete(labProblemRepository.getOne(id));
        log.trace("deleteProblem - method finished");
    }

    @Override
    @Transactional
    public void updateProblem(LabProblem problem) throws MyException {
        log.trace("updateProblem - method entered: problem={}", problem);
        validator.validate(problem);
        if (!labProblemRepository.existsById(problem.getId()))
            throw new RepositoryException("Lab problem doesn't exist");
        labProblemRepository.findById(problem.getId())
                .ifPresent(p -> {
                    p.setDescription(problem.getDescription());
                    p.setScore(problem.getScore());
                    log.debug("updateProblem - updated: p={}", p);
                });
        log.trace("updateProblem - method finished");
    }

    @Override
    public LabProblem getProblemById(long id) throws MyException {
        log.trace("getProblemById - method entered={}", id);
        return labProblemRepository.findById(id).get();
    }

    @Override
    public List<LabProblem> getAllProblems() {
        log.trace("getAllProblems - method entered");
        return labProblemRepository.findAll();
    }

    @Override
    public List<LabProblem> filterProblemsByScore(int minScore) {
        log.trace("filterProblemsByScore - method entered={}",minScore);
        return labProblemRepository.findAll()
                .stream()
                .filter(labProblem -> labProblem.getScore()>=minScore)
                .collect(Collectors.toList());
    }

    @Override
    public List<LabProblem> sortProblemsDescendingByScore() {
        log.trace("sortProblemsDescendingByScore - method entered");
        Iterable<LabProblem> problems = labProblemRepository.findAll(Sort.by("score").descending());
        return StreamSupport.stream(problems.spliterator(),false)
                .collect(Collectors.toList());
    }

    @Override
    public LabProblem getProblemAssignedMostTimes() throws MyException {
        log.trace("getProblemAssignedMostTimes - method entered");
        List<Assignment> assignments = assignmentService.getAllAssignments();
        Map<Long, Long> countForId = assignments.stream()
                .collect(Collectors.groupingBy(assignment -> assignment.getId().getSecond(), Collectors.counting()));
        Map<Long, Long> sortedByValue = countForId.entrySet()
                .stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        Optional<Map.Entry<Long, Long>> problem = sortedByValue.entrySet().stream().findFirst();
        return this.getProblemById(problem.get().getKey());
    }

    @Override
    public List<LabProblem> getProblemsOnPage(Integer pageNo, Integer size) {
        Pageable page = PageRequest.of(pageNo, size);
        Page<LabProblem> pagedResult = labProblemRepository.findAll(page);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }
}
