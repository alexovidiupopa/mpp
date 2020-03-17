import Controller.AssignmentController;
import Controller.LabProblemController;
import Controller.StudentController;
import Model.Assignment;
import Model.LabProblem;
import Model.Student;
import Model.Validators.AssignmentValidator;
import Model.Validators.LabProblemValidator;
import Model.Validators.StudentValidator;
import Model.Validators.Validator;
import Repository.AssignmentXMLRepository;
import Repository.LabProblemXMLRepository;
import Repository.RepositoryInterface;
import Repository.StudentXMLRepository;
import Utils.Pair;
import View.Console;

public class Main {

    public static void main(String[] args) {
        Validator<Student> studentValidator = new StudentValidator();
        RepositoryInterface<Long, Student> studentRepository = new StudentXMLRepository(studentValidator, ".\\files\\xml\\students.xml");
        StudentController studentController = new StudentController(studentRepository);


        Validator<LabProblem> labProblemValidator = new LabProblemValidator();
        RepositoryInterface<Long, LabProblem> labProblemRepository = new LabProblemXMLRepository(labProblemValidator, ".\\files\\xml\\problems.xml");
        LabProblemController labProblemController = new LabProblemController(labProblemRepository);


        Validator<Assignment> assignmentValidator = new AssignmentValidator();
        RepositoryInterface<Pair<Long, Long>, Assignment> assignmentRepository = new AssignmentXMLRepository(assignmentValidator, ".\\files\\xml\\assignments.xml");
        AssignmentController assignmentController = new AssignmentController(assignmentRepository);


        studentController.setAssignmentController(assignmentController);
        labProblemController.setAssignmentController(assignmentController);
        assignmentController.setStudentController(studentController);
        assignmentController.setProblemController(labProblemController);


        Console console = new Console(studentController, labProblemController, assignmentController);
        console.run();
    }

}
