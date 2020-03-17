package labProblems.repository;

import Model.Exceptions.ValidatorException;
import Model.Student;
import Model.Validators.StudentValidator;
import Model.Validators.Validator;
import Repository.MemoryRepository;
import Repository.StudentFileRepository;
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

public class StudentFileRepositoryTest {
    private  Validator<Student> studentValidator;
    private MemoryRepository<Long,Student> testRepository;

    @Before
    public void setUp() throws Exception {
        studentValidator = new StudentValidator();
        testRepository = new StudentFileRepository(studentValidator,".\\files\\txt\\test-students.txt");
    }

    @After
    public void tearDown() throws Exception {
        testRepository.update(new Student(1,"1","alex",1));
        testRepository=null;
    }


    @Test
    public void testSaveAllToFile() throws ValidatorException, IOException, TransformerException, ParserConfigurationException {
        testRepository.update(new Student(1,"1","alin",2));
        MemoryRepository<Long,Student> testRepositoryTest = new StudentFileRepository(studentValidator,".\\files\\txt\\test-students.txt");
        Set<Student> students = (HashSet<Student>) testRepositoryTest.getAll();
        assertEquals(students.size(),2);
        assertTrue(students.contains(new Student(1,"1","alin",2)));
    }


    @Test
    public void testLoadData() {
        Set<Student> students = (HashSet<Student>) testRepository.getAll();
        assertTrue(students.contains(new Student(1L,"1","alex",1)));
        assertTrue(students.contains(new Student(2L,"2","vlad",2)));
    }
}
