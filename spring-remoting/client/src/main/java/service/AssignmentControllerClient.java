package service;

import model.Assignment;
import model.Exceptions.MyException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

public class AssignmentControllerClient implements AssignmentControllerInterface {

    @Autowired
    private AssignmentControllerInterface assignmentController;

    @Override
    public void addAssignment(Assignment assignment) throws MyException {
        assignmentController.addAssignment(assignment);
    }

    @Override
    public void deleteAssignment(Assignment assignment) throws MyException {
        assignmentController.deleteAssignment(assignment);
    }

    @Override
    public void updateAssignment(Assignment assignment) throws MyException {
        assignmentController.updateAssignment(assignment);
    }

    @Override
    public Set<Assignment> getAllAssignments() {
        return assignmentController.getAllAssignments();
    }

    @Override
    public Set<Assignment> filterAssignmentsByGrade(double g) {
        return assignmentController.filterAssignmentsByGrade(g);
    }

    @Override
    public List<Assignment> sortAssignmentsAscendingById() {
        return assignmentController.sortAssignmentsAscendingById();
    }
}
