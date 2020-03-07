package labProblems.repository;

import Model.Student;
import Model.Validators.StudentValidator;
import Model.Validators.Validator;
import Repository.MemoryRepository;
import Repository.RepositoryInterface;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import Model.Exceptions.ValidatorException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

public class InMemoryRepositoryTest {

    private Validator<Student> testValidator;
    private RepositoryInterface<Long, Student> testStudentRepository;

    @Before
    public void setUp() throws Exception {
        testValidator = new StudentValidator();
        testStudentRepository = new MemoryRepository<>(testValidator);
        testStudentRepository.add(new Student(1L,"1234","alex",1));
        testStudentRepository.add(new Student(2L,"9999","vlad",3));
    }

    @After
    public void tearDown() throws Exception {
        testValidator=null;
        testStudentRepository=null;
    }

    @Test
    public void testFindById() throws Exception {
        assertEquals(testStudentRepository.findById(1L).get(), new Student(1L,"1234","alex",1));
    }

    @Test
    public void testGetAll() throws Exception {
        Set<Student> allStudents = (HashSet<Student>) testStudentRepository.getAll();
        assertEquals(allStudents.size(),2);
        assertTrue(allStudents.contains(new Student(2L,"9999","vlad",3)));
    }

    @Test
    public void testAdd() throws Exception {
        testStudentRepository.add(new Student(3L,"5678","alin",3));
        Set<Student> allStudents = (HashSet<Student>) testStudentRepository.getAll();
        assertEquals(allStudents.size(),3);
        assertTrue(allStudents.contains(new Student(3L,"5678","alin",3)));
    }

    @Test(expected = ValidatorException.class)
    public void testAddException() throws Exception {
        testStudentRepository.add(new Student(2L,"","",-1));
    }

    @Test
    public void testDelete() throws Exception {
        testStudentRepository.delete(1L);
        Set<Student> allStudents = (HashSet<Student>) testStudentRepository.getAll();
        assertEquals(allStudents.size(),1);
        assertFalse(allStudents.contains(new Student(1L,"1234","alex",1)));
    }

    @Test
    public void testUpdate() throws Exception {
        assertEquals(testStudentRepository.update(new Student(1L,"1234","popa",1)), Optional.empty());
        assertEquals(testStudentRepository.findById(1L).get().getName(),"popa");
    }

    @Test(expected = ValidatorException.class)
    public void testUpdateException() throws Exception {
        testStudentRepository.update(new Student(5L,"1234","",1));
    }

}