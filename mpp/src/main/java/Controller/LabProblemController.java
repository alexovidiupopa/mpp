package Controller;

import Model.Exceptions.ValidatorException;
import Model.LabProblem;
import Repository.RepositoryInterface;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class LabProblemController {

    private RepositoryInterface<Long, LabProblem> repository;

    public LabProblemController(RepositoryInterface<Long, LabProblem> repository) {
        this.repository = repository;
    }

    public void addLabProblem(LabProblem problem) throws ValidatorException {
        repository.add(problem);
    }

    public Set<LabProblem> getAllProblems() {
        Iterable<LabProblem> problems = repository.getAll();
        return StreamSupport.stream(problems.spliterator(), false)
                .collect(Collectors.toSet());
    }

    public Set<LabProblem> filterProblemsByScore(int minScore) {
        Iterable<LabProblem> problems = repository.getAll();
        return StreamSupport.stream(problems.spliterator(), false)
                .filter(lp -> lp.getScore() >= minScore)
                .collect(Collectors.toSet());
    }

}
