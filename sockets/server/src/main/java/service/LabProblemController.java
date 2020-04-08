package service;

import model.Exceptions.MyException;
import model.Exceptions.ValidatorException;
import model.LabProblem;
import org.xml.sax.SAXException;
import repository.RepositoryInterface;
import utils.Direction;
import utils.Sort;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class LabProblemController implements LabProblemControllerInterface {

    ExecutorService executor;
    RepositoryInterface<Long, LabProblem> repository;
    AssignmentControllerInterface assignmentController;

    public LabProblemController(ExecutorService executorService, RepositoryInterface<Long, LabProblem> labProblemRepository) {
        this.executor = executorService;
        this.repository = labProblemRepository;
    }

    public void setAssignmentController(AssignmentControllerInterface assignmentController) {
        this.assignmentController = assignmentController;
    }

    @Override
    public CompletableFuture<Boolean> addProblem(LabProblem problem) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return !this.repository.add(problem).isPresent();
            } catch (ValidatorException | IOException | TransformerException | SAXException | ParserConfigurationException | SQLException e) {
                throw new RuntimeException(e.getCause());
            }
        }, this.executor);
    }

    @Override
    public CompletableFuture<Boolean> deleteProblem(LabProblem problem) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return repository.delete(problem.getId()).isPresent();
            } catch (IOException | TransformerException | ParserConfigurationException | SQLException e) {
                throw new RuntimeException(e.getCause());
            }
        }, this.executor);
    }

    @Override
    public CompletableFuture<Boolean> updateProblem(LabProblem problem) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return !repository.update(problem).isPresent();
            } catch (ValidatorException | IOException | TransformerException | ParserConfigurationException | SQLException e) {
                throw new RuntimeException(e.getCause());
            }
        }, this.executor);
    }

    @Override
    public CompletableFuture<LabProblem> getProblemById(long id) {
        return CompletableFuture.supplyAsync(() ->
        {
            try {
                return this.repository.findById(id).orElse(null);
            } catch (SQLException e) {
                throw new RuntimeException(e.getCause());
            }
        }, this.executor);
    }

    @Override
    public CompletableFuture<Set<LabProblem>> getAllProblems() {
        return CompletableFuture.supplyAsync(() ->
        {
            try {
                return StreamSupport.stream(this.repository.getAll().spliterator(), false)
                        .collect(Collectors.toSet());
            } catch (SQLException e) {
                throw new RuntimeException(e.getCause());
            }
        }, this.executor);
    }

    @Override
    public CompletableFuture<Set<LabProblem>> filterProblemsByScore(int minScore) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return StreamSupport.stream(this.repository.getAll().spliterator(), false)
                        .filter(problem -> problem.getScore() >= minScore)
                        .collect(Collectors.toSet());
            } catch (SQLException e) {
                throw new RuntimeException(e.getCause());
            }
        }, this.executor);
    }

    @Override
    public CompletableFuture<List<LabProblem>> sortProblemsDescendingByScore() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return StreamSupport.stream(this.repository.getAll(new Sort(Direction.DESC, "score")).spliterator(), false)
                        .collect(Collectors.toList());
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e.getCause());
            }
        }, this.executor);
    }

    @Override
    public CompletableFuture<LabProblem> getProblemAssignedMostTimes() {
        return CompletableFuture
                .supplyAsync(() -> assignmentController.getAllAssignments(), this.executor)
                .thenApply(assignments -> {
            try {

                Map<Long, Long> sortedByValue = assignments.get().stream()
                        .collect(Collectors.groupingBy(assignment -> assignment.getId().getSecond(), Collectors.counting()))
                        .entrySet()
                        .stream()
                        .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
                Optional<Map.Entry<Long, Long>> problem = sortedByValue.entrySet().stream().findFirst();
                if (!problem.isPresent())
                    throw new MyException("No problem found");
                return this.getProblemById(problem.get().getKey()).get();
            } catch (InterruptedException | ExecutionException | MyException e) {
                throw new RuntimeException(e.getCause());
            }
        });
    }

}
