package labProblems.repository;

import Model.Exceptions.ValidatorException;
import Model.LabProblem;
import Model.Validators.LabProblemValidator;
import Model.Validators.Validator;
import Repository.LabProblemXMLRepository;
import Repository.MemoryRepository;
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

public class LabProblemXMLRepositoryTest {

    private Validator<LabProblem> labProblemValidator;
    private MemoryRepository<Long, LabProblem> testRepository;

    @Before
    public void setUp() {
        labProblemValidator = new LabProblemValidator();
        testRepository = new LabProblemXMLRepository(labProblemValidator,".\\files\\xml\\test-problems.xml");
    }

    @After
    public void tearDown() throws Exception {
        testRepository.update(new LabProblem(1L,"p1",10));
        testRepository=null;
    }

    @Test
    public void testLoadData() {
        Set<LabProblem> problems = (HashSet<LabProblem>) testRepository.getAll();
        assertEquals(problems.size(), 3);
        assertTrue(problems.contains(new LabProblem(1L, "p1", 10)));
        assertTrue(problems.contains(new LabProblem(2L, "p2", 20)));
        assertTrue(problems.contains(new LabProblem(3L, "p3", 30)));
    }

    @Test
    public void testSaveAllToFile() throws IOException, ValidatorException, TransformerException, ParserConfigurationException {
        testRepository.update(new LabProblem(1L,"p11",100));
        MemoryRepository<Long,LabProblem> testRepositoryTest = new LabProblemXMLRepository(labProblemValidator,".\\files\\xml\\test-problems.xml");
        Set<LabProblem> problems = (HashSet<LabProblem>) testRepositoryTest.getAll();
        assertEquals(problems.size(), 3);
        assertTrue(problems.contains(new LabProblem(1L, "p11", 100)));
        assertTrue(problems.contains(new LabProblem(2L, "p2", 20)));
        assertTrue(problems.contains(new LabProblem(3L, "p3", 30)));
    }

}
