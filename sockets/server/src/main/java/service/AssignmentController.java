package service;

import model.Assignment;
import model.Exceptions.ValidatorException;
import org.xml.sax.SAXException;
import repository.RepositoryInterface;
import utils.Direction;
import utils.Pair;
import utils.Sort;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AssignmentController implements AssignmentControllerInterface {

    ExecutorService executor;
    RepositoryInterface<Pair<Long, Long>, Assignment> repository;
    StudentControllerInterface studentController;
    LabProblemControllerInterface problemController;

    public AssignmentController(ExecutorService executorService, RepositoryInterface<Pair<Long, Long>, Assignment> labProblemRepository) {
        this.executor = executorService;
        this.repository = labProblemRepository;
    }

    public void setStudentController(StudentControllerInterface studentController) {
        this.studentController = studentController;
    }

    public void setProblemController(LabProblemControllerInterface problemController) {
        this.problemController = problemController;
    }

    @Override
    public CompletableFuture<Boolean> addAssignment(Assignment assignment) {
        return CompletableFuture.supplyAsync(() ->  {
            try {
                return !repository.add(assignment).isPresent();
            } catch (ValidatorException | IOException | TransformerException | SAXException | ParserConfigurationException | SQLException e) {
                throw new RuntimeException(e.getCause());
            }
        }, this.executor);
    }

    @Override
    public CompletableFuture<Boolean> deleteAssignment(Assignment assignment) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return repository.delete(assignment.getId()).isPresent();
            } catch (IOException | TransformerException | ParserConfigurationException | SQLException e) {
                throw new RuntimeException(e.getCause());
            }
        }, this.executor);
    }

    @Override
    public CompletableFuture<Boolean> updateAssignment(Assignment assignment) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return !repository.update(assignment).isPresent();
            } catch (ValidatorException | IOException | TransformerException | ParserConfigurationException | SQLException e) {
                throw new RuntimeException(e.getCause());
            }
        }, this.executor);
    }

    @Override
    public CompletableFuture<Set<Assignment>> getAllAssignments() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return StreamSupport.stream(this.repository.getAll().spliterator(), false)
                        .collect(Collectors.toSet());
            } catch (SQLException e) {
                throw new RuntimeException(e.getCause());
            }
        }, this.executor);
    }

    @Override
    public CompletableFuture<Set<Assignment>> filterAssignmentsByGrade(double g) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return StreamSupport.stream(this.repository.getAll().spliterator(), false)
                        .filter(assignment -> assignment.getGrade() != null && assignment.getGrade() >= g)
                        .collect(Collectors.toSet());
            } catch (SQLException e) {
                throw new RuntimeException(e.getCause());
            }
        }, this.executor);
    }

    @Override
    public CompletableFuture<List<Assignment>> sortAssignmentsAscendingById() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return StreamSupport.stream(this.repository.getAll(new Sort(Direction.ASC, "id")).spliterator(), false)
                        .collect(Collectors.toList());
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e.getCause());
            }
        }, this.executor);
    }

}
