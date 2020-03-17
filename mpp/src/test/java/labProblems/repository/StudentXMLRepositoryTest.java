package labProblems.repository;

import Model.Exceptions.ValidatorException;
import Model.Student;
import Model.Validators.StudentValidator;
import Model.Validators.Validator;
import Repository.MemoryRepository;
import Repository.StudentXMLRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StudentXMLRepositoryTest {

    private Validator<Student> studentValidator;
    private MemoryRepository<Long,Student> testRepository;

    @Before
    public void setUp() {
        studentValidator = new StudentValidator();
        testRepository = new StudentXMLRepository(studentValidator,".\\files\\xml\\test-students.xml");
    }

    @After
    public void tearDown() throws Exception {
        testRepository.update(new Student(1,"10","s1",1));
        testRepository=null;
    }


    @Test
    public void testSaveAllToFile() throws ValidatorException, IOException, TransformerException, ParserConfigurationException {
        testRepository.update(new Student(1L,"100","s11",4));
        MemoryRepository<Long,Student> testRepositoryTest = new StudentXMLRepository(studentValidator,".\\files\\xml\\test-students.xml");
        Set<Student> students = (HashSet<Student>) testRepositoryTest.getAll();
        assertEquals(students.size(),3);
        assertTrue(students.contains(new Student(1L,"100","s11",4)));
        assertTrue(students.contains(new Student(2L,"20","s2",2)));
        assertTrue(students.contains(new Student(3L,"30","s3",3)));
    }


    @Test
    public void testLoadData() {
        Set<Student> students = (HashSet<Student>) testRepository.getAll();
        assertEquals(students.size(), 3);
        assertTrue(students.contains(new Student(1L,"10","s1",1)));
        assertTrue(students.contains(new Student(2L,"20","s2",2)));
        assertTrue(students.contains(new Student(3L,"30","s3",3)));
    }

}
