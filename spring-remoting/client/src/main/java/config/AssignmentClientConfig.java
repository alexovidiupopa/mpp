package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import service.AssignmentControllerInterface;

@Configuration
public class AssignmentClientConfig {
    @Bean
    RmiProxyFactoryBean rmiAssignmentProxyFactoryBean(){
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceInterface(AssignmentControllerInterface.class);
        rmiProxyFactoryBean.setServiceUrl("rmi://localhost:1099/AssignmentController");
        return rmiProxyFactoryBean;
    }


}
