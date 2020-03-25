package labProblems.controller;

import Controller.AssignmentController;
import Controller.LabProblemController;
import Controller.StudentController;
import Model.Assignment;
import Model.Exceptions.RepositoryException;
import Model.Exceptions.ValidatorException;
import Model.LabProblem;
import Model.Student;
import Model.Validators.AssignmentValidator;
import Model.Validators.LabProblemValidator;
import Model.Validators.StudentValidator;
import Model.Validators.Validator;
import Repository.MemoryRepository;
import Repository.RepositoryInterface;
import Utils.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class AssignmentControllerTest {

    private StudentController studentController;
    private LabProblemController labProblemController;
    private AssignmentController assignmentController;

    @Before
    public void setUp() throws Exception {
        Validator<Student> studentValidator = new StudentValidator();
        RepositoryInterface<Long, Student> studentRepo = new MemoryRepository<>(studentValidator);
        studentController = new StudentController(studentRepo);

        Validator<LabProblem> labProblemValidator = new LabProblemValidator();
        RepositoryInterface<Long, LabProblem> labProblemRepo = new MemoryRepository<>(labProblemValidator);
        labProblemController = new LabProblemController(labProblemRepo);

        Validator<Assignment> assignmentValidator = new AssignmentValidator();
        RepositoryInterface<Pair<Long, Long>, Assignment> assignmentRepo = new MemoryRepository<>(assignmentValidator);
        assignmentController = new AssignmentController(assignmentRepo);

        studentController.setAssignmentController(assignmentController);
        labProblemController.setAssignmentController(assignmentController);
        assignmentController.setStudentController(studentController);
        assignmentController.setProblemController(labProblemController);
        studentController.addStudent(new Student(1L,"1","alex",1));
        studentController.addStudent(new Student(2L,"2","vlad",2));

        labProblemController.addProblem(new LabProblem(1L,"stack",2));
        labProblemController.addProblem(new LabProblem(2L,"queue",100));

        assignmentController.addAssignment(new Assignment(new Pair<>(1L,1L)));
    }

    @After
    public void tearDown() {
        studentController = null;
        labProblemController = null;
        assignmentController = null;
    }

    @Test (expected = RepositoryException.class)
    public void testAddAlreadyExisting() throws SAXException, ValidatorException, RepositoryException, TransformerException, IOException, ParserConfigurationException, SQLException {
        assignmentController.addAssignment(new Assignment(new Pair<>(1L,1L)));
    }

    @Test (expected = RepositoryException.class)
    public void testAddNotExistingStudent() throws SAXException, ValidatorException, RepositoryException, TransformerException, IOException, ParserConfigurationException, SQLException {
        assignmentController.addAssignment(new Assignment(new Pair<>(10L,1L)));
    }

    @Test (expected = RepositoryException.class)
    public void testAddNotExistingProblem() throws SAXException, ValidatorException, RepositoryException, TransformerException, IOException, ParserConfigurationException, SQLException {
        assignmentController.addAssignment(new Assignment(new Pair<>(1L,10L)));
    }

    @Test
    public void testGradeAssignment() throws RepositoryException, IOException, TransformerException, ParserConfigurationException, ValidatorException, SQLException {
        assignmentController.updateAssignment(new Assignment(new Pair<>(1L,1L),3.4));
        assertTrue(assignmentController.getAllAssignments().contains(new Assignment(new Pair<>(1L,1L),3.4)));
        assertFalse(assignmentController.getAllAssignments().contains(new Assignment(new Pair<>(1L,1L),-1)));
    }

    @Test
    public void testAddAssignment() throws SAXException, ValidatorException, RepositoryException, TransformerException, IOException, ParserConfigurationException, SQLException {
        assignmentController.addAssignment(new Assignment(new Pair<>(2L,2L),-1));
        assertTrue(assignmentController.getAllAssignments().contains(new Assignment(new Pair<>(2L,2L),-1)));
        assertEquals(assignmentController.getAllAssignments().size(),2);
    }


}
