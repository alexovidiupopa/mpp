package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import service.LabProblemControllerInterface;

@Configuration
public class LabProblemClientConfig {
    @Bean
    RmiProxyFactoryBean rmiLabProblemProxyFactoryBean(){
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceInterface(LabProblemControllerInterface.class);
        rmiProxyFactoryBean.setServiceUrl("rmi://localhost:1099/LabProblemController");
        return rmiProxyFactoryBean;
    }

}
