package ro.ubb.client.ui;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ro.ubb.core.model.Exceptions.MyException;
import ro.ubb.core.utils.Pair;
import ro.ubb.web.dto.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class Console {
    private static final String studentsUrl = "http://localhost:8080/api/students";
    private static final String problemsUrl = "http://localhost:8080/api/problems";
    private static final String assignmentsUrl = "http://localhost:8080/api/assignments";

    private RestTemplate restTemplate;


    public Console(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

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
                System.out.println(e);
            }
        }
    }



    /**
     * Method to handle adding students.
     */
    private void addStudents() {
        while (true) {
            StudentDto student = readStudent();
            if (student == null) {
                break;
            }
            CompletableFuture.supplyAsync(
                    ()->{
                        try{
                            restTemplate.postForObject(studentsUrl, student, StudentDto.class);
                            return "Student added successfully";
                        }catch (RestClientException e){
                            return "student already exists";
                        }
                    }
            )
                    .thenAcceptAsync(System.out::println);

        }
    }

    /**
     * Method to handle deleting students.
     */
    private void deleteStudents(){
        while (true) {
            StudentDto student = readStudent();
            if (student == null) {
                break;
            }
            CompletableFuture.supplyAsync(
                    ()->{
                        try{
                            restTemplate.delete(studentsUrl+"/{id}", student.getId());
                            return "Student deleted successfully";

                        } catch(RestClientException e){
                            return "student doesn't exist";
                        }
                    }
            )
                    .thenAcceptAsync(System.out::println);

        }
    }

    /**
     * Method to handle updating students.
     */
    private void updateStudent(){
        while (true) {
            StudentDto student = readStudent();
            if (student == null) {
                break;
            }
            CompletableFuture.supplyAsync(
                    ()->{
                        try{
                            restTemplate.put(studentsUrl, student);
                            return "Student updated successfully";
                        }catch(RestClientException e){
                            return "student doesn't exist";
                        }
                    }
            )
                    .thenAcceptAsync(System.out::println);

        }
    }

    /**
     * Helper method to handle reading a student from keyboard.
     * @return read student
     */
    private StudentDto readStudent() {
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
            return new StudentDto(id, serialNumber, name, group);
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
        CompletableFuture.supplyAsync(
                ()->restTemplate.getForObject(studentsUrl, StudentsDto.class)
        ).thenAcceptAsync(result -> result.getStudents().forEach(System.out::println));
    }
    private void filterStudents() {
        System.out.println("filtered students (name containing 's2'):");
        CompletableFuture.supplyAsync(
                ()->restTemplate.getForObject(studentsUrl+"/filter/s2", StudentsDto.class)
        ).thenAcceptAsync(result -> result.getStudents().forEach(System.out::println));
    }


    private void sortStudents() {
        System.out.println("sorted students (by name):");
        CompletableFuture.supplyAsync(
                ()->restTemplate.getForObject(studentsUrl+"/sort", StudentsDto.class)
        ).thenAcceptAsync(result -> result.getStudents().forEach(System.out::println));
    }

    private void addProblems() {
        while (true) {
            LabProblemDto newProblem = readProblem();
            if (newProblem == null || newProblem.getId() < 0) {
                break;
            }
            CompletableFuture.supplyAsync(
                    ()->{
                        try{
                            restTemplate.postForObject(problemsUrl, newProblem, LabProblemDto.class);
                            return "Problem added successfully";
                        }catch(RestClientException e){
                            return "Problem already exists";
                        }
                    }
            )
                    .thenAcceptAsync(System.out::println);
        }
    }


    private void deleteProblems(){
        while (true) {
            LabProblemDto problem = readProblem();
            if (problem == null) {
                break;
            }
            CompletableFuture.supplyAsync(
                    ()->{
                        try{
                            restTemplate.delete(problemsUrl+"/{id}", problem.getId());
                            return "Problem deleted successfully";
                        }catch (RestClientException e){
                            return "Problem doesn't exist";
                        }
                    }

            )
                    .thenAcceptAsync(System.out::println);
        }
    }


    private void updateProblems(){
        while (true) {
            LabProblemDto problem = readProblem();
            if (problem == null) {
                break;
            }
            CompletableFuture.supplyAsync(
                    ()->{
                        try{
                            restTemplate.put(problemsUrl, problem);
                            return "Problem updated successfully";
                        }catch (RestClientException e){
                            return "Problem doesn't exist";
                        }
                    }
            )
                    .thenAcceptAsync(System.out::println);
        }
    }


    private LabProblemDto readProblem() {
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
            return new LabProblemDto(id, description, score);
        }
        catch (MyException | IOException e) {
            System.out.println(e.getMessage());
            return readProblem();
        }
    }


    private void printAllProblems() {
        CompletableFuture.supplyAsync(
                ()->restTemplate.getForObject(problemsUrl, LabProblemsDto.class)
        )
                .thenAcceptAsync(result -> result.getProblems().forEach(System.out::println));
    }

    private void filterProblems() {
        System.out.println("filtered problems (score >= 5):");
        CompletableFuture.supplyAsync(
                ()->restTemplate.getForObject(problemsUrl+"/filter/5", LabProblemsDto.class)
        )
                .thenAcceptAsync(result -> result.getProblems().forEach(System.out::println));
    }


    private void sortProblems() {
        System.out.println("sorted problems (by score):");
        CompletableFuture.supplyAsync(
                ()->restTemplate.getForObject(problemsUrl+"/sort", LabProblemsDto.class)
        )
                .thenAcceptAsync(result -> result.getProblems().forEach(System.out::println));
    }

    private void addAssignments() {
        while (true) {
            AssignmentDto assignment = readAssignment();
            if (assignment == null) {
                break;
            }
            CompletableFuture.supplyAsync(
                    ()->{
                        try{
                            restTemplate.postForObject(assignmentsUrl, assignment, AssignmentDto.class);
                            return "Assignment added successfully";
                        }catch (RestClientException e){
                            return "Assignment already exists";
                        }
                    }
            )
                    .thenAcceptAsync(System.out::println);
        }
    }


    private void deleteAssignment() {
        while (true) {
            AssignmentDto assignment = readAssignment();
            if (assignment == null) {
                break;
            }
            CompletableFuture.supplyAsync(
                    ()->{
                        try{
                            restTemplate.delete(assignmentsUrl+"/{sid}/{aid}", assignment.getId().getFirst(),assignment.getId().getSecond());
                            return "Assignment deleted successfully";
                        }catch (RestClientException e){
                            return "Assignment doesn't exist";
                        }
                    }
            )
                    .thenAcceptAsync(System.out::println);

        }
    }


    private AssignmentDto readAssignment() {
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
            return new AssignmentDto(new Pair<>(studentId, problemId));
        }
        catch (MyException | IOException e) {
            System.out.println(e.getMessage());
            return readAssignment();
        }
    }


    private void gradeAssignments(){
        while (true) {
            AssignmentDto assignment = gradeAssignment();
            if (assignment == null) {
                break;
            }
            CompletableFuture.supplyAsync(
                    ()->{
                        try{
                            restTemplate.put(assignmentsUrl, assignment);
                            return "Assignment updated successfully";
                        }catch (RestClientException e){
                            return "Assignment doesn't exist";
                        }
                    }
            )
                    .thenAcceptAsync(System.out::println);
        }
    }


    private AssignmentDto gradeAssignment() {
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
            return new AssignmentDto(new Pair<>(studentId, problemId), grade);
        }
        catch (MyException | IOException e) {
            System.out.println(e.getMessage());
            return gradeAssignment();
        }
    }


    private void printAllAssignments() {
        CompletableFuture.supplyAsync(
                ()->restTemplate.getForObject(assignmentsUrl, AssignmentsDto.class)
        )
                .thenAcceptAsync(result -> result.getAssignments().forEach(System.out::println));
    }

    private void filterAssignments() {
        System.out.println("filtered assignments (grade >= 5):");
        CompletableFuture.supplyAsync(
                ()->restTemplate.getForObject(assignmentsUrl+"/filter/5", AssignmentsDto.class)
        )
                .thenAcceptAsync(result -> result.getAssignments().forEach(System.out::println));
    }


    private void sortAssignments() {
        System.out.println("sorted assignments (by student and problem):");
        CompletableFuture.supplyAsync(
                ()->restTemplate.getForObject(assignmentsUrl+"/sort", AssignmentsDto.class)
        )
                .thenAcceptAsync(result -> result.getAssignments().forEach(System.out::println));
    }

    private void passingStudents() {
        System.out.println("students currently passing at least one assignment:");
        CompletableFuture.supplyAsync(
                ()->restTemplate.getForObject(studentsUrl+"/passed", StudentsDto.class)
        ).thenAcceptAsync(result -> result.getStudents().forEach(System.out::println));
    }

    private void studentMostAssignedProblems(){
        System.out.println("student with the most assigned problems:");
        CompletableFuture.supplyAsync(
                ()->restTemplate.getForObject(studentsUrl+"/mostproblems", StudentDto.class)
        ).thenAcceptAsync(System.out::println);
    }

    private void mostAssignedProblem() {
        System.out.println("problem assigned the most times:");
        CompletableFuture.supplyAsync(
                ()->restTemplate.getForObject(problemsUrl+"/mostassigned", LabProblemDto.class)
        ).thenAcceptAsync(System.out::println);
    }

}
