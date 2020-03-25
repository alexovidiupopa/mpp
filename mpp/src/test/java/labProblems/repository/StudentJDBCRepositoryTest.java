package labProblems.repository;

import Model.Exceptions.ValidatorException;
import Model.Student;
import Model.Validators.StudentValidator;
import Model.Validators.Validator;
import Repository.RepositoryInterface;
import Repository.StudentJDBCRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@FixMethodOrder (MethodSorters.NAME_ASCENDING)
public class StudentJDBCRepositoryTest {

    private RepositoryInterface<Long, Student> repo;

    @Before
    public void setUp() {
        Validator<Student> validator = new StudentValidator();
        repo = new StudentJDBCRepository(validator, ".\\files\\credentials\\test.txt");

    }

    @After
    public void tearDown() throws Exception {
        repo.update(new Student(1L,"111", "alex", 4));
        repo=null;
    }

    @Test
    public void testAdd() throws ValidatorException, ParserConfigurationException, TransformerException, IOException, SQLException, SAXException {
        repo.add(new Student(10L, "10", "10", 4));
        assertTrue(((HashSet<Student>)repo.getAll()).contains(new Student(10L, "10", "10", 4)));
    }

    @Test
    public void testAddExisting() throws ValidatorException, ParserConfigurationException, TransformerException, IOException, SQLException, SAXException {
        Optional<Student> student = repo.add(new Student(10L, "10", "10", 4));
        assertTrue(student.isPresent());
    }

    @Test
    public void testDelete() throws ParserConfigurationException, TransformerException, SQLException, IOException {
        repo.delete(10L);
        assertFalse(((HashSet<Student>)repo.getAll()).contains(new Student(10L, "10", "10", 4)));
    }

    @Test
    public void testDeleteNotExisting() throws ParserConfigurationException, TransformerException, SQLException, IOException {
        Optional<Student> student = repo.delete(10L);
        assertTrue(student.isEmpty());
    }

    @Test
    public void testUpdate() throws ParserConfigurationException, TransformerException, SQLException, IOException, ValidatorException {
        repo.update(new Student(1L,"111", "new",4));
        assertTrue(((HashSet<Student>)repo.getAll()).contains(new Student(1L, "111", "new", 4)));
    }

    @Test
    public void testUpdateNotExisting() throws IOException, TransformerException, ParserConfigurationException, SQLException, ValidatorException {
        Optional<Student> student = repo.update(new Student(20L,"111", "new",4));
        assertTrue(student.isPresent());
    }

}
