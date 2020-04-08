package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import service.StudentControllerInterface;

@Configuration
public class StudentClientConfig {
    @Bean
    RmiProxyFactoryBean rmiStudentProxyFactoryBean(){
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceInterface(StudentControllerInterface.class);
        rmiProxyFactoryBean.setServiceUrl("rmi://localhost:1099/StudentController");
        return rmiProxyFactoryBean;
    }

}