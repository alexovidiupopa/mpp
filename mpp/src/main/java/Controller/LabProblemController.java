package Controller;

import Model.Assignment;
import Model.Exceptions.RepositoryException;
import Model.Exceptions.ValidatorException;
import Model.LabProblem;
import Repository.RepositoryInterface;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class LabProblemController {

    private RepositoryInterface<Long, LabProblem> repository;

    private AssignmentController assignmentController;

    public void setAssignmentController(AssignmentController assignmentController) {
        this.assignmentController = assignmentController;
    }

    public LabProblemController(RepositoryInterface<Long, LabProblem> repository) {
        this.repository = repository;
    }

    /**
     * Adds the given lab problem to the repository.
     * @param problem - given problem
     * @throws ValidatorException if problem is invalid
     * @throws IllegalArgumentException if problem is null.
     */
    public void addProblem(LabProblem problem) throws ValidatorException, RepositoryException, IOException, ParserConfigurationException, TransformerException, SAXException, SQLException {
        Optional<LabProblem> optional = repository.add(problem);
        if (optional.isPresent())
            throw new RepositoryException("Id already exists");

    }

    /**
     * Removes the given problem from the repository.
     * @param problem - given problem
     */
    public void deleteProblem(LabProblem problem) throws RepositoryException, IOException, TransformerException, ParserConfigurationException, SQLException {
        this.assignmentController
                .getAllAssignments()
                .stream()
                .filter(assignment -> assignment.getId().getSecond().equals(problem.getId()))
                .forEach(a -> {
                    try {
                        this.assignmentController.deleteAssignment(a);
                    } catch (IOException | RepositoryException | TransformerException | ParserConfigurationException | SQLException e) {
                        e.printStackTrace();
                    }
                });
        Optional<LabProblem> optional = repository.delete(problem.getId());
        if (!optional.isPresent())
            throw new RepositoryException("Problem doesn't exist");
    }
    /**
     * Updates the given problem in the repository.
     * @param problem - given problem
     * @throws ValidatorException if the problem is not valid
     */
    public void updateProblem(LabProblem problem) throws ValidatorException, RepositoryException, IOException, TransformerException, ParserConfigurationException, SQLException {
        Optional<LabProblem> optional = repository.update(problem);
        if (optional.isPresent())
            throw new RepositoryException("Problem doesn't exist");
    }

    /**
     * Gets the problem which has a given id.
     * @param id - given problem id
     * @return Problem in the repository with the given id.
     */
    public LabProblem getProblemById(long id) throws SQLException {
        Optional<LabProblem> optional = this.repository.findById(id);
        return optional.orElse(null);
    }

    /**
     * Returns all lab problems currently in the repository.
     * @return HashSet containing all lab problems in the repository.
     */
    public Set<LabProblem> getAllProblems() throws SQLException {
        Iterable<LabProblem> problems = repository.getAll();
        return StreamSupport.stream(problems.spliterator(), false)
                .collect(Collectors.toSet());
    }

    /**
     * Returns all lab problems whose score is greater than or equal to the given minScore.
     * @param minScore - integer.
     * @return Set containing the above lab problems.
     */
    public Set<LabProblem> filterProblemsByScore(int minScore) throws SQLException {
        Iterable<LabProblem> problems = repository.getAll();
        return StreamSupport.stream(problems.spliterator(), false)
                .filter(lp -> lp.getScore() >= minScore)
                .collect(Collectors.toSet());
    }

    /**
     * Returns all lab problems sorted descending by score.
     * @return List containing said problems.
     */
    public List<LabProblem> sortProblemsDescendingByScore() throws SQLException {
        Iterable<LabProblem> problems = repository.getAll();
        return StreamSupport.stream(problems.spliterator(),false)
                .sorted((o1, o2) -> o2.getScore()-o1.getScore())
                .collect(Collectors.toList());
    }
    /**
     * Get the problem which has been assigned the most times.
     * @return a LabProblem respecting fore-mentioned property.
     */
    public LabProblem getProblemAssignedMostTimes() throws SQLException {
        Set<Assignment> assignments = assignmentController.getAllAssignments();
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
}