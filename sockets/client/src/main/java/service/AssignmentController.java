package service;


import message.Message;
import model.Assignment;
import model.Exceptions.MyException;
import model.Exceptions.SocketException;
import model.Student;
import tcp.TcpClient;
import utils.Factory;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class AssignmentController implements AssignmentControllerInterface {

    private ExecutorService executorService;
    private TcpClient tcpClient;

    public AssignmentController(ExecutorService executorService, TcpClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    @Override
    public CompletableFuture<Boolean> addAssignment(Assignment assignment) throws MyException {
        Message message = new Message("add assignment", Factory.assignmentToString(assignment));
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
    public CompletableFuture<Boolean> deleteAssignment(Assignment assignment) throws MyException {
        Message message = new Message("delete assignment", Factory.assignmentToString(assignment));
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
    public CompletableFuture<Boolean> updateAssignment(Assignment assignment) throws MyException {
        Message message = new Message("update assignment", Factory.assignmentToString(assignment));
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
    public CompletableFuture<Set<Assignment>> getAllAssignments()  {
        Message message = new Message("get all assignments", "");
        Message response = null;
        try {
            response = tcpClient.sendAndReceive(message);
        } catch (SocketException e) {
            throw new RuntimeException();
        }
        String body = response.getBody();
        String[] tokens = body.split(System.lineSeparator());
        Set<Assignment> assignments = Arrays.stream(tokens).map(Factory::messageToAssignment).collect(Collectors.toSet());
        //return executorService.submit(() -> assignments);
        return CompletableFuture.supplyAsync(
                ()->assignments,
                executorService
        );
    }

    @Override
    public CompletableFuture<Set<Assignment>> filterAssignmentsByGrade(double g)  {
        Message message = new Message("filter assignments by grade", String.valueOf(g));
        Message response = null;
        try {
            response = tcpClient.sendAndReceive(message);
        } catch (SocketException e) {
            throw new RuntimeException();
        }
        String body = response.getBody();
        String[] tokens = body.split(System.lineSeparator());
        Set<Assignment> assignments = Arrays.stream(tokens).map(Factory::messageToAssignment).collect(Collectors.toSet());
        //return executorService.submit(() -> assignments);
        return CompletableFuture.supplyAsync(
                ()->assignments,
                executorService
        );
    }

    @Override
    public CompletableFuture<List<Assignment>> sortAssignmentsAscendingById() {
        Message message = new Message("sort assignments ascending by id", "");
        Message response = null;
        try {
            response = tcpClient.sendAndReceive(message);
        } catch (SocketException e) {
            throw new RuntimeException();
        }
        String body = response.getBody();
        String[] tokens = body.split(System.lineSeparator());
        List<Assignment> assignments = Arrays.stream(tokens).map(Factory::messageToAssignment).collect(Collectors.toList());
        //return executorService.submit(() -> assignments);
        return CompletableFuture.supplyAsync(
                ()->assignments,
                executorService
        );
    }
}