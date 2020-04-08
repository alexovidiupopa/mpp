package service;

import model.Assignment;
import model.Exceptions.MyException;

import java.util.List;
import java.util.Set;

public interface AssignmentControllerInterface {

    void addAssignment(Assignment assignment) throws MyException;

    void deleteAssignment(Assignment assignment) throws MyException;

    void updateAssignment(Assignment assignment) throws MyException;

    Set<Assignment>getAllAssignments();

    Set<Assignment> filterAssignmentsByGrade(double g);

    List<Assignment> sortAssignmentsAscendingById();
}
