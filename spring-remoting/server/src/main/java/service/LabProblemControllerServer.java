package service;

import model.Exceptions.MyException;
import model.LabProblem;
import model.Validators.Validator;
import repository.LabProblemRepository;
import repository.RepositoryInterface;
import utils.Direction;
import utils.Sort;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class LabProblemControllerServer implements LabProblemControllerInterface {

    Validator<LabProblem> validator;
    RepositoryInterface<Long, LabProblem> repository;
    AssignmentControllerInterface assignmentController;

    public LabProblemControllerServer(Validator<LabProblem> labProblemValidator, LabProblemRepository labProblemRepository) {
            this.validator=  labProblemValidator;
            this.repository = labProblemRepository;
    }

    public void setAssignmentController(AssignmentControllerInterface assignmentController) {
        this.assignmentController = assignmentController;
    }

    @Override
    public void addProblem(LabProblem problem) throws MyException {
        this.validator.validate(problem);
        if(this.repository.add(problem).isPresent())
            throw new MyException("Problem already exists");
    }

    @Override
    public void deleteProblem(LabProblem problem) throws MyException {
        this.validator.validate(problem);
        if(!this.repository.delete(problem.getId()).isPresent())
            throw new MyException("Problem does not exist");
    }

    @Override
    public void updateProblem(LabProblem problem) throws MyException {
        this.validator.validate(problem);
        if(this.repository.update(problem).isPresent())
            throw new MyException("Problem does not exist");
    }

    @Override
    public LabProblem getProblemById(long id) throws MyException {
        Optional<LabProblem> result = this.repository.findById(id);
        if(result.isPresent())
            return result.get();
        else
            throw new MyException("Problem does not exist");
    }

    @Override
    public Set<LabProblem> getAllProblems() {
        return StreamSupport.stream(this.repository.getAll().spliterator(), false)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<LabProblem> filterProblemsByScore(int minScore) {
        return StreamSupport.stream(this.repository.getAll().spliterator(), false)
                .filter(student -> student.getScore() >= minScore)
                .collect(Collectors.toSet());
    }

    @Override
    public List<LabProblem> sortProblemsDescendingByScore() {
        return StreamSupport.stream(this.repository.getAll(new Sort(Direction.DESC, "score")).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public LabProblem getProblemAssignedMostTimes() throws MyException {
        Map<Long, Long> sortedByValue = assignmentController.getAllAssignments().stream()
                .collect(Collectors.groupingBy(assignment -> assignment.getId().getSecond(), Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        Optional<Map.Entry<Long, Long>> problem = sortedByValue.entrySet().stream().findFirst();
        if (!problem.isPresent())
            throw new MyException("No problem found");
        return this.getProblemById(problem.get().getKey());
    }
}
