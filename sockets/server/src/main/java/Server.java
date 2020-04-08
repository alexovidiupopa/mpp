import model.Assignment;
import model.LabProblem;
import model.Student;
import model.Validators.AssignmentValidator;
import model.Validators.LabProblemValidator;
import model.Validators.StudentValidator;
import repository.AssignmentJDBCRepository;
import repository.LabProblemJDBCRepository;
import repository.RepositoryInterface;
import repository.StudentJDBCRepository;
import service.*;
import tcp.MessageHandler;
import tcp.TcpServer;
import utils.Pair;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        RepositoryInterface<Long, Student> studentRepository = new StudentJDBCRepository(new StudentValidator(), "./files/credentials/vlad.txt");
        RepositoryInterface<Long, LabProblem> labProblemRepository = new LabProblemJDBCRepository(new LabProblemValidator(), "./files/credentials/vlad.txt");
        RepositoryInterface<Pair<Long, Long>, Assignment> assignmentRepository = new AssignmentJDBCRepository(new AssignmentValidator(), "./files/credentials/vlad.txt");
        StudentController studentController = new StudentController(executorService, studentRepository);
        LabProblemController labProblemController = new LabProblemController(executorService, labProblemRepository);
        AssignmentController assignmentController = new AssignmentController(executorService, assignmentRepository);
        studentController.setAssignmentController(assignmentController);
        labProblemController.setAssignmentController(assignmentController);
        assignmentController.setStudentController(studentController);
        assignmentController.setProblemController(labProblemController);

        MessageHandler messageHandler = new MessageHandler(studentController, labProblemController, assignmentController);
        TcpServer server = new TcpServer(executorService);

        server.addHandler("add student", messageHandler::addStudent);
        server.addHandler("delete student", messageHandler::deleteStudent);
        server.addHandler("update student", messageHandler::updateStudent);
        server.addHandler("get student by id", messageHandler::getStudentById);
        server.addHandler("get all students", messageHandler::getAllStudents);
        server.addHandler("filter students by name", messageHandler::filterStudentsByName);
        server.addHandler("sort students ascending by name", messageHandler::sortStudentsAscendingByName);
        server.addHandler("get students who passed", messageHandler::getStudentsWhoPassed);
        server.addHandler("get student with most problems", messageHandler::getStudentsWithMostProblems);

        server.addHandler("add problem", messageHandler::addProblem);
        server.addHandler("delete problem", messageHandler::deleteProblem);
        server.addHandler("update problem", messageHandler::updateProblem);
        server.addHandler("get problem by id", messageHandler::getProblemById);
        server.addHandler("get all problems", messageHandler::getAllProblems);
        server.addHandler("filter problems by score", messageHandler::filterProblemsByScore);
        server.addHandler("sort problems descending by score", messageHandler::sortProblemsDescendingByScore);
        server.addHandler("get problem assigned most times", messageHandler::getProblemAssignedMostTimes);

        server.addHandler("add assignment", messageHandler::addAssignment);
        server.addHandler("delete assignment", messageHandler::deleteAssignment);
        server.addHandler("update assignment", messageHandler::updateAssignment);
        server.addHandler("get all assignments", messageHandler::getAllAssignments);
        server.addHandler("filter assignments by grade", messageHandler::filterAssignmentsByGrade);
        server.addHandler("sort assignments ascending by id", messageHandler::sortAssignmentsAscendingById);

        System.out.println("Server started");
        server.startServer();
        executorService.shutdown();
        System.out.println("Server stopped");
    }

}