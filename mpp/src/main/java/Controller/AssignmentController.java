package Controller;

import Model.Assignment;
import Model.Exceptions.RepositoryException;
import Model.Exceptions.ValidatorException;
import Model.Student;
import Repository.RepositoryInterface;
import Utils.Pair;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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
    public void addAssignment(Assignment assignment) throws ValidatorException, IOException, RepositoryException {
        Optional<Assignment> optional = repository.add(assignment);
        if (optional.isPresent())
            throw new RepositoryException("Id already exists");
    }

    /**
     * Removes the given assignment from the repository.
     * @param assignment - given assignment
     */
    public void deleteAssignment(Assignment assignment) throws IOException, RepositoryException {
        Optional<Assignment> optional = repository.delete(assignment.getId());
        if (!optional.isPresent())
            throw new RepositoryException("Assignments doesn't exist");
    }

    /**
     * Updates the given assignment in the repository.
     * @param assignment - given assignment
     * @throws ValidatorException if the assignment is not valid
     */
    public void updateAssignment(Assignment assignment) throws ValidatorException, IOException, RepositoryException {
        Optional<Assignment> optional = repository.update(assignment);
        if (optional.isPresent())
            throw new RepositoryException("Assignments doesn't exist");
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
                .filter(assignment -> assignment.getGrade() != null && assignment.getGrade() >= g)
                .collect(Collectors.toSet());
    }

    /**
     * Returns all assignments sorted ascending by their student
     * In case of equality, assignments are sorted ascending by their problem
     * @return List containing said assignments.
     */
    public List<Assignment> sortAssignmentsAscendingById(){
        Iterable<Assignment> assignments = repository.getAll();
        return StreamSupport.stream(assignments.spliterator(),false)
                .sorted((a1, a2) -> (int) (a1.getId().getSecond() - a2.getId().getSecond()))
                .sorted((a1, a2) -> (int) (a1.getId().getFirst() - a2.getId().getFirst()))
                .collect(Collectors.toList());
    }

}