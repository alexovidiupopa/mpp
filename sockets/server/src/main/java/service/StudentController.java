package service;

import model.Exceptions.MyException;
import model.Exceptions.ValidatorException;
import model.Student;
import org.xml.sax.SAXException;
import repository.RepositoryInterface;
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

public class StudentController implements StudentControllerInterface {

    ExecutorService executor;
    RepositoryInterface<Long, Student> repository;
    AssignmentControllerInterface assignmentController;

    public StudentController(ExecutorService executorService, RepositoryInterface<Long, Student> studentRepository) {
        this.executor = executorService;
        this.repository = studentRepository;
    }

    public void setAssignmentController(AssignmentControllerInterface assignmentController) {
        this.assignmentController = assignmentController;
    }

    @Override
    public CompletableFuture<Boolean> addStudent(Student student) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return !this.repository.add(student).isPresent();
            } catch (ValidatorException | IOException | TransformerException | SAXException | ParserConfigurationException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }

    @Override
    public CompletableFuture<Boolean> deleteStudent(Student student) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return repository.delete(student.getId()).isPresent();
            } catch (IOException | TransformerException | ParserConfigurationException | SQLException e) {
                throw new RuntimeException(e.getCause());
            }
        });
    }

    @Override
    public CompletableFuture<Boolean> updateStudent(Student student) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return !this.repository.update(student).isPresent();
            } catch (ValidatorException | IOException | TransformerException | ParserConfigurationException | SQLException e) {
                throw new RuntimeException(e.getCause());
            }
        });
    }

    @Override
    public CompletableFuture<Student> getStudentById(long id) {
        return CompletableFuture.supplyAsync(() ->
        {
            try {
                return this.repository.findById(id).orElse(null);
            } catch (SQLException e) {
                throw new RuntimeException(e.getCause());
            }
        });
    }

    @Override
    public CompletableFuture<Set<Student>> getAllStudents() {
        return CompletableFuture.supplyAsync(() ->
        {
            try {
                return StreamSupport.stream(this.repository.getAll().spliterator(), false)
                        .collect(Collectors.toSet());
            } catch (SQLException e) {
                throw new RuntimeException(e.getCause());
            }
        });
    }

    @Override
    public CompletableFuture<Set<Student>> filterStudentsByName(String s) {
        return CompletableFuture.supplyAsync(() ->
        {
            try {
                return StreamSupport.stream(this.repository.getAll().spliterator(), false)
                        .filter(student -> student.getName().contains(s))
                        .collect(Collectors.toSet());
            } catch (SQLException e) {
                throw new RuntimeException(e.getCause());
            }
        });
    }

    @Override
    public CompletableFuture<List<Student>> sortStudentsAscendingByName() {
        return CompletableFuture.supplyAsync(() ->
        {
            try {
                return StreamSupport.stream(this.repository.getAll(new Sort("name")).spliterator(), false)
                        .collect(Collectors.toList());
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e.getCause());
            }
        });
    }

    @Override
    public CompletableFuture<Set<Student>> getStudentsWhoPassed() {
        return CompletableFuture
                .supplyAsync(() -> assignmentController.getAllAssignments(), this.executor)
                .thenApply(assignments -> {
            try {
            return assignments.get().
                    stream()
                    .filter(assignment -> assignment.getGrade() != null && assignment.getGrade() >= 5.0)
                    .map(assignment -> {
                        try {
                            return this.getStudentById(assignment.getId().getFirst()).get();
                        } catch (InterruptedException | ExecutionException e) {
                            throw new RuntimeException(e.getCause());
                        }
                    })
                    .collect(Collectors.toSet());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e.getCause());
            }
        });
    }

    @Override
    public CompletableFuture<Student> getStudentsWithMostProblems() {
        return CompletableFuture
                .supplyAsync(() -> assignmentController.getAllAssignments(), this.executor)
                .thenApply(assignments -> {
            try {
                Map<Long, Long> countForId = assignments.get().stream()
                        .collect(Collectors.groupingBy(assignment -> assignment.getId().getFirst(), Collectors.counting()));
                Map<Long, Long> sortedByValue = countForId.entrySet()
                        .stream()
                        .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                        .collect(Collectors.toMap(Map.Entry::getKey,
                                Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
                Optional<Map.Entry<Long, Long>> student = sortedByValue.entrySet().stream().findFirst();
                if (!student.isPresent())
                    throw new MyException("No student found");
                return this.getStudentById(student.get().getKey()).get();
            } catch (InterruptedException | ExecutionException | MyException e) {
                throw new RuntimeException(e.getCause());
            }
        });
    }

}
