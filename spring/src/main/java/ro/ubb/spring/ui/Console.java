package ro.ubb.spring.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.ubb.spring.model.Assignment;
import ro.ubb.spring.model.Exceptions.MyException;
import ro.ubb.spring.model.LabProblem;
import ro.ubb.spring.model.Student;
import ro.ubb.spring.service.IAssignmentService;
import ro.ubb.spring.service.ILabProblemService;
import ro.ubb.spring.service.IStudentService;
import ro.ubb.spring.utils.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Console {
    @Autowired
    private IStudentService studentService;

    @Autowired
    private ILabProblemService labProblemService;

    @Autowired
    private IAssignmentService assignmentService;

    private Map<String, Runnable> commands;

    private void initCommands(){
        commands = new HashMap<>();
        commands.put("exit", () -> System.exit(0));
        commands.put("add students", this::addStudents);
        commands.put("add problems", this::addProblems);
        commands.put("add assignments", this::addAssignments);
        commands.put("delete students", this::deleteStudents);
        commands.put("delete problems", this::deleteProblems);
        commands.put("delete assignments", this::deleteAssignment);
        commands.put("update students", this::updateStudent);
        commands.put("update problems", this::updateProblems);
        commands.put("grade assignments", this::gradeAssignments);
        commands.put("list students", this::printAllStudents);
        commands.put("list problems", this::printAllProblems);
        commands.put("list assignments", this::printAllAssignments);
        commands.put("filter students", this::filterStudents);
        commands.put("filter problems", this::filterProblems);
        commands.put("filter assignments", this::filterAssignments);
        commands.put("sort students", this::sortStudents);
        commands.put("sort problems", this::sortProblems);
        commands.put("sort assignments", this::sortAssignments);
        commands.put("most assigned problem", this::mostAssignedProblem);
        commands.put("students who passed", this::passingStudents);
        commands.put("student most problems", this::studentMostAssignedProblems);
    }

    public void printMenu(){
        System.out.println("Menu:");
        System.out.println(
                commands.keySet()
                        .stream()
                        .reduce("", (s, k) -> s += k + "\n")
        );
    }

    /**
     * Method to run the console.
     */
    public void run(){
        initCommands();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        this.printMenu();
        while(true){
            try {
                System.out.println(">>>");
                String command = br.readLine();
                if(!commands.containsKey(command))
                    throw new MyException("Invalid command");
                commands.get(command).run();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }



    /**
     * Method to handle adding students.
     */
    private void addStudents() {
        while (true) {
            Student student = readStudent();
            if (student == null) {
                break;
            }
            try {
                studentService.addStudent(student);
                System.out.println("Student added successfully");
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Method to handle deleting students.
     */
    private void deleteStudents(){
        while (true) {
            Student student = readStudent();
            if (student == null) {
                break;
            }
            try {
                studentService.deleteStudent(student);
                System.out.println("Student deleted successfully");
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    /**
     * Method to handle updating students.
     */
    private void updateStudent(){
        while (true) {
            Student student = readStudent();
            if (student == null) {
                break;
            }
            try {
                studentService.updateStudent(student);
                System.out.println("Student updated successfully");
            } catch (MyException e) {
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
                throw new MyException("Wrong number of arguments for reading student: (" + arguments.size() + " instead of 4");
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
                group = Integer.parseInt(arguments.get(3));
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
     * Method to handle printing the students.
     */
    private void printAllStudents() {
        List<Student> students = studentService.getAllStudents();
        students.forEach(System.out::println);
    }

    /**
     * Method to handle filtering the students.
     */
    private void filterStudents() {
        System.out.println("filtered students (name containing 's2'):");
        List<Student> students = studentService.filterStudentsByName("s2");
        students.forEach(System.out::println);
    }

    /**
     * Method to handle sorting the students.
     */
    private void sortStudents() {
        System.out.println("sorted students (by name):");
        List<Student> students = studentService.sortStudentsAscendingByName();
        students.forEach(System.out::println);
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
                labProblemService.addProblem(newProblem);
                System.out.println("Problem added successfully");
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Method to handle deleting lab problems.
     */
    private void deleteProblems(){
        while (true) {
            LabProblem problem = readProblem();
            if (problem == null) {
                break;
            }
            try {
                labProblemService.deleteProblem(problem);
                System.out.println("Problem deleted successfully");
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    /**
     * Method to handle updating lab problems.
     */
    private void updateProblems(){
        while (true) {
            LabProblem problem = readProblem();
            if (problem == null) {
                break;
            }
            try {
                labProblemService.updateProblem(problem);
                System.out.println("Problem updated successfully");
            } catch (MyException e) {
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
                throw new MyException("Wrong number of arguments for reading lab problem: (" + arguments.size() + " instead of 3");
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

    /**
     * Method to handle printing the lab problems.
     */
    private void printAllProblems() {
        List<LabProblem> allProblems = labProblemService.getAllProblems();
        allProblems.forEach(System.out::println);
    }

    /**
     * Method to handle filtering the lab problems.
     */
    private void filterProblems() {
        System.out.println("filtered problems (score >= 5):");
        List<LabProblem> filteredProblems = this.labProblemService.filterProblemsByScore(5);
        filteredProblems.forEach(System.out::println);
    }

    /**
     * Method to handle sorting the lab problems.
     */
    private void sortProblems() {
        System.out.println("sorted problems (by score):");
        List<LabProblem> students = labProblemService.sortProblemsDescendingByScore();
        students.forEach(System.out::println);
    }



    /**
     * Method to handle adding assignment.
     */
    private void addAssignments() {
        while (true) {
            Assignment assignment = readAssignment();
            if (assignment == null) {
                break;
            }
            try {
                this.assignmentService.addAssignment(assignment);
                System.out.println("Assignment added successfully");
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Method to handle deleting assignment.
     */
    private void deleteAssignment() {
        while (true) {
            Assignment assignment = readAssignment();
            if (assignment == null) {
                break;
            }
            try {
                assignmentService.deleteAssignment(assignment);
                System.out.println("Assignment deleted successfully");
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Helper method to handle reading an assignment from keyboard.
     * @return read assignment
     */
    private Assignment readAssignment() {
        System.out.println("Read assignment {studentId, ProblemId}");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            String line = br.readLine();
            if(line.equals("done")){
                return null;
            }
            List<String> arguments = Arrays.stream(line.split(" "))
                    .filter(word -> !word.equals(""))
                    .collect(Collectors.toList());
            if(arguments.size() != 2)
                throw new MyException("Wrong number of arguments for reading assignment: (" + arguments.size() + " instead of 2");
            long studentId;
            try {
                studentId = Long.parseLong(arguments.get(0));
            }
            catch (NumberFormatException nfe) {
                throw new MyException("Argument for studentId is not a number");
            }
            long problemId;
            try {
                problemId = Long.parseLong(arguments.get(1));
            }
            catch (NumberFormatException nfe) {
                throw new MyException("Argument for problemId is not a number");
            }
            return new Assignment(new Pair<>(studentId, problemId));
        }
        catch (MyException | IOException e) {
            System.out.println(e.getMessage());
            return readAssignment();
        }
    }

    /**
     * Method to handle grading assignment.
     */
    private void gradeAssignments(){
        while (true) {
            Assignment assignment = gradeAssignment();
            if (assignment == null) {
                break;
            }
            try {
                assignmentService.updateAssignment(assignment);
                System.out.println("Assignment updated successfully");
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Method to handle giving a grade to one assignment.
     */
    private Assignment gradeAssignment() {
        System.out.println("Read grade {studentId, problemId, grade}");
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
                throw new MyException("Wrong number of arguments for reading grade: (" + arguments.size() + " instead of 3");
            long studentId;
            try {
                studentId = Long.parseLong(arguments.get(0));
            }
            catch (NumberFormatException nfe) {
                throw new MyException("Argument for studentId is not a number");
            }
            long problemId;
            try {
                problemId = Long.parseLong(arguments.get(1));
            }
            catch (NumberFormatException nfe) {
                throw new MyException("Argument for problemId is not a number");
            }
            double grade;
            try {
                grade = Double.parseDouble(arguments.get(2));
            }
            catch (NumberFormatException nfe) {
                throw new MyException("Argument for grade is not a number");
            }
            return new Assignment(new Pair<>(studentId, problemId), grade);
        }
        catch (MyException | IOException e) {
            System.out.println(e.getMessage());
            return gradeAssignment();
        }
    }

    /**
     * Method to handle printing the assignment.
     */
    private void printAllAssignments() {
        List<Assignment> allAssignments = this.assignmentService.getAllAssignments();
        allAssignments.forEach(System.out::println);
    }

    /**
     * Method to handle filtering the assignments.
     */
    private void filterAssignments() {
        System.out.println("filtered assignments (grade >= 5):");
        List<Assignment> filteredAssignments = this.assignmentService.filterAssignmentsByGrade(5);
        filteredAssignments.forEach(System.out::println);
    }

    /**
     * Method to handle sorting the assignments.
     */
    private void sortAssignments() {
        System.out.println("sorted assignments (by student and problem):");
        List<Assignment> students = assignmentService.sortAssignmentsAscendingById();
        students.forEach(System.out::println);
    }



    /**
     * Method to handle printing the passing students.
     */
    private void passingStudents() {
        System.out.println("students currently passing at least one assignment:");
        Set<Student> students=studentService.getStudentsWhoPassed();
        students.forEach(System.out::println);
    }

    /**
     * Method to handle getting the problem assigned the most times.
     */
    private void mostAssignedProblem() {
        System.out.println("problem assigned the most times:");
        try {
            System.out.println(labProblemService.getProblemAssignedMostTimes());
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method to handle getting the student with the most assigned problems.
     */
    private void studentMostAssignedProblems(){
        System.out.println("student with the most assigned problems:");
        try {
            System.out.println(studentService.getStudentsWithMostProblems());
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
    }

}

