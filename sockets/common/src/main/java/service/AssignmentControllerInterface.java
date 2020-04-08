package service;

import model.Assignment;
import model.Exceptions.MyException;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface AssignmentControllerInterface {

    CompletableFuture<Boolean> addAssignment(Assignment assignment) throws MyException;

    CompletableFuture<Boolean> deleteAssignment(Assignment assignment) throws MyException;

    CompletableFuture<Boolean> updateAssignment(Assignment assignment) throws MyException;

    CompletableFuture<Set<Assignment>> getAllAssignments();

    CompletableFuture<Set<Assignment>> filterAssignmentsByGrade(double g);

    CompletableFuture<List<Assignment>> sortAssignmentsAscendingById();

}
