import Controller.AssignmentController;
import Controller.LabProblemController;
import Model.Assignment;
import Model.LabProblem;
import Model.Student;
import Model.Validators.AssignmentValidator;
import Model.Validators.LabProblemValidator;
import Model.Validators.StudentValidator;
import Model.Validators.Validator;
import Repository.*;
import Controller.StudentController;
import Utils.Pair;

import View.Console;

public class Main {

    public static void main(String[] args) {
        Validator<Student> studentValidator = new StudentValidator();
        //RepositoryInterface<Long, Student> studentRepository = new MemoryRepository<>(studentValidator);
        RepositoryInterface<Long, Student> studentRepository = new StudentFileRepository(studentValidator, ".\\files\\students.txt");
        StudentController studentController = new StudentController(studentRepository);
        Validator<LabProblem> labProblemValidator = new LabProblemValidator();
        //RepositoryInterface<Long, LabProblem> labProblemRepository = new MemoryRepository<>(labProblemValidator);
        RepositoryInterface<Long, LabProblem> labProblemRepository = new LabProblemFileRepository(labProblemValidator, ".\\files\\problems.txt");
        LabProblemController labProblemController = new LabProblemController(labProblemRepository);
        Validator<Assignment> assignmentValidator = new AssignmentValidator();
        //RepositoryInterface<Pair<Long, Long>, Assignment> assignmentRepository = new MemoryRepository<>(assignmentValidator);
        RepositoryInterface<Pair<Long, Long>, Assignment> assignmentRepository = new AssignmentFileRepository(assignmentValidator, ".\\files\\assignments.txt");
        AssignmentController assignmentController = new AssignmentController(assignmentRepository);
        Console console = new Console(studentController, labProblemController, assignmentController);
        console.run();
    }

}

// TODO
// Delete stud/probl -- doesnt exist - fixed
// Update probl -- deletes problem - fixed
// Add assignments -- doesnt throw error - fixed
// Delete assignments - fixed

