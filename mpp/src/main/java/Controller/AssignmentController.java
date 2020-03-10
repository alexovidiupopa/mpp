package Controller;

import Model.Assignment;
import Model.Exceptions.ValidatorException;
import Model.Student;
import Repository.RepositoryInterface;
import Utils.Pair;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AssignmentController {

    private RepositoryInterface<Pair<Long, Long>, Assignment> repository;

    public AssignmentController(RepositoryInterface<Pair<Long, Long>, Assignment> repository) {
        this.repository = repository;
    }

    /**
     * Adds the given assignment to the repository.
     * @param assignment - given assignment
     * @throws ValidatorException if assignment is not valid
     */
    public void addAssignment(Assignment assignment) throws ValidatorException {
        repository.add(assignment);
    }

    /**
     * Removes the given assignment from the repository.
     * @param assignment - given assignment
     */
    public void deleteAssignment(Assignment assignment) {
        repository.delete(assignment.getId());
    }

    /**
     * Updates the given assignment in the repository.
     * @param assignment - given assignment
     * @throws ValidatorException if the assignment is not valid
     */
    public void updateAssignment(Assignment assignment) throws ValidatorException{
        repository.update(assignment);
    }

    /**
     * Gets all the assignments currently in the repository.
     * @return HashSet containing all assignments in the repository.
     */
    public Set<Assignment> getAllAssignments() {
        Iterable<Assignment> assignments = repository.getAll();
        return StreamSupport.stream(assignments.spliterator(), false).collect(Collectors.toSet());
    }

    /**
     * Returns all assignments which were given a grade grater or equal than the given grade.
     * * @param g - the given grade
     * @return HashSet containing the above assignments.
     */
    public Set<Assignment> filterAssignmentsByGrade(double g) {
        Iterable<Assignment> filtered = repository.getAll();
        return StreamSupport.stream(filtered.spliterator(), false)
                .filter(assignment -> assignment.getGrade() != null && assignment.getGrade() > g)
                .collect(Collectors.toSet());
    }

}
