package View;

import Controller.LabProblemController;
import Model.LabProblem;
import Model.Student;
import Model.Exceptions.ValidatorException;
import Controller.StudentController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Set;

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
        students.stream().forEach(System.out::println);
    }

    /**
     * Method to handle printing the students.
     */
    private void printAllStudents() {
        Set<Student> students = studentController.getAllStudents();
        students.stream().forEach(System.out::println);
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
            Scanner scanner = new Scanner(line);
            Long id = scanner.nextLong();// ...
            String serialNumber = scanner.next();
            String name = scanner.next();
            int group = scanner.nextInt();// ...
            return new Student(id, serialNumber, name, group);
        } catch (Exception e) {
            System.out.println("Wrong input. Use {id, serialNumber, name, group}");
            return readStudent();
        }
    }

    /**
     * Method to handle filtering the lab problems.
     */
    private void filterProblems() {
        System.out.println("filtered problems (score >= 5):");
        Set<LabProblem> filteredProblems = this.labProblemController.filterProblemsByScore(5);
        filteredProblems.stream().forEach(System.out::println);
    }

    /**
     * Method to handle printing the lab problems.
     */
    private void printAllProblems() {
        Set<LabProblem> allProblems = labProblemController.getAllProblems();
        allProblems.stream().forEach(System.out::println);
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
            Scanner scanner = new Scanner(line);
            Long id = scanner.nextLong();
            String description = scanner.next();
            int score = scanner.nextInt();
            return new LabProblem(id, description, score);
        }
        catch (Exception e) {
            System.out.println("Wrong input. Use {id, serialNumber, name, group}");
            return readProblem();
        }
    }

}
