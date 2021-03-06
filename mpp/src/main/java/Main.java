
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
import Repository.*;
import Utils.Pair;
import View.Console;

public class Main {

    public static void main(String[] args) {
        Validator<Student> studentValidator = new StudentValidator();
        //RepositoryInterface<Long, Student> studentRepository = new StudentJDBCRepository(studentValidator, ".\\files\\credentials\\vlad.txt");
        RepositoryInterface<Long, Student> studentRepository = new StudentJDBCRepository(studentValidator, ".\\files\\credentials\\alex.txt");
        StudentController studentController = new StudentController(studentRepository);


        Validator<LabProblem> labProblemValidator = new LabProblemValidator();
        //RepositoryInterface<Long, LabProblem> labProblemRepository = new LabProblemJDBCRepository(labProblemValidator,".\\files\\credentials\\vlad.txt");
        RepositoryInterface<Long, LabProblem> labProblemRepository = new LabProblemJDBCRepository(labProblemValidator,".\\files\\credentials\\alex.txt");
        LabProblemController labProblemController = new LabProblemController(labProblemRepository);


        Validator<Assignment> assignmentValidator = new AssignmentValidator();
        //RepositoryInterface<Pair<Long, Long>, Assignment> assignmentRepository = new AssignmentsJDBCRepository(assignmentValidator, ".\\files\\credentials\\vlad.txt");
        RepositoryInterface<Pair<Long, Long>, Assignment> assignmentRepository = new AssignmentsJDBCRepository(assignmentValidator, ".\\files\\credentials\\alex.txt");
        AssignmentController assignmentController = new AssignmentController(assignmentRepository);

        studentController.setAssignmentController(assignmentController);
        labProblemController.setAssignmentController(assignmentController);
        assignmentController.setStudentController(studentController);
        assignmentController.setProblemController(labProblemController);

        Console console = new Console(studentController, labProblemController, assignmentController);
        console.run();
    }

}