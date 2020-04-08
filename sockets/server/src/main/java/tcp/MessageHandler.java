package tcp;

import message.Message;
import model.Assignment;
import model.Exceptions.MyException;
import model.LabProblem;
import model.Student;
import service.AssignmentControllerInterface;
import service.LabProblemControllerInterface;
import service.StudentControllerInterface;
import utils.Factory;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class MessageHandler {

    StudentControllerInterface studentController;
    LabProblemControllerInterface labProblemController;
    AssignmentControllerInterface assignmentController;

    public MessageHandler(StudentControllerInterface studentController, LabProblemControllerInterface labProblemController, AssignmentControllerInterface assignmentController) {
        this.studentController = studentController;
        this.labProblemController = labProblemController;
        this.assignmentController = assignmentController;
    }

    public Message addStudent(Message request){
        Student parameter = Factory.messageToStudent(request.getBody());
        try {
            if(studentController.addStudent(parameter).get())
                return new Message("ok", "ok");
            else
                return new Message("error", "Student already exists");
        }
        catch (InterruptedException | ExecutionException | RuntimeException | MyException e) {
            System.out.println(e.getCause().getMessage());
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message deleteStudent(Message request){
        Student parameter = Factory.messageToStudent(request.getBody());
        try {
            if(studentController.deleteStudent(parameter).get())
                return new Message("ok", "ok");
            else
                return new Message("error", "Student does not exist");
        }
        catch (InterruptedException | ExecutionException | MyException e) {
            System.out.println(e.getCause().getMessage());
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message updateStudent(Message request){
        Student parameter = Factory.messageToStudent(request.getBody());
        try {
            if(studentController.updateStudent(parameter).get())
                return new Message("ok", "ok");
            else
                return new Message("error", "Student does not exist");
        }
        catch (InterruptedException | ExecutionException | MyException e) {
            System.out.println(e.getCause().getMessage());
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message getStudentById(Message request){
        Student student = Factory.messageToStudent(request.getBody());
        try {
            Student result =  studentController.getStudentById(student.getId()).get();
            return new Message("ok", Factory.studentToString(result));
        }
        catch (InterruptedException | ExecutionException | MyException e) {
            System.out.println(e.getCause().getMessage());
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message getAllStudents(Message request){
        try {
            Set<Student> result =  studentController.getAllStudents().get();
            String messageBody = result.stream()
                    .map(Factory::studentToString)
                    .reduce("", (s,e) -> s + e + "\n");
            return new Message("ok", messageBody);
        }
        catch (InterruptedException | ExecutionException e) {
            System.out.println(e.getCause().getMessage());
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message filterStudentsByName(Message request){
        String toFilterBy = request.getBody();
        try {
            Set<Student> result =  studentController.filterStudentsByName(toFilterBy).get();
            String messageBody = result.stream()
                    .map(Factory::studentToString)
                    .reduce("", (s,e) -> s + e + "\n");
            return new Message("ok", messageBody);
        }
        catch (InterruptedException | ExecutionException e) {
            System.out.println(e.getCause().getMessage());
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message sortStudentsAscendingByName(Message request){
        try {
            List<Student> result =  studentController.sortStudentsAscendingByName().get();
            String messageBody = result.stream()
                    .map(Factory::studentToString)
                    .reduce("", (s,e) -> s + e + "\n");
            return new Message("ok", messageBody);
        }
        catch (InterruptedException | ExecutionException e) {
            System.out.println(e.getCause().getMessage());
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message getStudentsWhoPassed(Message request){
        try {
            Set<Student> result =  studentController.getStudentsWhoPassed().get();
            String messageBody = result.stream()
                    .map(Factory::studentToString)
                    .reduce("", (s,e) -> s + e + "\n");
            return new Message("ok", messageBody);
        }
        catch (InterruptedException | ExecutionException e) {
            System.out.println(e.getCause().getMessage());
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message getStudentsWithMostProblems(Message request){
        try {
            Student result =  studentController.getStudentsWithMostProblems().get();
            return new Message("ok", Factory.studentToString(result));
        }
        catch (InterruptedException | ExecutionException e) {
            System.out.println(e.getCause().getMessage());
            return new Message("error", e.getCause().getMessage());
        }
    }



    public Message addProblem(Message request){
        LabProblem parameter = Factory.messageToProblem(request.getBody());
        try {
            if(labProblemController.addProblem(parameter).get())
                return new Message("ok", "ok");
            else
                return new Message("error", "Problem already exists");
        }
        catch (InterruptedException | ExecutionException | MyException e) {
            System.out.println(e.getCause().getMessage());
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message deleteProblem(Message request){
        LabProblem parameter = Factory.messageToProblem(request.getBody());
        try {
            if(labProblemController.deleteProblem(parameter).get())
                return new Message("ok", "ok");
            else
                return new Message("error", "Problem does not exist");
        }
        catch (InterruptedException | ExecutionException | MyException e) {
            System.out.println(e.getCause().getMessage());
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message updateProblem(Message request){
        LabProblem parameter = Factory.messageToProblem(request.getBody());
        try {
            if(labProblemController.updateProblem(parameter).get())
                return new Message("ok", "ok");
            else
                return new Message("error", "Problem does not exist");
        }
        catch (InterruptedException | ExecutionException | MyException e) {
            System.out.println(e.getCause().getMessage());
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message getProblemById(Message request){
        LabProblem parameter = Factory.messageToProblem(request.getBody());
        try {
            LabProblem result =  labProblemController.getProblemById(parameter.getId()).get();
            return new Message("ok", Factory.problemToString(result));
        }
        catch (InterruptedException | ExecutionException e) {
            System.out.println(e.getCause().getMessage());
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message getAllProblems(Message request){
        try {
            Set<LabProblem> result =  labProblemController.getAllProblems().get();
            String messageBody = result.stream()
                    .map(Factory::problemToString)
                    .reduce("", (s,e) -> s + e + "\n");
            return new Message("ok", messageBody);
        }
        catch (InterruptedException | ExecutionException e) {
            System.out.println(e.getCause().getMessage());
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message filterProblemsByScore(Message request){
        int toFilterBy = Integer.parseInt(request.getBody());
        try {
            Set<LabProblem> result =  labProblemController.filterProblemsByScore(toFilterBy).get();
            String messageBody = result.stream()
                    .map(Factory::problemToString)
                    .reduce("", (s,e) -> s + e + "\n");
            return new Message("ok", messageBody);
        }
        catch (InterruptedException | ExecutionException e) {
            System.out.println(e.getCause().getMessage());
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message sortProblemsDescendingByScore(Message request){
        try {
            List<LabProblem> result =  labProblemController.sortProblemsDescendingByScore().get();
            String messageBody = result.stream()
                    .map(Factory::problemToString)
                    .reduce("", (s,e) -> s + e + "\n");
            return new Message("ok", messageBody);
        }
        catch (InterruptedException | ExecutionException e) {
            System.out.println(e.getCause().getMessage());
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message getProblemAssignedMostTimes(Message request){
        try {
            LabProblem result =  labProblemController.getProblemAssignedMostTimes().get();
            return new Message("ok", Factory.problemToString(result));
        }
        catch (InterruptedException | ExecutionException e) {
            System.out.println(e.getCause().getMessage());
            return new Message("error", e.getCause().getMessage());
        }
    }



    public Message addAssignment(Message request){
        Assignment parameter = Factory.messageToAssignment(request.getBody());
        try {
            if(assignmentController.addAssignment(parameter).get())
                return new Message("ok", "ok");
            else
                return new Message("error", "Assignment already exists");
        }
        catch (InterruptedException | ExecutionException | MyException e) {
            System.out.println(e.getCause().getMessage());
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message deleteAssignment(Message request){
        Assignment parameter = Factory.messageToAssignment(request.getBody());
        try {
            if(assignmentController.deleteAssignment(parameter).get())
                return new Message("ok", "ok");
            else
                return new Message("error", "Assignment does not exist");
        }
        catch (InterruptedException | ExecutionException | MyException e) {
            System.out.println(e.getCause().getMessage());
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message updateAssignment(Message request){
        Assignment parameter = Factory.messageToAssignment(request.getBody());
        try {
            if(assignmentController.updateAssignment(parameter).get())
                return new Message("ok", "ok");
            else
                return new Message("error", "Assignment does not exist");
        }
        catch (InterruptedException | ExecutionException | MyException e) {
            System.out.println(e.getCause().getMessage());
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message getAllAssignments(Message request){
        try {
            Set<Assignment> result =  assignmentController.getAllAssignments().get();
            String messageBody = result.stream()
                    .map(Factory::assignmentToString)
                    .reduce("", (s,e) -> s + e + "\n");
            return new Message("ok", messageBody);
        }
        catch (InterruptedException | ExecutionException e) {
            System.out.println(e.getCause().getMessage());
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message filterAssignmentsByGrade(Message request){
        Double toFilterBy = Double.parseDouble(request.getBody());
        try {
            Set<Assignment> result =  assignmentController.filterAssignmentsByGrade(toFilterBy).get();
            String messageBody = result.stream()
                    .map(Factory::assignmentToString)
                    .reduce("", (s,e) -> s + e + "\n");
            return new Message("ok", messageBody);
        }
        catch (InterruptedException | ExecutionException e) {
            System.out.println(e.getCause().getMessage());
            return new Message("error", e.getCause().getMessage());
        }
    }

    public Message sortAssignmentsAscendingById(Message request){
        try {
            List<Assignment> result =  assignmentController.sortAssignmentsAscendingById().get();
            String messageBody = result.stream()
                    .map(Factory::assignmentToString)
                    .reduce("", (s,e) -> s + e + "\n");
            return new Message("ok", messageBody);
        }
        catch (InterruptedException | ExecutionException e) {
            System.out.println(e.getCause().getMessage());
            return new Message("error", e.getCause().getMessage());
        }
    }

}
