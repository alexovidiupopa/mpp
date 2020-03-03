package labProblems.controller;

import Controller.LabProblemController;
import Model.Exceptions.ValidatorException;
import Model.LabProblem;
import Model.Student;
import Model.Validators.LabProblemValidator;
import Repository.MemoryRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

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
    public void testAddProblem() throws ValidatorException {
        this.labProblemController.addProblem(new LabProblem(55L, "problem5", 40));
    }

    @Test(expected = ValidatorException.class)
    public void testAddProblemException() throws ValidatorException {
        this.labProblemController.addProblem(new LabProblem(55L, "problem5", -100));
    }

    @Test
    public void testAddProblemUsedId() throws ValidatorException {
        this.labProblemController.addProblem(new LabProblem(33L, "problem5", 100));
        assertTrue(this.labProblemController.getAllProblems().contains(new LabProblem(33L, "problem3", 10)));
        assertFalse(this.labProblemController.getAllProblems().contains(new LabProblem(33L, "problem5", 100)));
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
}
