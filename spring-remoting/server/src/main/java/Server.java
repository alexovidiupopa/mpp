import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.AssignmentControllerServer;
import service.LabProblemControllerServer;
import service.StudentControllerServer;

public class Server {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("config");
        StudentControllerServer studentController = (StudentControllerServer) context.getBean("studentController");
        LabProblemControllerServer labProblemController = (LabProblemControllerServer) context.getBean("labProblemController");
        AssignmentControllerServer assignmentController = (AssignmentControllerServer) context.getBean("assignmentController");
        assignmentController.setStudentController(studentController);
        assignmentController.setProblemController(labProblemController);
        studentController.setAssignmentController(assignmentController);
        labProblemController.setAssignmentController(assignmentController);
        System.out.println("\nServer started\n");

        //studentController.getAllStudents().forEach(s -> System.out.println(s.toString()));

    }
}
