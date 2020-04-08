import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.AssignmentControllerInterface;
import service.LabProblemControllerInterface;
import service.StudentControllerInterface;
import ui.Console;

import java.util.Arrays;

public class Client {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(
                    "config"
            );
        Arrays.asList(context.getBeanDefinitionNames()).forEach(e -> System.out.println(e));
        StudentControllerInterface studentController = context.getBean(StudentControllerInterface.class);
        LabProblemControllerInterface labProblemController = context.getBean(LabProblemControllerInterface.class);
        AssignmentControllerInterface assignmentController = context.getBean(AssignmentControllerInterface.class);


        Console console = new Console(studentController,labProblemController,assignmentController);
        console.run();
    }
}
