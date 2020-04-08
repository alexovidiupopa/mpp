package config;

import model.Validators.LabProblemValidator;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;
import repository.LabProblemRepository;
import service.LabProblemControllerServer;
import service.LabProblemControllerInterface;

@Configuration
public class LabProblemServerConfig implements ApplicationContextAware {

    private static ApplicationContext context;

    public static ApplicationContext getContext() {
        return context;
    }

    @Bean
    RmiServiceExporter rmiLabProblemServiceExporter() {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceName("LabProblemController");
        rmiServiceExporter.setServiceInterface(LabProblemControllerInterface.class);
        rmiServiceExporter.setService(labProblemController());
        return rmiServiceExporter;
    }

    @Bean
    LabProblemControllerInterface labProblemController() {
        LabProblemRepository labProblemRepository  = (LabProblemRepository) context.getBean("labProblemRepository");
        return new LabProblemControllerServer(new LabProblemValidator(), labProblemRepository);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
