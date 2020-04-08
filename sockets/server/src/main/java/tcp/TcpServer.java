package tcp;

import message.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.UnaryOperator;

public class TcpServer {

    private ExecutorService executorService;
    private Map<String, UnaryOperator<Message>> methodHandlers;

    public TcpServer(ExecutorService executorService) {
        this.methodHandlers = new HashMap<>();
        this.executorService = executorService;
    }

    public void addHandler(String methodName, UnaryOperator<Message> handler) {
        methodHandlers.put(methodName, handler);
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(Message.PORT)) {
            while (true) {
                Socket clientConnection = serverSocket.accept();
                executorService.submit(new ClientHandler(clientConnection));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private class ClientHandler implements Runnable {

        private Socket clientSocket;

        public ClientHandler(Socket client) {
            this.clientSocket = client;
        }

        @Override
        public void run() {
            try (InputStream is = clientSocket.getInputStream(); OutputStream os = clientSocket.getOutputStream()) {
                Message request = new Message();
                request.readFrom(is);
                System.out.println("received request: " + request);
                Message response = methodHandlers.get(request.getHeader()).apply(request);
                response.writeTo(os);
                System.out.println("sent response: " + response);
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

    }

}
