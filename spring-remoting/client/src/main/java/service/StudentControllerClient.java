package service;

import model.Exceptions.MyException;
import model.Student;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

public class StudentControllerClient implements StudentControllerInterface {
    @Autowired
    private StudentControllerInterface studentController;

    @Override
    public void addStudent(Student student) throws MyException {
        studentController.addStudent(student);
    }

    @Override
    public void deleteStudent(Student student) throws MyException {
        studentController.deleteStudent(student);
    }

    @Override
    public void updateStudent(Student student) throws MyException {
        studentController.updateStudent(student);
    }

    @Override
    public Student getStudentById(long id) throws MyException {
        return studentController.getStudentById(id);
    }

    @Override
    public Set<Student> getAllStudents() {
        return studentController.getAllStudents();
    }

    @Override
    public Set<Student> filterStudentsByName(String s) {
        return studentController.filterStudentsByName(s);
    }

    @Override
    public List<Student> sortStudentsAscendingByName() {
        return studentController.sortStudentsAscendingByName();
    }

    @Override
    public Set<Student> getStudentsWhoPassed() {
        return studentController.getStudentsWhoPassed();
    }

    @Override
    public Student getStudentsWithMostProblems() throws MyException {
        return studentController.getStudentsWithMostProblems();
    }
}
