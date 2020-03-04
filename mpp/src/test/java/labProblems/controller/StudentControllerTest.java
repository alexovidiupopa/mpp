package labProblems.controller;

import Controller.StudentController;
import Model.Exceptions.ValidatorException;
import Model.Student;
import Model.Validators.StudentValidator;
import Repository.MemoryRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class StudentControllerTest {

    private StudentController studentController;

    @Before
    public void setUp() throws Exception {
        this.studentController = new StudentController(new MemoryRepository<>(new StudentValidator()));
        this.studentController.addStudent(new Student(23L, "ab12", "Mary", 1));
        this.studentController.addStudent(new Student(12L, "cd34", "Alex 1", 2));
        this.studentController.addStudent(new Student(45L, "ef56", "Andrew", 1));
        this.studentController.addStudent(new Student(89L, "gh78", "Alex 2", 3));
    }

    @After
    public void tearDown() throws Exception {
        this.studentController = null;
    }

    @Test
    public void testAddStudent() throws ValidatorException {
        this.studentController.addStudent(new Student(4L, "1234", "Michael", 1));
    }

    @Test(expected = ValidatorException.class)
    public void testAddStudentException() throws ValidatorException {
        this.studentController.addStudent(new Student(1L, "abcd", "William", -2));
    }

    @Test
    public void testAddStudentUsedId() throws ValidatorException {
        this.studentController.addStudent(new Student(23L, "abcd", "William", 2));
        assertTrue(this.studentController.getAllStudents().contains(new Student(23L, "ab12", "Mary", 1)));
        assertFalse(this.studentController.getAllStudents().contains(new Student(23L, "abcd", "William", 2)));
    }

    @Test
    public void testGetAllStudents(){
        assertEquals(this.studentController.getAllStudents().size(), 4);
        assertTrue(this.studentController.getAllStudents().contains(new Student(89L, "gh78", "Alex 2", 3)));
        assertTrue(this.studentController.getAllStudents().contains(new Student(23L, "ab12", "Mary", 1)));
    }

    @Test
    public void testFilterStudentByName(){
        Set<Student> filtered = this.studentController.filterStudentsByName("Alex");
        assertEquals(filtered.size(), 2);
        assertTrue(this.studentController.getAllStudents().contains(new Student(12L, "cd34", "Alex 1", 2)));
        assertTrue(this.studentController.getAllStudents().contains(new Student(89L, "gh78", "Alex 2", 3)));
    }

}
