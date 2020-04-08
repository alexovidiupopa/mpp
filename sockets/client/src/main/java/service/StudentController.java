package service;

import message.Message;
import model.Exceptions.MyException;
import model.Exceptions.SocketException;
import model.Student;
import tcp.TcpClient;
import utils.Factory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class StudentController implements StudentControllerInterface {

    private ExecutorService executorService;
    private TcpClient tcpClient;

    public StudentController(ExecutorService executorService, TcpClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    @Override
    public CompletableFuture<Boolean> addStudent(Student student) throws MyException {
        Message message = new Message("add student", Factory.studentToString(student));
        Message response = tcpClient.sendAndReceive(message);
        String body = response.getBody();
        String header = response.getHeader();
        if (header.equals("error"))
            throw new MyException(body);
        //return executorService.submit(() -> true);
        return CompletableFuture.supplyAsync(
                ()->true,
                executorService
        );
    }

    @Override
    public CompletableFuture<Boolean> deleteStudent(Student student) throws MyException {
        Message message = new Message("delete student", Factory.studentToString(student));
        Message response = tcpClient.sendAndReceive(message);
        String body = response.getBody();
        String header = response.getHeader();
        if (header.equals("error"))
            throw new MyException(body);
        //return executorService.submit(() -> true);
        return CompletableFuture.supplyAsync(
                ()->true,
                executorService
        );
    }

    @Override
    public CompletableFuture<Boolean> updateStudent(Student student) throws MyException {
        Message message = new Message("update student", Factory.studentToString(student));
        Message response = tcpClient.sendAndReceive(message);
        String body = response.getBody();
        String header = response.getHeader();
        if (header.equals("error"))
            throw new MyException(body);
        //return executorService.submit(() -> true);
        return CompletableFuture.supplyAsync(
                ()->true,
                executorService
        );
    }

    @Override
    public CompletableFuture<Student> getStudentById(long id)   {
        Message message = new Message("get student by id", String.valueOf(id));
        Message response = null;
        try {
            response = tcpClient.sendAndReceive(message);
        } catch (SocketException e) {
            throw new RuntimeException();
        }
        String body = response.getBody();
        if (body.equals(""))
            //return executorService.submit(() -> null);
            return CompletableFuture.supplyAsync(
                    ()->Factory.messageToStudent(null),
                    executorService
            );
        //return executorService.submit(() -> Factory.messageToStudent(body));
        return CompletableFuture.supplyAsync(
                ()->Factory.messageToStudent(body),
                executorService
        );
    }

    @Override
    public CompletableFuture<Set<Student>> getAllStudents()   {
        Message message = new Message("get all students", "");
        Message response = null;
        try {
            response = tcpClient.sendAndReceive(message);
        } catch (SocketException e) {
            throw new RuntimeException();
        }
        String body = response.getBody();
        String[] tokens = (body.split(System.lineSeparator()));
        Set<Student> students = Arrays.stream(tokens).map(Factory::messageToStudent).collect(Collectors.toSet());
        //return executorService.submit(() -> students);
        return CompletableFuture.supplyAsync(
                ()-> students,
                executorService
        );
    }

    @Override
    public CompletableFuture<Set<Student>> filterStudentsByName(String s)   {
        Message message = new Message("filter students by name", s);
        Message response = null;
        try {
            response = tcpClient.sendAndReceive(message);
        } catch (SocketException e) {
            throw new RuntimeException();
        }
        String body = response.getBody();
        String[] tokens = body.split(System.lineSeparator());
        Set<Student> students = Arrays.stream(tokens).map(Factory::messageToStudent).collect(Collectors.toSet());
        //return executorService.submit(() -> students);
        return CompletableFuture.supplyAsync(
                ()->students,
                executorService
        );
    }

    @Override
    public CompletableFuture<List<Student>> sortStudentsAscendingByName()   {
        Message message = new Message("sort students ascending by name", "");
        Message response = null;
        try {
            response = tcpClient.sendAndReceive(message);
        } catch (SocketException e) {
            throw new RuntimeException();
        }
        String body = response.getBody();
        String[] tokens = body.split(System.lineSeparator());
        List<Student> students = Arrays.stream(tokens).map(Factory::messageToStudent).collect(Collectors.toList());
        //return executorService.submit(() -> students);
        return CompletableFuture.supplyAsync(
                ()->students,
                executorService
        );
    }

    @Override
    public CompletableFuture<Set<Student>> getStudentsWhoPassed()   {
        Message message = new Message("get students who passed", "");
        Message response = null;
        try {
            response = tcpClient.sendAndReceive(message);
        } catch (SocketException e) {
            throw new RuntimeException();
        }
        String body = response.getBody();
        String[] tokens = body.split(System.lineSeparator());
        Set<Student> students = Arrays.stream(tokens).map(Factory::messageToStudent).collect(Collectors.toSet());
        //return executorService.submit(() -> students);
        return CompletableFuture.supplyAsync(
                ()->students,
                executorService
        );
    }

    @Override
    public CompletableFuture<Student> getStudentsWithMostProblems()   {
        Message message = new Message("get student with most problems", "");
        Message response = null;
        try {
            response = tcpClient.sendAndReceive(message);
        } catch (SocketException e) {
            throw new RuntimeException();
        }
        String body = response.getBody();
        if (body.equals(""))
            //return executorService.submit(() -> null);
            return CompletableFuture.supplyAsync(
                    ()->Factory.messageToStudent(null),
                    executorService
            );
        //return executorService.submit(() -> Factory.messageToStudent(body));
        return CompletableFuture.supplyAsync(
                ()->Factory.messageToStudent(body),
                executorService
        );

    }
}