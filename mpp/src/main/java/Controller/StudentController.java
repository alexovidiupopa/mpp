package Controller;

import Model.Student;
import Model.Exceptions.ValidatorException;
import Repository.RepositoryInterface;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class StudentService {

    private RepositoryInterface<Long, Student> repository;

    public StudentService(RepositoryInterface<Long, Student> repository) {
        this.repository = repository;
    }

    public void addStudent(Student student) throws ValidatorException {
        repository.add(student);
    }

    public Set<Student> getAllStudents() {
        Iterable<Student> students = repository.getAll();
        return StreamSupport.stream(students.spliterator(), false).collect(Collectors.toSet());
    }

    public Set<Student> filterStudentsByName(String s) {
        Iterable<Student> students = repository.getAll();
        //version 1
//        Set<Student> filteredStudents = StreamSupport.stream(students.spliterator(), false)
//                .filter(student -> student.getName().contains(s)).collect(Collectors.toSet());

        //version 2
        Set<Student> filteredStudents= new HashSet<>();
        students.forEach(filteredStudents::add);
        filteredStudents.removeIf(student -> !student.getName().contains(s));

        return filteredStudents;
    }

}
