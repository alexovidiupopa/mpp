package labProblems.controller;

import Controller.LabProblemController;
import Model.Exceptions.RepositoryException;
import Model.Exceptions.ValidatorException;
import Model.LabProblem;
import Model.Student;
import Model.Validators.LabProblemValidator;
import Model.Validators.Validator;
import Repository.MemoryRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class LabProblemControllerTest {

    private LabProblemController labProblemController;

    @Before
    public void setUp() throws Exception {
        this.labProblemController = new LabProblemController(new MemoryRepository<>(new LabProblemValidator()));
        this.labProblemController.addProblem(new LabProblem(11L, "problem1", 100));
        this.labProblemController.addProblem(new LabProblem(22L, "problem2", 50));
        this.labProblemController.addProblem(new LabProblem(33L, "problem3", 10));
        this.labProblemController.addProblem(new LabProblem(44L, "problem4", 200));
    }

    @After
    public void tearDown() throws Exception {
        this.labProblemController = null;
    }

    @Test
    public void testAddProblem() throws ValidatorException, RepositoryException {
        this.labProblemController.addProblem(new LabProblem(55L, "problem5", 40));
    }

    @Test(expected = ValidatorException.class)
    public void testAddProblemException() throws ValidatorException, RepositoryException {
        this.labProblemController.addProblem(new LabProblem(55L, "problem5", -100));
    }

    @Test (expected = RepositoryException.class)
    public void testAddProblemUsedId() throws ValidatorException, RepositoryException {
        this.labProblemController.addProblem(new LabProblem(33L, "problem5", 100));
    }

    @Test
    public void testDeleteProblem() throws RepositoryException {
        this.labProblemController.deleteProblem(new LabProblem(33L, "problem3", 10));
        assertFalse(this.labProblemController.getAllProblems().contains(new LabProblem(33L, "problem3", 10)));
    }

    @Test
    public void testUpdateProblem() throws ValidatorException, RepositoryException {
        this.labProblemController.updateProblem(new LabProblem(33L, "updated", 10));
        assertFalse(this.labProblemController.getAllProblems().contains(new LabProblem(33L, "problem3", 10)));
        assertTrue(this.labProblemController.getAllProblems().contains(new LabProblem(33L, "updated", 10)));
    }

    @Test (expected = ValidatorException.class)
    public void testUpdateProblemException() throws ValidatorException, RepositoryException {
        this.labProblemController.updateProblem(new LabProblem(33L, "expect-exception", -1));
    }

    @Test
    public void testGetAllProblems(){
        assertEquals(this.labProblemController.getAllProblems().size(), 4);
        assertTrue(this.labProblemController.getAllProblems().contains(new LabProblem(11L, "problem1", 100)));
        assertTrue(this.labProblemController.getAllProblems().contains(new LabProblem(44L, "problem4", 200)));
    }

    @Test
    public void testFilterProblemsByScore(){
        Set<LabProblem> filtered = this.labProblemController.filterProblemsByScore(100);
        assertEquals(filtered.size(), 2);
        assertTrue(this.labProblemController.getAllProblems().contains(new LabProblem(11L, "problem1", 100)));
        assertTrue(this.labProblemController.getAllProblems().contains(new LabProblem(44L, "problem4", 200)));
    }

    @Test
    public void testSortProblemsDescendingByScore() {
        List<LabProblem> sortedProblems = this.labProblemController.sortProblemsDescendingByScore();
        List<LabProblem> expectedProblems =  this.labProblemController.getAllProblems().stream().sorted((o1, o2) -> o2.getScore()-o1.getScore()).collect(Collectors.toList());
        assertArrayEquals(sortedProblems.toArray(),expectedProblems.toArray());
    }
}
