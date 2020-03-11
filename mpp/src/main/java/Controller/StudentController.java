package Controller;

import Model.Exceptions.RepositoryException;
import Model.LabProblem;
import Model.Student;
import Model.Exceptions.ValidatorException;
import Repository.RepositoryInterface;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class StudentController {

    private RepositoryInterface<Long, Student> repository;

    public StudentController(RepositoryInterface<Long, Student> repository) {
        this.repository = repository;
    }

    /**
     * Adds the given student to the repository.
     * @param student - given student
     * @throws ValidatorException if student is not valid
     */
    public void addStudent(Student student) throws ValidatorException, RepositoryException, IOException {
        Optional<Student> optional = repository.add(student);
        if (optional.isPresent())
            throw new RepositoryException("Id already exists");
    }

    /**
     * Removes the given student from the repository.
     * @param student - given student
     */
    public void deleteStudent(Student student) throws RepositoryException, IOException {
        Optional<Student> optional = repository.delete(student.getId());
        if (!optional.isPresent())
            throw new RepositoryException("Student doesn't exist");
    }

    /**
     * Updates the given student in the repository.
     * @param student - given student
     * @throws ValidatorException if the student is not valid
     */
    public void updateStudent(Student student) throws ValidatorException, RepositoryException, IOException {
        Optional<Student> optional = repository.update(student);
        if (optional.isPresent())
            throw new RepositoryException("Student doesn't exist");
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

}
