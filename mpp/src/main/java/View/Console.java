package View;

import Controller.LabProblemController;
import Model.LabProblem;
import Model.Student;
import Model.Exceptions.ValidatorException;
import Controller.StudentController;

import java.io.BufferedReader;
import java.io.IOException;
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

    public void runConsole() {
        addStudents();
        printAllStudents();
        filterStudents();
        addProblems();
        printAllProblems();
        filterProblems();
    }

    private void filterStudents() {
        System.out.println("filtered students (name containing 's2'):");
        Set<Student> students = studentController.filterStudentsByName("s2");
        students.stream().forEach(System.out::println);
    }

    private void printAllStudents() {
        Set<Student> students = studentController.getAllStudents();
        students.stream().forEach(System.out::println);
    }

    private void addStudents() {
        while (true) {
            Student student = readStudent();
            if (student == null || student.getId() <= 0) {
                break;
            }
            try {
                studentController.addStudent(student);
            } catch (ValidatorException e) {
                e.printStackTrace();
            }
        }
    }

    private Student readStudent() {
        System.out.println("Read student {id, serialNumber, name, group}");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            String line = br.readLine();
            Scanner scanner = new Scanner(line);
            Long id = scanner.nextLong();// ...
            String serialNumber = scanner.next();
            String name = scanner.next();
            int group = scanner.nextInt();// ...
            return new Student(id, serialNumber, name, group);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private void filterProblems() {
        System.out.println("filtered problems (score >= 5):");
        Set<LabProblem> filteredProblems = this.labProblemController.filterProblemsByScore(5);
        filteredProblems.stream().forEach(System.out::println);
    }

    private void printAllProblems() {
        Set<LabProblem> allProblems = labProblemController.getAllProblems();
        allProblems.stream().forEach(System.out::println);
    }

    private void addProblems() {
        while (true) {
            LabProblem newProblem = readProblem();
            if (newProblem == null || newProblem.getId() < 0) {
                break;
            }
            try {
                labProblemController.addLabProblem(newProblem);
            } catch (ValidatorException e) {
                e.printStackTrace();
            }
        }
    }

    private LabProblem readProblem() {
        System.out.println("Read problem {id, number, description, score}");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            String line = br.readLine();
            Scanner scanner = new Scanner(line);
            Long id = scanner.nextLong();
            int number = scanner.nextInt();
            String description = scanner.next();
            int score = scanner.nextInt();
            return new LabProblem(id, number, description, score);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
