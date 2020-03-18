package Controller;

import Model.Assignment;
import Model.Exceptions.MyException;
import Model.Exceptions.RepositoryException;
import Model.Exceptions.ValidatorException;
import Model.Student;
import Repository.RepositoryInterface;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class StudentController {

    private RepositoryInterface<Long, Student> repository;
    private AssignmentController assignmentController;

    public StudentController(RepositoryInterface<Long, Student> repository) {
        this.repository = repository;
    }

    public void setAssignmentController(AssignmentController assignmentController) {
        this.assignmentController = assignmentController;
    }

    /**
     * Adds the given student to the repository.
     * @param student - given student
     * @throws ValidatorException if student is not valid
     */
    public void addStudent(Student student) throws ValidatorException, RepositoryException, IOException, ParserConfigurationException, TransformerException, SAXException {
        Optional<Student> optional = repository.add(student);
        if (optional.isPresent())
            throw new RepositoryException("Id already exists");
    }

    /**
     * Removes the given student from the repository.
     * @param student - given student
     */
    public void deleteStudent(Student student) throws RepositoryException, IOException, TransformerException, ParserConfigurationException {
        this.assignmentController
                .getAllAssignments()
                .stream()
                .filter(assignment -> assignment.getId().getFirst().equals(student.getId()))
                .forEach(a -> {
                    try {
                        this.assignmentController.deleteAssignment(a);
                    } catch (IOException | RepositoryException | TransformerException | ParserConfigurationException e) {
                        e.printStackTrace();
                    }
                });
        Optional<Student> optional = repository.delete(student.getId());
        if (!optional.isPresent())
            throw new RepositoryException("Student doesn't exist");
    }

    /**
     * Updates the given student in the repository.
     * @param student - given student
     * @throws ValidatorException if the student is not valid
     */
    public void updateStudent(Student student) throws ValidatorException, RepositoryException, IOException, TransformerException, ParserConfigurationException {
        Optional<Student> optional = repository.update(student);
        if (optional.isPresent())
            throw new RepositoryException("Student doesn't exist");
    }

    /**
     * Gets the student which has a given id.
     * @param id - given student id
     * @return Student in the repository with the given id.
     */
    public Student getStudentById(long id) {
        Optional<Student> optional = this.repository.findById(id);
        return optional.orElse(null);
    }

    /**
     * Gets all the students currently in the repository.
     * @return HashSet containing all students in the repository.
     */
    public Set<Student> getAllStudents() {
        Iterable<Student> students = repository.getAll();
        return StreamSupport.stream(students.spliterator(), false).collect(Collectors.toSet());
    }

    /**
     * Returns all students whose name contain the given string.
     * @param s - given string
     * @return HashSet containing the above students.
     */
    public Set<Student> filterStudentsByName(String s) {
        Iterable<Student> students = repository.getAll();
        return StreamSupport.stream(students.spliterator(), false)
                .filter(student -> student.getName().contains(s))
                .collect(Collectors.toSet());
    }

    /**
     * Returns all students sorted ascending by their name (using string comparator)
     * @return List containing said students.
     */
    public List<Student> sortStudentsAscendingByName(){
        Iterable<Student> students = repository.getAll();
        return StreamSupport.stream(students.spliterator(),false)
                .sorted(Comparator.comparing(Student::getName))
                .collect(Collectors.toList());
    }

    /**
     * Determine which are the students who passed at least one assignment.
     * @return a Set containing all the students respecting fore-mentioned property.
     */
    public Set<Student> getStudentsWhoPassed(){
        Set<Assignment> assignments = assignmentController.getAllAssignments();
        return assignments.stream()
                .filter(assignment -> assignment.getGrade()!=null && assignment.getGrade()>=5.0)
                .map(assignment -> this.getStudentById(assignment.getId().getFirst()))
                .collect(Collectors.toSet());
    }

    /**
     * Find the student which has been assigned the most problems.
     * @return a Student respecting the fore-mentioned property.
     */
    public Student getStudentsWithMostProblems() throws MyException {
        Set<Assignment> assignments = assignmentController.getAllAssignments();
        Map<Long, Long> countForId = assignments.stream()
                .collect(Collectors.groupingBy(assignment -> assignment.getId().getFirst(), Collectors.counting()));
        Map<Long, Long> sortedByValue = countForId.entrySet()
                .stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        Optional<Map.Entry<Long, Long>> student = sortedByValue.entrySet().stream().findFirst();
        if(!student.isPresent())
            throw new MyException("No student found");
        return this.getStudentById(student.get().getKey());
    }

}