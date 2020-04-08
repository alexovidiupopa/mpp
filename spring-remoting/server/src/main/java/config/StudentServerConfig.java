package config;

import model.Validators.StudentValidator;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;
import repository.StudentRepository;
import service.StudentControllerServer;
import service.StudentControllerInterface;

@Configuration
public class StudentServerConfig implements ApplicationContextAware {

    private static ApplicationContext context;

    public static ApplicationContext getContext() {
        return context;
    }

    @Bean
    RmiServiceExporter rmiStudentServiceExporter() {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceName("StudentController");
        rmiServiceExporter.setServiceInterface(StudentControllerInterface.class);
        rmiServiceExporter.setService(studentController());
        return rmiServiceExporter;
    }

    @Bean
    StudentControllerInterface studentController() {
        StudentRepository studentRepository  = (StudentRepository) context.getBean("studentRepository");
        return new StudentControllerServer(new StudentValidator(), studentRepository);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
