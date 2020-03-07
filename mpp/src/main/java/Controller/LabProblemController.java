package Controller;

import Model.Exceptions.ValidatorException;
import Model.LabProblem;
import Repository.RepositoryInterface;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class LabProblemController {

    private RepositoryInterface<Long, LabProblem> repository;

    public LabProblemController(RepositoryInterface<Long, LabProblem> repository) {
        this.repository = repository;
    }

    /**
     * Adds the given lab problem to the repository.
     * @param problem - given problem
     * @throws ValidatorException if problem is invalid
     * @throws IllegalArgumentException if problem is null.
     */
    public void addProblem(LabProblem problem) throws ValidatorException {
        repository.add(problem);
    }

    /**
     * Removes the given problem from the repository.
     * @param problem - given problem
     */
    public void deleteProblem(LabProblem problem){
        repository.delete(problem.getId());
    }
    /**
     * Updates the given problem in the repository.
     * @param problem - given problem
     * @throws ValidatorException if the problem is not valid
     */
    public void updateProblem(LabProblem problem) throws ValidatorException {
        repository.update(problem);
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
