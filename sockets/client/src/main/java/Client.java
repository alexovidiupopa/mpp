import model.LabProblem;
import service.*;
import tcp.TcpClient;
import ui.UserInterface;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    public static void main(String[] args) {
        System.out.println("Client started the process");

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        TcpClient tcpClient = new TcpClient();
        AssignmentControllerInterface assignmentController = new AssignmentController(executorService,tcpClient);
        StudentControllerInterface studentController = new StudentController(executorService,tcpClient);
        LabProblemControllerInterface labProblemController = new LabProblemController(executorService,tcpClient);

        ExecutorService executorServiceUi = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        UserInterface ui = new UserInterface(executorServiceUi, studentController,labProblemController,assignmentController);
        ui.run();

        executorService.shutdown();
        executorServiceUi.shutdown();
        System.out.println("Client ended the process.");
    }
}
