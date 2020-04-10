package ro.ubb.spring.service;

import ro.ubb.spring.model.Exceptions.MyException;
import ro.ubb.spring.model.Student;

import java.util.List;
import java.util.Set;

public interface IStudentService {
    void addStudent(Student student) throws MyException;

    void deleteStudent(Student student) throws MyException;

    void updateStudent(Student student) throws MyException;

    Student getStudentById(long id) throws MyException;

    List<Student> getAllStudents();

    List<Student> filterStudentsByName(String s);

    List<Student> sortStudentsAscendingByName();

    Set<Student> getStudentsWhoPassed();

    Student getStudentsWithMostProblems() throws MyException;
}
