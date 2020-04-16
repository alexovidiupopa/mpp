package ro.ubb.spring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ro.ubb.spring.model.Assignment;
import ro.ubb.spring.model.Exceptions.MyException;
import ro.ubb.spring.model.Exceptions.RepositoryException;
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
        log.trace("addAssignment - method entered assignment={}",assignment);
        validator.validate(assignment);
        if (assignmentRepository.existsById(assignment.getId()))
            throw new RepositoryException("Assignment already exists");
        Assignment asg = assignmentRepository.save(assignment);
        log.trace("addAssignment - method finished assignment={}",asg);
    }

    @Override
    @Transactional
    public void deleteAssignment(Assignment assignment) throws MyException {
        log.trace("deleteAssignment - method entered assignment={}",assignment);
        validator.validate(assignment);
        if (!assignmentRepository.existsById(assignment.getId()))
            throw new RepositoryException("Assignment doesn't exist");
        assignmentRepository.delete(assignment);
        log.trace("deleteAssignment - method entered finished");
    }

    @Override
    @Transactional
    public void updateAssignment(Assignment assignment) throws MyException {
        log.trace("updateAssignment - method entered assignment={}",assignment);
        validator.validate(assignment);
        if (!assignmentRepository.existsById(assignment.getId()))
            throw new RepositoryException("Assignment doesn't exist");
        assignmentRepository.findById(assignment.getId())
                .ifPresent(assig -> {
                    assig.setGrade(assignment.getGrade());
                    log.debug("updateAssignment - updated: assig={}",assig);
                });
        log.trace("updateAssignment - method finished",assignment);
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
