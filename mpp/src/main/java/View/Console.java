package View;

import Controller.LabProblemController;
import Model.Exceptions.MyException;
import Model.LabProblem;
import Model.Student;
import Model.Exceptions.ValidatorException;
import Controller.StudentController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Console {

    private StudentController studentController;
    private LabProblemController labProblemController;

    public Console(StudentController studentService, LabProblemController problemController) {
        this.studentController = studentService;
        this.labProblemController = problemController;
    }

    /**
     * Method to run the console.
     */
    public void runConsole() {
        addStudents();
        printAllStudents();
        filterStudents();
        addProblems();
        printAllProblems();
        filterProblems();
    }

    /**
     * Method to handle filtering the students.
     */
    private void filterStudents() {
        System.out.println("filtered students (name containing 's2'):");
        Set<Student> students = studentController.filterStudentsByName("s2");
        students.forEach(System.out::println);
    }

    /**
     * Method to handle printing the students.
     */
    private void printAllStudents() {
        Set<Student> students = studentController.getAllStudents();
        students.forEach(System.out::println);
    }

    /**
     * Method to handle adding students.
     */
    private void addStudents() {
        while (true) {
            Student student = readStudent();
            if (student == null || student.getId() <= 0) {
                break;
            }
            try {
                studentController.addStudent(student);
                System.out.println("Student added successfully");
            } catch (ValidatorException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Helper method to handle reading a student from keyboard.
     * @return read student
     */
    private Student readStudent() {
        System.out.println("Read student {id, serialNumber, name, group}");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            String line = br.readLine();
            if(line.equals("done")){
                return null;
            }
            List<String> arguments = Arrays.stream(line.split(" "))
                    .filter(word -> !word.equals(""))
                    .collect(Collectors.toList());
            if(arguments.size() != 4)
                throw new MyException("Wrong number of arguments for reading student (" + arguments.size() + " instead of 4");
            long id;
            try {
                id = Long.parseLong(arguments.get(0));
            }
            catch (NumberFormatException nfe) {
                throw new MyException("Argument for id is not a number");
            }
            String serialNumber = arguments.get(1);
            String name = arguments.get(2);
            int group;
            try {
                group = Integer.parseInt(arguments.get(0));
            }
            catch (NumberFormatException nfe) {
                throw new MyException("Argument for group is not an integer");
            }
            return new Student(id, serialNumber, name, group);
        }
        catch (MyException | IOException e) {
            System.out.println(e.getMessage());
            return readStudent();
        }
    }

    /**
     * Method to handle filtering the lab problems.
     */
    private void filterProblems() {
        System.out.println("filtered problems (score >= 5):");
        Set<LabProblem> filteredProblems = this.labProblemController.filterProblemsByScore(5);
        filteredProblems.forEach(System.out::println);
    }

    /**
     * Method to handle printing the lab problems.
     */
    private void printAllProblems() {
        Set<LabProblem> allProblems = labProblemController.getAllProblems();
        allProblems.forEach(System.out::println);
    }

    /**
     * Method to handle adding lab problems.
     */
    private void addProblems() {
        while (true) {
            LabProblem newProblem = readProblem();
            if (newProblem == null || newProblem.getId() < 0) {
                break;
            }
            try {
                labProblemController.addProblem(newProblem);
                System.out.println("Problem added successfully");
            } catch (ValidatorException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Helper method to handle reading a lab problem from keyboard.
     * @return read problem
     */
    private LabProblem readProblem() {
        System.out.println("Read problem {id, description, score}");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            String line = br.readLine();
            if(line.equals("done")){
                return null;
            }
            List<String> arguments = Arrays.stream(line.split(" "))
                    .filter(word -> !word.equals(""))
                    .collect(Collectors.toList());
            if(arguments.size() != 3)
                throw new MyException("Wrong number of arguments for reading lab problem (" + arguments.size() + " instead of 3");
            long id;
            try {
                id = Long.parseLong(arguments.get(0));
            }
            catch (NumberFormatException nfe) {
                throw new MyException("Argument for id is not a number");
            }
            String description = arguments.get(1);
            int score = Integer.parseInt(arguments.get(2));
            return new LabProblem(id, description, score);
        }
        catch (MyException | IOException e) {
            System.out.println(e.getMessage());
            return readProblem();
        }
    }

}
