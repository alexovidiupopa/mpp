package server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.NameValuePair;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.*;

class ServeConnection extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private static List<String> toKill= new ArrayList<>();
    private int votesA, votesB, votesC;
    public ServeConnection(Socket s) throws IOException {
        socket = s;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        votesA = 0;
        votesB=0;
        votesC=0;
    }

    @Override
    public void run() {
        try {
            System.out.println("Serving connection");
            while (true) {

                String purpose=in.readLine();
                if(purpose.equals("kill"))
                {
                    String toKillName=in.readLine();
                    toKill.add(toKillName);
                    return;
                }
                String name = in.readLine();
                String a = in.readLine();
                String b = in.readLine();
                String c = in.readLine();

                if (toKill.contains(name)) {
                    System.out.println("Connection closing");
                    out.println("stop");
                    toKill.remove(name);
                    return;
                }
                System.out.println("Received:");
                System.out.println(name);
                System.out.println(a);
                System.out.println(b);
                System.out.println(c);

                sendRequest(name,a,b,c);
                out.println("not-stop");
                try {
                    Thread.sleep(new Random().nextInt(4000)+2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("IO Exception");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Socket not closed");
            }
        }
    }

    private void sendRequest(String name, String a, String b, String c) throws IOException, InterruptedException {
        int intA = Integer.parseInt(a);
        int intB = Integer.parseInt(b);
        int intC = Integer.parseInt(c);
        if (intA + votesA != votesA || intB + votesB != votesB || intC + votesC != votesC){
            //save
            votesA+=intA;
            votesB+=intB;
            votesC+=intC;
            var values = new HashMap<String, String>() {{
                put("name", name);
                put ("a", String.valueOf(votesA));
                put ("b", String.valueOf(votesB));
                put ("c", String.valueOf(votesC));
                put("nr" , String.valueOf(votesA+votesB+votesC));
            }};

            var objectMapper = new ObjectMapper();
            String requestBody = objectMapper
                    .writeValueAsString(values);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/voting"))
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            System.out.println(response.body());
        }
        else {
            System.out.println("Data not changed");
        }
    }
}

public class Server {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("please read county id:");
        int countyId = Integer.parseInt(scanner.nextLine());
        try (ServerSocket s = new ServerSocket(countyId)) {
            System.out.println("Server ready");
            while (true) {
                Socket socket = s.accept();
                try {
                    ServeConnection sc = new ServeConnection(socket);
                    sc.start();
                } catch (IOException e) {
                    System.err.println("IO Exception");
                }
            }
        }
    }

}
