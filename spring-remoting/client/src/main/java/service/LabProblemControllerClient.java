package service;

import model.Exceptions.MyException;
import model.LabProblem;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

public class LabProblemControllerClient implements LabProblemControllerInterface {
    @Autowired
    private LabProblemControllerInterface labProblemController;

    @Override
    public void addProblem(LabProblem problem) throws MyException {
        labProblemController.addProblem(problem);
    }

    @Override
    public void deleteProblem(LabProblem problem) throws MyException {
        labProblemController.deleteProblem(problem);
    }

    @Override
    public void updateProblem(LabProblem problem) throws MyException {
        labProblemController.updateProblem(problem);
    }

    @Override
    public LabProblem getProblemById(long id) throws MyException {
        return labProblemController.getProblemById(id);
    }

    @Override
    public Set<LabProblem> getAllProblems() {
        return labProblemController.getAllProblems();
    }

    @Override
    public Set<LabProblem> filterProblemsByScore(int minScore) {
        return labProblemController.filterProblemsByScore(minScore);
    }

    @Override
    public List<LabProblem> sortProblemsDescendingByScore() {
        return labProblemController.sortProblemsDescendingByScore();
    }

    @Override
    public LabProblem getProblemAssignedMostTimes() throws MyException {
        return labProblemController.getProblemAssignedMostTimes();
    }
}
