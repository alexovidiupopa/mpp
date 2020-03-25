package labProblems.controller;

import Controller.AssignmentController;
import Controller.LabProblemController;
import Model.Exceptions.RepositoryException;
import Model.Exceptions.ValidatorException;
import Model.LabProblem;
import Model.Validators.AssignmentValidator;
import Model.Validators.LabProblemValidator;
import Repository.MemoryRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class LabProblemControllerTest {

    private LabProblemController labProblemController;

    @Before
    public void setUp() throws Exception {
        this.labProblemController = new LabProblemController(new MemoryRepository<>(new LabProblemValidator()));
        this.labProblemController.setAssignmentController(new AssignmentController(new MemoryRepository<>(new AssignmentValidator())));
        this.labProblemController.addProblem(new LabProblem(11L, "problem1", 100));
        this.labProblemController.addProblem(new LabProblem(22L, "problem2", 50));
        this.labProblemController.addProblem(new LabProblem(33L, "problem3", 10));
        this.labProblemController.addProblem(new LabProblem(44L, "problem4", 200));
    }

    @After
    public void tearDown() {
        this.labProblemController = null;
    }

    @Test
    public void testAddProblem() throws ValidatorException, RepositoryException, IOException, TransformerException, ParserConfigurationException, SAXException, SQLException {
        this.labProblemController.addProblem(new LabProblem(55L, "problem5", 40));
    }

    @Test(expected = ValidatorException.class)
    public void testAddProblemException() throws ValidatorException, RepositoryException, IOException, TransformerException, ParserConfigurationException, SAXException, SQLException {
        this.labProblemController.addProblem(new LabProblem(55L, "problem5", -100));
    }

    @Test (expected = RepositoryException.class)
    public void testAddProblemUsedId() throws ValidatorException, RepositoryException, IOException, TransformerException, ParserConfigurationException, SAXException, SQLException {
        this.labProblemController.addProblem(new LabProblem(33L, "problem5", 100));
    }

    @Test
    public void testDeleteProblem() throws RepositoryException, IOException, TransformerException, ParserConfigurationException, SQLException {
        this.labProblemController.deleteProblem(new LabProblem(33L, "problem3", 10));
        assertFalse(this.labProblemController.getAllProblems().contains(new LabProblem(33L, "problem3", 10)));
    }

    @Test
    public void testUpdateProblem() throws ValidatorException, RepositoryException, IOException, TransformerException, ParserConfigurationException, SQLException {
        this.labProblemController.updateProblem(new LabProblem(33L, "updated", 10));
        assertFalse(this.labProblemController.getAllProblems().contains(new LabProblem(33L, "problem3", 10)));
        assertTrue(this.labProblemController.getAllProblems().contains(new LabProblem(33L, "updated", 10)));
    }

    @Test (expected = ValidatorException.class)
    public void testUpdateProblemException() throws ValidatorException, RepositoryException, IOException, TransformerException, ParserConfigurationException, SQLException {
        this.labProblemController.updateProblem(new LabProblem(33L, "expect-exception", -1));
    }

    @Test
    public void testGetAllProblems() throws SQLException {
        assertEquals(this.labProblemController.getAllProblems().size(), 4);
        assertTrue(this.labProblemController.getAllProblems().contains(new LabProblem(11L, "problem1", 100)));
        assertTrue(this.labProblemController.getAllProblems().contains(new LabProblem(44L, "problem4", 200)));
    }

    @Test
    public void testFilterProblemsByScore() throws SQLException {
        Set<LabProblem> filtered = this.labProblemController.filterProblemsByScore(100);
        assertEquals(filtered.size(), 2);
        assertTrue(this.labProblemController.getAllProblems().contains(new LabProblem(11L, "problem1", 100)));
        assertTrue(this.labProblemController.getAllProblems().contains(new LabProblem(44L, "problem4", 200)));
    }

    @Test
    public void testSortProblemsDescendingByScore() throws SQLException {
        List<LabProblem> sortedProblems = this.labProblemController.sortProblemsDescendingByScore();
        assertArrayEquals(sortedProblems.toArray(), this.labProblemController.getAllProblems().stream().sorted((o1, o2) -> o2.getScore() - o1.getScore()).toArray());
    }

}
