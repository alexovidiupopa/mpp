package labProblems.repository;

import Model.Assignment;
import Model.Validators.AssignmentValidator;
import Model.Validators.Validator;
import Repository.AssignmentFileRepository;
import Repository.RepositoryInterface;
import Utils.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AssignmentFileRepositoryTest {

    private Validator<Assignment> assignmentValidator;
    private RepositoryInterface<Pair<Long,Long>, Assignment> testRepository;

    @Before
    public void setUp() {
        assignmentValidator=new AssignmentValidator();
        testRepository = new AssignmentFileRepository(assignmentValidator,".\\files\\credentials\\test.txt");
    }

    @After
    public void tearDown() throws Exception {
        testRepository.add(new Assignment(new Pair<>(1L,1L),3.0));
        testRepository=null;
    }

    @Test
    public void testLoadData() throws SQLException {
        Set<Assignment> assignments = (HashSet<Assignment>) testRepository.getAll();
        assertEquals(assignments.size(),2);
        assertTrue(assignments.contains(new Assignment(new Pair<>(1L,1L),3.0)));
        assertTrue(assignments.contains(new Assignment(new Pair<>(2L,1L),4.0)));
    }

    @Test
    public void testSaveAllToFile() throws IOException, TransformerException, ParserConfigurationException, SQLException {
        Set<Assignment> assignments = (HashSet<Assignment>) testRepository.getAll();
        assertEquals(assignments.size(),2);
        testRepository.delete(new Pair<>(1L,1L));
        RepositoryInterface<Pair<Long,Long>, Assignment> testRepositorySave = new AssignmentFileRepository(assignmentValidator,".\\files\\txt\\test-assignments.txt");
        Set<Assignment> assignmentsNew = (HashSet<Assignment>) testRepositorySave.getAll();
        assertEquals(assignmentsNew.size(),1);
    }

}
