package service;

import model.Exceptions.MyException;
import model.Student;

import java.util.List;
import java.util.Set;

public interface StudentControllerInterface {

    void addStudent(Student student) throws MyException;

    void deleteStudent(Student student) throws MyException;

    void updateStudent(Student student) throws MyException;

    Student getStudentById(long id) throws MyException;

    Set<Student> getAllStudents();

    Set<Student> filterStudentsByName(String s);

    List<Student> sortStudentsAscendingByName();

    Set<Student> getStudentsWhoPassed();

    Student getStudentsWithMostProblems() throws MyException;
}
