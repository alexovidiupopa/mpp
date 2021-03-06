package Controller;

import Model.Assignment;
import Model.Exceptions.RepositoryException;
import Model.Exceptions.ValidatorException;
import Repository.RepositoryInterface;
import Utils.Direction;
import Utils.Pair;
import Utils.Sort;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AssignmentController {

    private RepositoryInterface<Pair<Long, Long>, Assignment> repository;
    private StudentController studentController;
    private LabProblemController problemController;

    public AssignmentController(RepositoryInterface<Pair<Long, Long>, Assignment> repository) {
        this.repository = repository;
    }

    public void setStudentController(StudentController studentController) {
        this.studentController = studentController;
    }

    public void setProblemController(LabProblemController problemController) {
        this.problemController = problemController;
    }

    /**
     * Adds the given assignment to the repository.
     * @param assignment - given assignment
     * @throws ValidatorException if assignment is not valid
     */
    public void addAssignment(Assignment assignment) throws ValidatorException, IOException, RepositoryException, ParserConfigurationException, TransformerException, SAXException, SQLException {
        if(this.studentController.getStudentById(assignment.getId().getFirst()) == null)
            throw new RepositoryException("Student id does not exist");
        if(this.problemController.getProblemById(assignment.getId().getSecond()) == null)
            throw new RepositoryException("Problem id does not exist");
        Optional<Assignment> optional = repository.add(assignment);
        if (optional.isPresent())
            throw new RepositoryException("Id already exists");
    }

    /**
     * Removes the given assignment from the repository.
     * @param assignment - given assignment
     */
    public void deleteAssignment(Assignment assignment) throws IOException, RepositoryException, TransformerException, ParserConfigurationException, SQLException {
        Optional<Assignment> optional = repository.delete(assignment.getId());
        if (!optional.isPresent())
            throw new RepositoryException("Assignments doesn't exist");
    }

    /**
     * Updates the given assignment in the repository.
     * @param assignment - given assignment
     * @throws ValidatorException if the assignment is not valid
     */
    public void updateAssignment(Assignment assignment) throws ValidatorException, IOException, RepositoryException, TransformerException, ParserConfigurationException, SQLException {
        Optional<Assignment> optional = repository.update(assignment);
        if (optional.isPresent())
            throw new RepositoryException("Assignments doesn't exist");
    }

    /**
     * Gets all the assignments currently in the repository.
     * @return HashSet containing all assignments in the repository.
     */
    public Set<Assignment> getAllAssignments() throws SQLException {
        Iterable<Assignment> assignments = repository.getAll();
        return StreamSupport.stream(assignments.spliterator(), false).collect(Collectors.toSet());
    }

    /**
     * Returns all assignments which were given a grade greater or equal to the given grade.
     * * @param g - the given grade
     * @return HashSet containing the above assignments.
     */
    public Set<Assignment> filterAssignmentsByGrade(double g) throws SQLException {
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
    public List<Assignment> sortAssignmentsAscendingById() throws SQLException, ClassNotFoundException {
        Iterable<Assignment> assignments = repository.getAll(new Sort(Direction.ASC, "id"));
        return StreamSupport.stream(assignments.spliterator(),false)
                .collect(Collectors.toList());
    }

}


