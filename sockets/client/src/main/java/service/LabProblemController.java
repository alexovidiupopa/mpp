package service;

import message.Message;
import model.Exceptions.MyException;
import model.Exceptions.SocketException;
import model.LabProblem;
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

public class LabProblemController implements LabProblemControllerInterface {
    private ExecutorService executorService;
    private TcpClient tcpClient;

    public LabProblemController(ExecutorService executorService, TcpClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    @Override
    public CompletableFuture<Boolean> addProblem(LabProblem problem) throws MyException {
        Message message = new Message("add problem", Factory.problemToString(problem));

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
    public CompletableFuture<Boolean> deleteProblem(LabProblem problem) throws MyException {
        Message message = new Message("delete problem", Factory.problemToString(problem));

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
    public CompletableFuture<Boolean> updateProblem(LabProblem problem) throws MyException {
        Message message = new Message("update problem", Factory.problemToString(problem));

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
    public CompletableFuture<LabProblem> getProblemById(long id)   {
        Message message = new Message("get problem by id", String.valueOf(id));
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
                    ()->null,
                    executorService
            );
        //return executorService.submit(() -> Factory.messageToProblem(body));
        return CompletableFuture.supplyAsync(
                ()->Factory.messageToProblem(body),
                executorService
        );

    }

    @Override
    public CompletableFuture<Set<LabProblem>> getAllProblems()   {
        Message message = new Message("get all problems", "");
        Message response = null;
        try {
            response = tcpClient.sendAndReceive(message);
        } catch (SocketException e) {
            throw new RuntimeException();
        }
        String body = response.getBody();
        String[] tokens = body.split(System.lineSeparator());
        Set<LabProblem> problems = Arrays.stream(tokens).map(Factory::messageToProblem).collect(Collectors.toSet());
        //return executorService.submit(() -> problems);
        return CompletableFuture.supplyAsync(
                ()->problems,
                executorService
        );
    }

    @Override
    public CompletableFuture<Set<LabProblem>> filterProblemsByScore(int minScore)   {
        Message message = new Message("filter problems by score", String.valueOf(minScore));
        Message response = null;
        try {
            response = tcpClient.sendAndReceive(message);
        } catch (SocketException e) {
            throw new RuntimeException();
        }
        String body = response.getBody();
        String[] tokens = body.split(System.lineSeparator());
        Set<LabProblem> problems = Arrays.stream(tokens).map(Factory::messageToProblem).collect(Collectors.toSet());
        //return executorService.submit(() -> problems);
        return CompletableFuture.supplyAsync(
                ()->problems,
                executorService
        );
    }

    @Override
    public CompletableFuture<List<LabProblem>> sortProblemsDescendingByScore()   {
        Message message = new Message("sort problems descending by score", "");
        Message response = null;
        try {
            response = tcpClient.sendAndReceive(message);
        } catch (SocketException e) {
            throw new RuntimeException();
        }
        String body = response.getBody();
        String[] tokens = body.split(System.lineSeparator());
        List<LabProblem> problems = Arrays.stream(tokens).map(Factory::messageToProblem).collect(Collectors.toList());
        //return executorService.submit(() -> problems);
        return CompletableFuture.supplyAsync(
                ()->problems,
                executorService
        );

    }

    @Override
    public CompletableFuture<LabProblem> getProblemAssignedMostTimes()   {
        Message message = new Message("get problem assigned most times", "");
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
                    ()->null,
                    executorService
            );
        //return executorService.submit(() -> Factory.messageToProblem(body));
        return CompletableFuture.supplyAsync(
                ()->Factory.messageToProblem(body),
                executorService
        );
    }
}