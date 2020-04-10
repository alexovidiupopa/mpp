package ro.ubb.spring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ro.ubb.spring.model.Assignment;
import ro.ubb.spring.model.Exceptions.MyException;
import ro.ubb.spring.model.Validators.AssignmentValidator;
import ro.ubb.spring.repository.AssignmentRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AssignmentService implements IAssignmentService {

    public static final Logger log = LoggerFactory.getLogger(AssignmentService.class);

    @Autowired
    private AssignmentValidator validator;

    @Autowired
    private AssignmentRepository assignmentRepository;



    @Override
    @Transactional
    public void addAssignment(Assignment assignment) throws MyException {
        log.trace("addStudent - method entered assignment={}",assignment);
        validator.validate(assignment);
        Assignment asg = assignmentRepository.save(assignment);
        log.trace("addStudent - method finished assignment={}",asg);
    }

    @Override
    @Transactional
    public void deleteAssignment(Assignment assignment) throws MyException {

    }

    @Override
    @Transactional
    public void updateAssignment(Assignment assignment) throws MyException {

    }

    @Override
    public List<Assignment> getAllAssignments() {
        log.trace("getAllAssignments - method entered");
        return assignmentRepository.findAll();
    }

    @Override
    public List<Assignment> filterAssignmentsByGrade(double g) {
        log.trace("filterAssignmentsByGrade - method entered g={}",g);
        return assignmentRepository.findAll()
                .stream()
                .filter(assignment -> assignment.getGrade()>=g)
                .collect(Collectors.toList());
    }

    @Override
    public List<Assignment> sortAssignmentsAscendingById() {
        log.trace("sortAssignmentsAscendingById - method entered");
        Iterable<Assignment> assignments = assignmentRepository.findAll(Sort.by("id").ascending());
        return StreamSupport.stream(assignments.spliterator(),false)
                .collect(Collectors.toList());
    }
}
