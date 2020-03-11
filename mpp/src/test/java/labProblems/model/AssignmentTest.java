package labProblems.model;

import Model.Assignment;
import Utils.Pair;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AssignmentTest {
    private static final Long STUD_ID = 1L;
    private static final Long ASIG_ID = 2L;
    private static final Double OLD_GRADE = 2.3;
    private static final Double NEW_GRADE = 6.4;
    private Assignment assignment;

    @Before
    public void setUp() throws Exception {
        assignment = new Assignment(new Pair<>(STUD_ID, ASIG_ID),OLD_GRADE);
    }

    @After
    public void tearDown() throws Exception {
        assignment=null;
    }

    @Test
    public void testGetGrade() {
        Assert.assertEquals(OLD_GRADE,assignment.getGrade());
    }

    @Test
    public void testSetGrade() {
        assignment.setGrade(NEW_GRADE);
        Assert.assertEquals(NEW_GRADE,assignment.getGrade());
    }
}
