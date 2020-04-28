package ro.ubb.core.service;

import ro.ubb.core.model.Exceptions.MyException;
import ro.ubb.core.model.LabProblem;

import java.util.List;

public interface ILabProblemService {
    void addProblem(LabProblem problem) throws MyException;

    void deleteProblem(Long id) throws MyException;

    void updateProblem(LabProblem problem) throws MyException;

    LabProblem getProblemById(long id) throws MyException;

    List<LabProblem> getAllProblems();

    List<LabProblem> filterProblemsByScore(int minScore);

    List<LabProblem> sortProblemsDescendingByScore();

    LabProblem getProblemAssignedMostTimes() throws MyException;
}
