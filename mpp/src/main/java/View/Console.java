package View;

import Controller.AssignmentController;
import Controller.LabProblemController;
import Model.Assignment;
import Model.Exceptions.MyException;
import Model.Exceptions.RepositoryException;
import Model.LabProblem;
import Model.Student;
import Model.Exceptions.ValidatorException;
import Controller.StudentController;
import Utils.Pair;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Console {

    private StudentController studentController;
    private LabProblemController labProblemController;
    private AssignmentController assignmentController;
    private Map<String, Runnable> commands;

    public Console(StudentController studentService, LabProblemController problemController, AssignmentController assignmentController) {
        this.studentController = studentService;
        this.labProblemController = problemController;
        this.assignmentController = assignmentController;
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
    }

    /**
     * Method to print console menu.
     */
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
                studentController.addStudent(student);
                System.out.println("Student added successfully");
            } catch (ValidatorException | RepositoryException | IOException | ParserConfigurationException | TransformerException | SAXException e) {
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
                studentController.deleteStudent(student);
                System.out.println("Student deleted successfully");
            } catch (RepositoryException | IOException | TransformerException | ParserConfigurationException e) {
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
                studentController.updateStudent(student);
                System.out.println("Student updated successfully");
            } catch (ValidatorException | RepositoryException | IOException | TransformerException | ParserConfigurationException e) {
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
        Set<Student> students = studentController.getAllStudents();
        students.forEach(System.out::println);
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
     * Method to handle sorting the students.
     */
    private void sortStudents() {
        System.out.println("sorted students (by name):");
        List<Student> students = studentController.sortStudentsAscendingByName();
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
                labProblemController.addProblem(newProblem);
                System.out.println("Problem added successfully");
            } catch (ValidatorException | RepositoryException | IOException | ParserConfigurationException | TransformerException | SAXException e) {
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
                labProblemController.deleteProblem(problem);
                System.out.println("Problem deleted successfully");
            } catch (RepositoryException | IOException | TransformerException | ParserConfigurationException e) {
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
                labProblemController.updateProblem(problem);
                System.out.println("Problem updated successfully");
            } catch (ValidatorException | RepositoryException | IOException | TransformerException | ParserConfigurationException e) {
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
        Set<LabProblem> allProblems = labProblemController.getAllProblems();
        allProblems.forEach(System.out::println);
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
     * Method to handle sorting the lab problems.
     */
    private void sortProblems() {
        System.out.println("sorted problems (by score):");
        List<LabProblem> students = labProblemController.sortProblemsDescendingByScore();
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
                this.assignmentController.addAssignment(assignment);
                System.out.println("Assignment added successfully");
            } catch (ValidatorException | IOException | RepositoryException | ParserConfigurationException | TransformerException | SAXException e) {
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
                assignmentController.deleteAssignment(assignment);
                System.out.println("Assignment deleted successfully");
            } catch (IOException | RepositoryException | TransformerException | ParserConfigurationException e) {
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
                assignmentController.updateAssignment(assignment);
                System.out.println("Assignment updated successfully");
            } catch (ValidatorException | RepositoryException | IOException | TransformerException | ParserConfigurationException e) {
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
        Set<Assignment> allAssignments = this.assignmentController.getAllAssignments();
        allAssignments.forEach(System.out::println);
    }

    /**
     * Method to handle filtering the assignments.
     */
    private void filterAssignments() {
        System.out.println("filtered assignments (grade >= 5):");
        Set<Assignment> filteredAssignments = this.assignmentController.filterAssignmentsByGrade(5);
        filteredAssignments.forEach(System.out::println);
    }

    /**
     * Method to handle sorting the assignments.
     */
    private void sortAssignments() {
        System.out.println("sorted assignments (by student and problem):");
        List<Assignment> students = assignmentController.sortAssignmentsAscendingById();
        students.forEach(System.out::println);
    }


}