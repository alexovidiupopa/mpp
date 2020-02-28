package Controller;

import Model.LabProblem;
import Repository.RepositoryInterface;

public class LabProblemController {

    private RepositoryInterface<Long, LabProblem> repository;

    public LabProblemController(RepositoryInterface<Long, LabProblem> repository) {
        this.repository = repository;
    }

}
