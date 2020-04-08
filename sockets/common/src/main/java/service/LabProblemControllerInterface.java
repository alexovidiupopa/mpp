package service;

import model.Exceptions.MyException;
import model.LabProblem;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface LabProblemControllerInterface {

    CompletableFuture<Boolean> addProblem(LabProblem problem) throws MyException;

    CompletableFuture<Boolean> deleteProblem(LabProblem problem) throws MyException;

    CompletableFuture<Boolean> updateProblem(LabProblem problem) throws MyException;

    CompletableFuture<LabProblem> getProblemById(long id);

    CompletableFuture<Set<LabProblem>> getAllProblems();

    CompletableFuture<Set<LabProblem>> filterProblemsByScore(int minScore);

    CompletableFuture<List<LabProblem>> sortProblemsDescendingByScore();

    CompletableFuture<LabProblem> getProblemAssignedMostTimes();

}
