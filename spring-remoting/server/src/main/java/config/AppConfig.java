package config;

import model.Assignment;
import model.LabProblem;
import model.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repository.AssignmentRepository;
import repository.LabProblemRepository;
import repository.RepositoryInterface;
import repository.StudentRepository;
import utils.Pair;

@Configuration
public class AppConfig {

    @Bean
    RepositoryInterface<Long, Student> studentRepository() {
        return new StudentRepository();
    }

    @Bean
    RepositoryInterface<Long, LabProblem> labProblemRepository() {
        return new LabProblemRepository();
    }

    @Bean
    RepositoryInterface<Pair<Long, Long>, Assignment> assignmentRepository(){
        return new AssignmentRepository();
    }
}
