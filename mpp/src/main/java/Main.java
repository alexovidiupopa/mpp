
import Controller.LabProblemController;
import Model.LabProblem;
import Model.Student;
import Model.Validators.LabProblemValidator;
import Model.Validators.StudentValidator;
import Model.Validators.Validator;
import Repository.LabProblemFileRepository;
import Repository.MemoryRepository;
import Repository.RepositoryInterface;
import Controller.StudentController;
import Repository.StudentFileRepository;
import View.Console;

public class Main {

    public static void main(String args[]) {
        Validator<Student> studentValidator = new StudentValidator();
        //RepositoryInterface<Long, Student> studentRepository = new MemoryRepository<>(studentValidator);
        MemoryRepository<Long,Student> fileStudentsRepository = new StudentFileRepository(studentValidator,"./files/students.txt");
        StudentController studentController = new StudentController(fileStudentsRepository);
        Validator<LabProblem> labProblemValidator = new LabProblemValidator();
        //RepositoryInterface<Long, LabProblem> labProblemRepository = new MemoryRepository<>(labProblemValidator);
        MemoryRepository<Long, LabProblem> fileLabProblemsRepository = new LabProblemFileRepository(labProblemValidator, "./files/problems.txt");
        LabProblemController labProblemController = new LabProblemController(fileLabProblemsRepository);
        Console console = new Console(studentController, labProblemController);
        console.runConsole();
    }

}
