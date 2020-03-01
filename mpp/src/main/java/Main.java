
import Controller.LabProblemController;
import Model.LabProblem;
import Model.Student;
import Model.Validators.LabProblemValidator;
import Model.Validators.StudentValidator;
import Model.Validators.Validator;
import Repository.MemoryRepository;
import Repository.RepositoryInterface;
import Controller.StudentController;
import View.Console;

public class Main {
    public static void main(String args[]) {
        //in-memory repo
//         Validator<Student> studentValidator = new StudentValidator();
//         Repository<Long, Student> studentRepository = new InMemoryRepository<>(studentValidator);
//         StudentService studentController = new StudentService(studentRepository);
//         Console console = new Console(studentController);
//         console.runConsole();

        //file repo
//        try {
//            System.out.println(new File(".").getCanonicalPath());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //in file repo
        Validator<Student> studentValidator = new StudentValidator();
        RepositoryInterface<Long, Student> studentRepository = new MemoryRepository<>(studentValidator);
        StudentController studentController = new StudentController(studentRepository);
        Validator<LabProblem> labProblemValidator = new LabProblemValidator();
        RepositoryInterface<Long, LabProblem> labProblemRepository = new MemoryRepository<>(labProblemValidator);
        LabProblemController labProblemController = new LabProblemController(labProblemRepository);
        Console console = new Console(studentController, labProblemController);
        console.runConsole();
    }
}
