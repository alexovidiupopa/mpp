package service;

import model.Exceptions.MyException;
import model.Student;
import model.Validators.Validator;
import repository.RepositoryInterface;
import repository.StudentRepository;
import utils.Sort;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class StudentControllerServer implements StudentControllerInterface {

    Validator<Student> validator;
    RepositoryInterface<Long, Student> repository;
    AssignmentControllerInterface assignmentController;

    public StudentControllerServer(Validator<Student> studentValidator, StudentRepository studentRepository) {
        this.validator=  studentValidator;
        this.repository = studentRepository;
    }

    public void setAssignmentController(AssignmentControllerInterface assignmentController) {
        this.assignmentController = assignmentController;
    }

    @Override
    public void addStudent(Student student) throws MyException {
        this.validator.validate(student);
        if(this.repository.add(student).isPresent())
            throw new MyException("Student already exists");
    }

    @Override
    public void deleteStudent(Student student) throws MyException {
        this.validator.validate(student);
        if(!this.repository.delete(student.getId()).isPresent())
            throw new MyException("Student does not exist");
    }

    @Override
    public void updateStudent(Student student) throws MyException {
        this.validator.validate(student);
        if(this.repository.update(student).isPresent())
            throw new MyException("Student does not exist");
    }

    @Override
    public Student getStudentById(long id) throws MyException {
        Optional<Student> result = this.repository.findById(id);
        if(result.isPresent())
            return result.get();
        else
            throw new MyException("Student does not exist");
    }

    @Override
    public Set<Student> getAllStudents() {
        return StreamSupport.stream(this.repository.getAll().spliterator(), false)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Student> filterStudentsByName(String s) {
        return StreamSupport.stream(this.repository.getAll().spliterator(), false)
                .filter(student -> student.getName().contains(s))
                .collect(Collectors.toSet());
    }

    @Override
    public List<Student> sortStudentsAscendingByName() {
        return StreamSupport.stream(this.repository.getAll(new Sort("name")).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Set<Student> getStudentsWhoPassed() {
        return assignmentController.getAllAssignments().
            stream()
            .filter(assignment -> assignment.getGrade() != null && assignment.getGrade() >= 5.0)
            .map(assignment -> {
                try {
                    return this.getStudentById(assignment.getId().getFirst());
                } catch (MyException e) {
                    throw new RuntimeException(e.getCause());
                }
            })
            .collect(Collectors.toSet());
    }

    @Override
    public Student getStudentsWithMostProblems() throws MyException {
        Map<Long, Long> countForId = assignmentController.getAllAssignments()
                .stream()
                .collect(Collectors.groupingBy(assignment -> assignment.getId().getFirst(), Collectors.counting()));
        Map<Long, Long> sortedByValue = countForId.entrySet()
                .stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        Optional<Map.Entry<Long, Long>> student = sortedByValue.entrySet().stream().findFirst();
        if (!student.isPresent())
            throw new MyException("No student found");
        return this.getStudentById(student.get().getKey());
    }
}
