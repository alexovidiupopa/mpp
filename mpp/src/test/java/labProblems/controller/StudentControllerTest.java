package labProblems.controller;

import Controller.StudentController;
import Model.Exceptions.RepositoryException;
import Model.Exceptions.ValidatorException;
import Model.Student;
import Model.Validators.StudentValidator;
import Repository.MemoryRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
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
    public void tearDown() {
        this.studentController = null;
    }

    @Test
    public void testAddStudent() throws ValidatorException, RepositoryException, IOException, TransformerException, ParserConfigurationException, SAXException {
        this.studentController.addStudent(new Student(4L, "1234", "Michael", 1));
    }

    @Test(expected = ValidatorException.class)
    public void testAddStudentException() throws ValidatorException, RepositoryException, IOException, TransformerException, ParserConfigurationException, SAXException {
        this.studentController.addStudent(new Student(1L, "abcd", "William", -2));
    }

    @Test
    public void testDeleteStudent() throws RepositoryException, IOException, TransformerException, ParserConfigurationException {
        this.studentController.deleteStudent(new Student(23L, "ab12", "Mary", 1));
        assertEquals(this.studentController.getAllStudents().size(),3);
        assertFalse(this.studentController.getAllStudents().contains(new Student(23L, "ab12", "Mary", 1)));
    }

    @Test
    public void testUpdateStudent() throws ValidatorException, RepositoryException, IOException, TransformerException, ParserConfigurationException {
        this.studentController.updateStudent(new Student(23L, "ab12", "Angela", 2));
        assertTrue(this.studentController.getAllStudents().contains(new Student(23L, "ab12", "Angela", 2)));
        assertFalse(this.studentController.getAllStudents().contains(new Student(23L, "ab12", "Mary", 1)));
    }

    @Test(expected = ValidatorException.class)
    public void testUpdateStudentException() throws ValidatorException, RepositoryException, IOException, TransformerException, ParserConfigurationException {
        this.studentController.updateStudent(new Student(23L, "", "Angela", 2));
    }

    @Test(expected = RepositoryException.class)
    public void testAddStudentUsedId() throws ValidatorException, RepositoryException, IOException, TransformerException, ParserConfigurationException, SAXException {
        this.studentController.addStudent(new Student(23L, "abcd", "William", 2));
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

    @Test
    public void testSortStudentsAscendingByName() {
        List<Student> sortedStudents = this.studentController.sortStudentsAscendingByName();
        assertArrayEquals(sortedStudents.toArray(), this.studentController.getAllStudents().stream().sorted(Comparator.comparing(Student::getName)).toArray());
    }

}
