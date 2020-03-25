package labProblems.repository;

import Model.Exceptions.ValidatorException;
import Model.LabProblem;
import Model.Validators.LabProblemValidator;
import Model.Validators.Validator;
import Repository.LabProblemJDBCRepository;
import Repository.RepositoryInterface;
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

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LabProblemJDBCRepositoryTest {

    private RepositoryInterface<Long, LabProblem> repo;

    @Before
    public void setUp() throws Exception {
        Validator<LabProblem> validator = new LabProblemValidator();
        repo = new LabProblemJDBCRepository(validator,".\\files\\credentials\\test.txt");
    }

    @After
    public void tearDown() throws Exception {
        repo.update(new LabProblem(1L,"stack", 10));
        repo = null;
    }

    @Test
    public void testAdd() throws ValidatorException, ParserConfigurationException, TransformerException, IOException, SQLException, SAXException {
        repo.add(new LabProblem(10L,"desc",10));
        assertTrue(((HashSet<LabProblem>)repo.getAll()).contains(new LabProblem(10L,"desc",10)));
    }

    @Test
    public void testAddExisting() throws ValidatorException, ParserConfigurationException, TransformerException, IOException, SQLException, SAXException {
        Optional<LabProblem> problem = repo.add(new LabProblem(10L,"desc",10));
        assertTrue(problem.isPresent());
    }

    @Test
    public void testDelete() throws ParserConfigurationException, TransformerException, SQLException, IOException {
        repo.delete(10L);
        assertFalse(((HashSet<LabProblem>)repo.getAll()).contains(new LabProblem(10L,"desc",10)));
    }

    @Test
    public void testDeleteNotExisting() throws ParserConfigurationException, TransformerException, SQLException, IOException {
        Optional<LabProblem> problem = repo.delete(10L);
        assertTrue(problem.isEmpty());
    }

    @Test
    public void testUpdate() throws IOException, TransformerException, ParserConfigurationException, SQLException, ValidatorException {
        repo.update(new LabProblem(1L, "asd", 10));
        assertTrue(((HashSet<LabProblem>)repo.getAll()).contains(new LabProblem(1L,"asd",10)));
    }

    @Test
    public void testUpdateNotExisting() throws IOException, TransformerException, ParserConfigurationException, SQLException, ValidatorException {
        Optional<LabProblem> problem = repo.update(new LabProblem(100L, "asd", 100));
        assertTrue(problem.isPresent());
    }
}
