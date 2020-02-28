package catalog.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ro.ubb.catalog.domain.Student;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author radu.
 */
public class StudentTest {
    private static final Long ID = new Long(1);
    private static final Long NEW_ID = new Long(2);
    private static final String SERIAL_NUMBER = "sn01";
    private static final String NEW_SERIAL_NUMBER = "sn02";
    private static final String NAME = "studentName";
    private static final int GROUP = 123;

    private Student student;

    @Before
    public void setUp() throws Exception {
        student = new Student(SERIAL_NUMBER, NAME, GROUP);
        student.setId(ID);
    }

    @After
    public void tearDown() throws Exception {
        student=null;
    }

    @Test
    public void testGetSerialNumber() throws Exception {
        assertEquals("Serial numbers should be equal", SERIAL_NUMBER, student.getSerialNumber());
    }

    @Test
    public void testSetSerialNumber() throws Exception {
        student.setSerialNumber(NEW_SERIAL_NUMBER);
        assertEquals("Serial numbers should be equal", NEW_SERIAL_NUMBER, student.getSerialNumber());
    }

    @Test
    public void testGetId() throws Exception {
        assertEquals("Ids should be equal", ID, student.getId());
    }

    @Test
    public void testSetId() throws Exception {
        student.setId(NEW_ID);
        assertEquals("Ids should be equal", NEW_ID, student.getId());
    }

    @Ignore
    @Test
    public void testGetName() throws Exception {
        fail("Not tested yet.");
    }

    @Ignore
    @Test
    public void testSetName() throws Exception {
        fail("Not tested yet.");
    }

    @Ignore
    @Test
    public void testGetGroup() throws Exception {
        fail("Not tested yet.");
    }

    @Ignore
    @Test
    public void testSetGroup() throws Exception {
        fail("Not tested yet.");
    }
}