package labProblems.repository;

import Model.Exceptions.ValidatorException;
import Model.LabProblem;
import Model.Validators.LabProblemValidator;
import Model.Validators.Validator;
import Repository.LabProblemFileRepository;
import Repository.MemoryRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LabProblemFileRepositoryTest {
    private Validator<LabProblem> labProblemValidator;
    private MemoryRepository<Long, LabProblem> testRepository;

    @Before
    public void setUp() throws Exception {
        labProblemValidator = new LabProblemValidator();
        testRepository = new LabProblemFileRepository(labProblemValidator,"./files/test-problems.txt");
    }


    @After
    public void tearDown() throws Exception {
        testRepository=null;
    }


    @Test
    public void testLoadData() {
        Set<LabProblem> problems = (HashSet<LabProblem>) testRepository.getAll();
        assertTrue(problems.contains(new LabProblem(1L, "stack", 10)));
        assertTrue(problems.contains(new LabProblem(2L, "queue", 100)));
    }

    @Test
    public void testSaveAllToFile() {
        testRepository.delete(3L);
        MemoryRepository<Long,LabProblem> testRepositoryTest = new LabProblemFileRepository(labProblemValidator,"./files/test-problems.txt");
        Set<LabProblem> problems = (HashSet<LabProblem>) testRepositoryTest.getAll();
        assertEquals(problems.size(),2);
    }

    @Test
    public void testSaveToFile() throws ValidatorException {
        testRepository.add(new LabProblem(3L,"asd",11));
        MemoryRepository<Long,LabProblem> testRepositoryTest = new LabProblemFileRepository(labProblemValidator,"./files/test-problems.txt");
        Set<LabProblem> problems = (HashSet<LabProblem>) testRepositoryTest.getAll();
        assertEquals(problems.size(),3);
    }


}
