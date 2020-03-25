package labProblems.repository;

import Model.Assignment;
import Model.Exceptions.ValidatorException;
import Model.Validators.AssignmentValidator;
import Model.Validators.Validator;
import Repository.AssignmentsJDBCRepository;
import Repository.RepositoryInterface;
import Utils.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class AssignmentJDBCRepositoryTest {

    private RepositoryInterface<Pair<Long,Long>, Assignment> testRepository;

    @Before
    public void setUp() {
        Validator<Assignment> assignmentValidator = new AssignmentValidator();
        this.testRepository = new AssignmentsJDBCRepository(assignmentValidator,".\\files\\credentials\\test.txt");
    }

    @After
    public void tearDown() throws Exception {
        this.testRepository.update(new Assignment(new Pair<>(1L,1L),3.0));
        this.testRepository=null;
    }

    @Test
    public void testAdd() throws IOException, SQLException, SAXException, TransformerException, ParserConfigurationException, ValidatorException {
        this.testRepository.add(new Assignment(new Pair<>(1L, 2L)));
        List<Assignment> resultList = new ArrayList<>();
        this.testRepository.getAll().forEach(resultList::add);
        assertEquals(resultList.size(), 6);
        assertTrue(resultList.contains(new Assignment(new Pair<>(1L, 2L))));
        assertTrue(resultList.contains(new Assignment(new Pair<>(1L, 1L), 3.0)));
    }

    @Test
    public void testAddExisting() throws ValidatorException, ParserConfigurationException, TransformerException, IOException, SQLException, SAXException {
        Optional<Assignment> assignment = this.testRepository.add(new Assignment(new Pair<>(1L, 1L)));
        assertTrue(assignment.isPresent());
    }

    @Test
    public void testDelete() throws ParserConfigurationException, TransformerException, SQLException, IOException {
        this.testRepository.delete(new Pair<>(1L, 2L));
        assertFalse(((HashSet<Assignment>)this.testRepository.getAll()).contains(new Assignment(new Pair<>(1L, 2L))));
    }

    @Test
    public void testDeleteNotExisting() throws ParserConfigurationException, TransformerException, SQLException, IOException {
        Optional<Assignment> assignment = this.testRepository.delete(new Pair<>(100L, 100L));
        assertTrue(assignment.isEmpty());
    }

    @Test
    public void testUpdate() throws IOException, TransformerException, ParserConfigurationException, SQLException, ValidatorException {
        this.testRepository.update(new Assignment(new Pair<>(1L, 1L), 8.0));
        assertTrue(((HashSet<Assignment>)this.testRepository.getAll()).contains(new Assignment(new Pair<>(1L, 1L), 8.0)));
        assertFalse(((HashSet<Assignment>)this.testRepository.getAll()).contains(new Assignment(new Pair<>(1L, 1L), 3.0)));
    }

    @Test
    public void testUpdateNotExisting() throws IOException, TransformerException, ParserConfigurationException, SQLException, ValidatorException {
        Optional<Assignment> assignment = this.testRepository.update(new Assignment(new Pair<>(100L, 100L), 8.0));
        assertTrue(assignment.isPresent());
    }

    @Test
    public void testFindById() throws SQLException {
        Optional<Assignment> assignment1 = this.testRepository.findById(new Pair<>(1L, 1L));
        assertTrue(assignment1.isPresent());
        Optional<Assignment> assignment2 = this.testRepository.findById(new Pair<>(100L, 100L));
        assertTrue(assignment2.isEmpty());
    }

    @Test
    public void testGetAll() throws SQLException {
        List<Assignment> allAssignments = new ArrayList<>();
        this.testRepository.getAll().forEach(allAssignments::add);
        assertEquals(allAssignments.size(),5);
        assertTrue(allAssignments.contains(new Assignment(new Pair<>(1L, 1L), 3.0)));
        assertTrue(allAssignments.contains(new Assignment(new Pair<>(2L, 2L))));
        assertTrue(allAssignments.contains(new Assignment(new Pair<>(3L, 3L))));
        assertTrue(allAssignments.contains(new Assignment(new Pair<>(4L, 1L), 7.3)));
        assertTrue(allAssignments.contains(new Assignment(new Pair<>(4L, 3L), 8.1)));
    }

}
