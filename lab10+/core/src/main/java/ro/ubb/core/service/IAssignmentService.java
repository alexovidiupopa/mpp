package ro.ubb.core.service;

import ro.ubb.core.model.Assignment;
import ro.ubb.core.model.Exceptions.MyException;

import java.util.List;

public interface IAssignmentService {
    void addAssignment(Assignment assignment) throws MyException;

    void deleteAssignment(Assignment assignment) throws MyException;

    void updateAssignment(Assignment assignment) throws MyException;

    List<Assignment> getAllAssignments();

    List<Assignment> filterAssignmentsByGrade(double g);

    List<Assignment> sortAssignmentsAscendingById();
}
