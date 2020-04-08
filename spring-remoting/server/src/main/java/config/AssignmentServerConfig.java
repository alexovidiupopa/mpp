package config;

import model.Validators.AssignmentValidator;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;
import repository.AssignmentRepository;
import service.AssignmentControllerServer;
import service.AssignmentControllerInterface;

@Configuration
public class AssignmentServerConfig implements ApplicationContextAware {

    private static ApplicationContext context;

    public static ApplicationContext getContext() {
        return context;
    }

    @Bean
    RmiServiceExporter rmiAssignmentsServiceExporter() {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceName("AssignmentController");
        rmiServiceExporter.setServiceInterface(AssignmentControllerInterface.class);
        rmiServiceExporter.setService(assignmentController());
        return rmiServiceExporter;
    }

    @Bean
    AssignmentControllerInterface assignmentController() {
        AssignmentRepository assignmentRepository  = (AssignmentRepository) context.getBean("assignmentRepository");
        return new AssignmentControllerServer(new AssignmentValidator(), assignmentRepository);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
