package ro.ubb.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"ro.ubb.spring.repository", "ro.ubb.spring.service", "ro.ubb.spring.ui","ro.ubb.spring.model.Validators"})
public class LabProblemsConfig {

}
