package utils;

import model.Assignment;
import model.LabProblem;
import model.Student;

public class Factory {
    public static String studentToString(Student student){
        return student.getId() + "," + student.getSerialNumber() + "," + student.getName() + "," + student.getGroup();
    }

    public static String problemToString(LabProblem problem){
        return problem.getId() + "," + problem.getDescription() + "," + problem.getScore();
    }

    public static String assignmentToString(Assignment assignment){
        if (assignment.getGrade()==null)
            return assignment.getId().getFirst() + "," + assignment.getId().getSecond() + ",null";
        return assignment.getId().getFirst() + "," + assignment.getId().getSecond() + "," + assignment.getGrade();
    }

    public static Student messageToStudent(String message){
        String[] tokens = message.split(",");
        return new Student(Long.parseLong(tokens[0]),tokens[1], tokens[2], Integer.parseInt(tokens[3]));
    }

    public static LabProblem messageToProblem(String message){
        String[] tokens = message.split(",");
        return new LabProblem(Long.parseLong(tokens[0]), tokens[1], Integer.parseInt(tokens[2]));
    }

    public static Assignment messageToAssignment(String message){
        String[] tokens = message.split(",");
        if (tokens[2].equals("null"))
            return new Assignment(new Pair<>(Long.parseLong(tokens[0]), Long.parseLong(tokens[1])));
        return new Assignment(new Pair<>(Long.parseLong(tokens[0]), Long.parseLong(tokens[1])), Double.parseDouble(tokens[2]));
    }
}