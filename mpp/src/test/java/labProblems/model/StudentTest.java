package labProblems.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import Model.Student;

import static org.junit.Assert.assertEquals;

public class StudentTest {

    private static final Long ID = 1L;
    private static final Long NEW_ID = 2L;
    private static final String SERIAL_NUMBER = "sn01";
    private static final String NEW_SERIAL_NUMBER = "sn02";
    private static final String NAME = "studentName";
    private static final String NEW_NAME = "studentName2";
    private static final int GROUP = 123;
    private static final int NEW_GROUP = 124;
    private Student student;

    @Before
    public void setUp() {
        student = new Student(SERIAL_NUMBER, NAME, GROUP);
        student.setId(ID);
    }

    @After
    public void tearDown() {
        student=null;
    }

    @Test
    public void testGetSerialNumber() {
        assertEquals("Serial numbers should be equal", SERIAL_NUMBER, student.getSerialNumber());
    }

    @Test
    public void testSetSerialNumber() {
        student.setSerialNumber(NEW_SERIAL_NUMBER);
        assertEquals("Serial numbers should be equal", NEW_SERIAL_NUMBER, student.getSerialNumber());
    }

    @Test
    public void testGetId() {
        assertEquals("Ids should be equal", ID, student.getId());
    }

    @Test
    public void testSetId() {
        student.setId(NEW_ID);
        assertEquals("Ids should be equal", NEW_ID, student.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("Names should be equal", SERIAL_NUMBER, student.getSerialNumber());
    }


    @Test
    public void testSetName() {
        student.setName(NEW_NAME);
        assertEquals("Names should be equal", NEW_NAME, student.getName());
    }

    @Test
    public void testGetGroup() {
        assertEquals("Groups should be equal", GROUP, student.getGroup());
    }

    @Test
    public void testSetGroup() {
        student.setGroup(NEW_GROUP);
        assertEquals("Groups should be equal", NEW_GROUP, student.getGroup());
    }

}