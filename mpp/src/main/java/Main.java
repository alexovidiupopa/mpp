
import Model.Student;
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
//         StudentService studentService = new StudentService(studentRepository);
//         Console console = new Console(studentService);
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
        StudentController studentService = new StudentController(studentRepository);
        Console console = new Console(studentService);
        console.runConsole();

        System.out.println("Hello World!");
    }
}
