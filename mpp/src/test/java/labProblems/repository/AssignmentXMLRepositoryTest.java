package labProblems.repository;

import Model.Assignment;
import Model.Validators.AssignmentValidator;
import Model.Validators.Validator;
import Repository.AssignmentXMLRepository;
import Repository.RepositoryInterface;
import Utils.Pair;
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

public class AssignmentXMLRepositoryTest {

    private Validator<Assignment> assignmentValidator;
    private RepositoryInterface<Pair<Long,Long>, Assignment> testRepository;

    @Before
    public void setUp() {
        assignmentValidator=new AssignmentValidator();
        testRepository = new AssignmentXMLRepository(assignmentValidator,".\\files\\xml\\test-assignments.xml");
    }

    @After
    public void tearDown() throws Exception {
        testRepository.add(new Assignment(new Pair<>(2L,2L),1.3));
        testRepository=null;
    }

    @Test
    public void testLoadData() {
        Set<Assignment> assignments = (HashSet<Assignment>) testRepository.getAll();
        assertEquals(assignments.size(),3);
        assertTrue(assignments.contains(new Assignment(new Pair<>(2L,2L),1.3)));
        assertTrue(assignments.contains(new Assignment(new Pair<>(3L,3L),8.6)));
    }

    @Test
    public void testSaveAllToFile() throws IOException, TransformerException, ParserConfigurationException {
        Set<Assignment> assignments = (HashSet<Assignment>) testRepository.getAll();
        assertEquals(assignments.size(),3);
        testRepository.delete(new Pair<>(2L,2L));
        RepositoryInterface<Pair<Long,Long>, Assignment> testRepositorySave = new AssignmentXMLRepository(assignmentValidator,".\\files\\xml\\test-assignments.xml");
        Set<Assignment> assignmentsNew = (HashSet<Assignment>) testRepositorySave.getAll();
        assertEquals(assignmentsNew.size(),2);
    }

}
