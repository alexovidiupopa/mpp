import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Scanner;

public class MyScanner extends Thread {
    private final String name;
    private final Integer countyId;

    public MyScanner(String name, Integer countyId) {
        this.name = name;
        this.countyId = countyId;
    }

    public static void main(String[] args) {
        MyScanner scanned = readScanner();

        scanned.start();

        try {
            scanned.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        InetAddress address;
        Random random = new Random();
        try {
            address = InetAddress.getByName(null);
        } catch (UnknownHostException e) {
            System.out.println("Unknown host\nTerminating");
            return;
        }
        Boolean good = true;
        int buletinCount = 0;
        try(Socket socket = new Socket(address,countyId)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
            while (good) {
                int a,b,c;
                a = ((int)Math.round(Math.random()));
                b = ((int)Math.round(Math.random()));
                c = ((int)Math.round(Math.random()));
                System.out.println("Sending: " + a + " " + b + " "  + c) ;
                out.println("info");
                out.println(name);
                out.println(a);
                out.println(b);
                out.println(c);
                System.out.println(buletinCount+1 + "-" + name + " votes:" + a + " " + b + " "  + c);
                //String response=in.readLine();
                /*if(response.equals("stop"))
                {
                    return ;
                }*/
                try {

                    Thread.sleep(new Random().nextInt(1000)+3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        } catch (IOException exception) {
            System.out.println("Error with read and write\nTerminating...");
        }

    }

    private static MyScanner readScanner(){

        Scanner scanner = new Scanner(System.in);
        MyScanner scanned = null;
        System.out.println("Enter data for the scanner:");
        try {
            System.out.println("Input scanner name: ");
            String sensorName = scanner.nextLine();
            System.out.println("Input county id: ");
            Integer id = Integer.parseInt(scanner.nextLine());
            scanned = new MyScanner(sensorName,id);

        }
        catch(Exception exception)
        {
            System.out.println(exception);
        }

        return scanned;

    }

}
