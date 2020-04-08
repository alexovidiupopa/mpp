package service;

import model.Assignment;
import model.Exceptions.MyException;
import model.Validators.Validator;
import repository.RepositoryInterface;
import utils.Direction;
import utils.Pair;
import utils.Sort;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AssignmentControllerServer implements AssignmentControllerInterface {

    Validator<Assignment> validator;
    RepositoryInterface<Pair<Long, Long>, Assignment> repository;
    StudentControllerInterface studentController;
    LabProblemControllerInterface problemController;

    public AssignmentControllerServer(Validator<Assignment> validator, RepositoryInterface<Pair<Long, Long>, Assignment> repository) {
        this.validator = validator;
        this.repository = repository;
    }

    public void setStudentController(StudentControllerInterface studentController) {
        this.studentController = studentController;
    }

    public void setProblemController(LabProblemControllerInterface problemController) {
        this.problemController = problemController;
    }

    @Override
    public void addAssignment(Assignment assignment) throws MyException {
        this.validator.validate(assignment);
        if(this.repository.add(assignment).isPresent())
            throw new MyException("Assignment already exists");
    }

    @Override
    public void deleteAssignment(Assignment assignment) throws MyException {
        this.validator.validate(assignment);
        if(!this.repository.delete(assignment.getId()).isPresent())
            throw new MyException("Assignment does not exist");
    }

    @Override
    public void updateAssignment(Assignment assignment) throws MyException {
        this.validator.validate(assignment);
        if(this.repository.update(assignment).isPresent())
            throw new MyException("Assignment does not exist");
    }

    @Override
    public Set<Assignment> getAllAssignments() {
        return StreamSupport.stream(this.repository.getAll().spliterator(), false)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Assignment> filterAssignmentsByGrade(double g) {
        return StreamSupport.stream(this.repository.getAll().spliterator(), false)
                .filter(student -> student.getGrade() >= g)
                .collect(Collectors.toSet());
    }

    @Override
    public List<Assignment> sortAssignmentsAscendingById() {
        return StreamSupport.stream(this.repository.getAll(new Sort(Direction.ASC, "id")).spliterator(), false)
                .collect(Collectors.toList());
    }
}
