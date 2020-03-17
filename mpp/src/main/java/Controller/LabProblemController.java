package Controller;
import Model.Exceptions.RepositoryException;
import Model.Exceptions.ValidatorException;
import Model.LabProblem;
import Model.Student;
import Repository.RepositoryInterface;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
    public void addProblem(LabProblem problem) throws ValidatorException, RepositoryException, IOException, ParserConfigurationException, TransformerException, SAXException {
        Optional<LabProblem> optional = repository.add(problem);
        if (optional.isPresent())
            throw new RepositoryException("Id already exists");

    }

    /**
     * Removes the given problem from the repository.
     * @param problem - given problem
     */
    public void deleteProblem(LabProblem problem) throws RepositoryException, IOException, TransformerException, ParserConfigurationException {
        this.assignmentController
                .getAllAssignments()
                .stream()
                .filter(assignment -> assignment.getId().getSecond().equals(problem.getId()))
                .forEach(a -> {
                    try {
                        this.assignmentController.deleteAssignment(a);
                    } catch (IOException | RepositoryException | TransformerException | ParserConfigurationException e) {
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
    public void updateProblem(LabProblem problem) throws ValidatorException, RepositoryException, IOException, TransformerException, ParserConfigurationException {
        Optional<LabProblem> optional = repository.update(problem);
        if (optional.isPresent())
            throw new RepositoryException("Problem doesn't exist");
    }

    /**
     * Gets the problem which has a given id.
     * @param id - given problem id
     * @return Problem in the repository with the given id.
     */
    public LabProblem getProblemById(long id) {
        Optional<LabProblem> optional = this.repository.findById(id);
        return optional.orElse(null);
    }

    /**
     * Returns all lab problems currently in the repository.
     * @return HashSet containing all lab problems in the repository.
     */
    public Set<LabProblem> getAllProblems() {
        Iterable<LabProblem> problems = repository.getAll();
        return StreamSupport.stream(problems.spliterator(), false)
                .collect(Collectors.toSet());
    }

    /**
     * Returns all lab problems whose score is greater than or equal to the given minScore.
     * @param minScore - integer.
     * @return Set containing the above lab problems.
     */
    public Set<LabProblem> filterProblemsByScore(int minScore) {
        Iterable<LabProblem> problems = repository.getAll();
        return StreamSupport.stream(problems.spliterator(), false)
                .filter(lp -> lp.getScore() >= minScore)
                .collect(Collectors.toSet());
    }

    /**
     * Returns all lab problems sorted descending by score.
     * @return List containing said problems.
     */
    public List<LabProblem> sortProblemsDescendingByScore(){
        Iterable<LabProblem> problems = repository.getAll();
        return StreamSupport.stream(problems.spliterator(),false)
                .sorted((o1, o2) -> o2.getScore()-o1.getScore())
                .collect(Collectors.toList());
    }
}