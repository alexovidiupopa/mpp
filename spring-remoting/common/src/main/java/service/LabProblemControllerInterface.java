package service;

import model.Exceptions.MyException;
import model.LabProblem;

import java.util.List;
import java.util.Set;

public interface LabProblemControllerInterface {

    void addProblem(LabProblem problem) throws MyException;

    void deleteProblem(LabProblem problem) throws MyException;

    void updateProblem(LabProblem problem) throws MyException;

    LabProblem getProblemById(long id) throws MyException;

    Set<LabProblem> getAllProblems();

    Set<LabProblem> filterProblemsByScore(int minScore);

    List<LabProblem> sortProblemsDescendingByScore();

    LabProblem getProblemAssignedMostTimes() throws MyException;
}
