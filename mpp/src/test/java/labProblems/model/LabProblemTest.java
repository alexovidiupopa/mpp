package labProblems.model;

import Model.LabProblem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LabProblemTest {

    private static final Long ID = 1L;
    private static final Long NEW_ID = 2L;
    private static final String DESCRIPTION = "implement a stack";
    private static final String NEW_DESCRIPTION = "implement a queue";
    private static final int SCORE = 100;
    private static final int NEW_SCORE = 90;
    private LabProblem problem;

    @Before
    public void setUp() {
        problem = new LabProblem(ID,DESCRIPTION,SCORE);
    }

    @After
    public void tearDown() {
        problem=null;
    }

    @Test
    public void testGetID() {
        assertEquals("Ids should be equal", ID, problem.getId());
    }

    @Test
    public void testSetID() {
        problem.setId(NEW_ID);
        assertEquals("Ids should be equal", NEW_ID, problem.getId());
    }

    @Test
    public void testGetDescription() {
        assertEquals("Descriptions should be equal", DESCRIPTION, problem.getDescription());
    }

    @Test
    public void testSetDescription() {
        problem.setDescription(NEW_DESCRIPTION);
        assertEquals("Descriptions should be equal", NEW_DESCRIPTION, problem.getDescription());
    }

    @Test
    public void testGetScore() {
        assertEquals("Scores should be equal", SCORE, problem.getScore());
    }

    @Test
    public void testSetScore() {
        problem.setScore(NEW_SCORE);
        assertEquals("Scores should be equal", NEW_SCORE, problem.getScore());
    }

}
