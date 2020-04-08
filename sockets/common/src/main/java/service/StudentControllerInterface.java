package service;

import model.Exceptions.MyException;
import model.Student;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface StudentControllerInterface {

    CompletableFuture<Boolean> addStudent(Student student) throws MyException;

    CompletableFuture<Boolean> deleteStudent(Student student) throws MyException;

    CompletableFuture<Boolean> updateStudent(Student student) throws MyException;

    CompletableFuture<Student> getStudentById(long id) throws MyException;

    CompletableFuture<Set<Student>> getAllStudents();

    CompletableFuture<Set<Student>> filterStudentsByName(String s);

    CompletableFuture<List<Student>> sortStudentsAscendingByName();

    CompletableFuture<Set<Student>> getStudentsWhoPassed();

    CompletableFuture<Student> getStudentsWithMostProblems();

}
