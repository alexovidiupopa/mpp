
import Controller.AssignmentController;
import Controller.LabProblemController;
import Model.Assignment;
import Model.LabProblem;
import Model.Student;
import Model.Validators.AssignemtValidator;
import Model.Validators.LabProblemValidator;
import Model.Validators.StudentValidator;
import Model.Validators.Validator;
import Repository.MemoryRepository;
import Repository.RepositoryInterface;
import Controller.StudentController;
import Utils.Pair;
import View.Console;

public class Main {

    public static void main(String[] args) {
        Validator<Student> studentValidator = new StudentValidator();
        RepositoryInterface<Long, Student> studentRepository = new MemoryRepository<>(studentValidator);
        StudentController studentController = new StudentController(studentRepository);
        Validator<LabProblem> labProblemValidator = new LabProblemValidator();
        RepositoryInterface<Long, LabProblem> labProblemRepository = new MemoryRepository<>(labProblemValidator);
        LabProblemController labProblemController = new LabProblemController(labProblemRepository);
        Validator<Assignment> assignmentValidator = new AssignemtValidator();
        RepositoryInterface<Pair<Long, Long>, Assignment> assignmentRepository = new MemoryRepository<>(assignmentValidator);
        AssignmentController assignmentController = new AssignmentController(assignmentRepository);
        Console console = new Console(studentController, labProblemController, assignmentController);
        console.run();
    }

}
