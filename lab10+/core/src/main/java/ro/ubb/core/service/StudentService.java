package ro.ubb.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.core.model.Assignment;
import ro.ubb.core.model.Exceptions.MyException;
import ro.ubb.core.model.Exceptions.RepositoryException;
import ro.ubb.core.model.Student;
import ro.ubb.core.model.Validators.StudentValidator;
import ro.ubb.core.repository.StudentRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StudentService implements IStudentService{
    public static final Logger log = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    private StudentValidator validator;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private IAssignmentService assignmentService;


    @Override
    @Transactional
    public void addStudent(Student student) throws MyException {
        log.trace("addStudent - method entered student={}",student);
        validator.validate(student);
        if (studentRepository.existsById(student.getId()))
            throw new RepositoryException("Student already exists");
        Student std = studentRepository.save(student);
        log.trace("addStudent - method finished student={}", std);
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) throws MyException {
        log.trace("deleteStudent - method entered={}",id);
        if (!studentRepository.existsById(id))
            throw new RepositoryException("Student doesn't exist");
        studentRepository.delete(studentRepository.getOne(id));
        log.trace("deleteStudent - method finished");
    }

    @Override
    @Transactional
    public void updateStudent(Student student) throws MyException {
        log.trace("updateStudent - method entered: student={}", student);
        validator.validate(student);
        if (!studentRepository.existsById(student.getId()))
            throw new RepositoryException("Student doesn't exist");
        studentRepository.findById(student.getId())
                .ifPresent(s -> {
                    s.setName(student.getName());
                    s.setGroupNumber(student.getGroupNumber());
                    log.debug("updateStudent - updated: s={}", s);
                });
        log.trace("updateStudent - method finished");
    }

    @Override
    public Student getStudentById(long id) throws MyException {
        log.trace("getStudentById - method entered={}", id);
        return studentRepository.findById(id).get();
    }

    @Override
    public List<Student> getAllStudents() {
        log.trace("getAllStudents - method entered");
        return studentRepository.findAll();

    }

    @Override
    public List<Student> filterStudentsByName(String s) {
        log.trace("filterStudentsByName - method entered s={}",s);
        return studentRepository.findAll()
                .stream()
                .filter(student -> student.getName().contains(s))
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> sortStudentsAscendingByName() {
        log.trace("sortStudentsAscendingByName - method entered");
        Iterable<Student> students = studentRepository.findAll(Sort.by("name").ascending());
        return StreamSupport.stream(students.spliterator(),false)
                .collect(Collectors.toList());
    }

    @Override
    public Set<Student> getStudentsWhoPassed() {
        log.trace("getStudentsWhoPassed - method entered");
        List<Assignment> assignments = assignmentService.getAllAssignments();
        return assignments.stream()
                .filter(assignment -> assignment.getGrade()!=null && assignment.getGrade()>=5.0)
                .map(assignment -> {
                    try {
                        return this.getStudentById(assignment.getId().getFirst());
                    } catch (MyException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .collect(Collectors.toSet());
    }

    @Override
    public Student getStudentsWithMostProblems() throws MyException {
        log.trace("getStudentsWithMostProblems - method entered");
        List<Assignment> assignments = assignmentService.getAllAssignments();
        Map<Long, Long> countForId = assignments.stream()
                .collect(Collectors.groupingBy(assignment -> assignment.getId().getFirst(), Collectors.counting()));
        Map<Long, Long> sortedByValue = countForId.entrySet()
                .stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        Optional<Map.Entry<Long, Long>> student = sortedByValue.entrySet().stream().findFirst();
        return this.getStudentById(student.get().getKey());
    }
}
