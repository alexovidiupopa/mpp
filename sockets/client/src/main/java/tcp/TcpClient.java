package tcp;


import message.Message;
import model.Exceptions.SocketException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by alex.
 */
public class TcpClient {
    public Message sendAndReceive(Message request) throws SocketException {
        try (Socket socket = new Socket(Message.HOST, Message.PORT)) {
            try (InputStream is = socket.getInputStream()) {
                try (OutputStream os = socket.getOutputStream()) {
                    //System.out.println("sendAndReceive - sending request: " + request);
                    request.writeTo(os);
                    //System.out.println("sendAndReceive - received response: ");
                    Message response = new Message();
                    response.readFrom(is);
                    //System.out.println(response);
                    return response;
                }
            }
        } catch (IOException e) {
            throw new SocketException("error when connecting to server " + e.getMessage());
        }
    }
}
