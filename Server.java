import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.ClassNotFoundException;

public class Server {
    private static int port = 7777;
   
    public static void main(String[] args) throws ClassNotFoundException {
		ServerSocket serverSocket = null;
        boolean listening = true;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("cannot create a server socket");
            System.exit(1);
        }

        while(listening) {
            try {
                System.out.println("the server is waiting for client request ..");
                Socket clientSocket = serverSocket.accept();
                ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
                String message = (String) inputStream.readObject();
                System.out.println("message from client : "+ message);

                Random rand = new Random();
                int newPort = rand.nextInt(9000) + 1024;

                new Thread(new ServerThread(newPort)).start();

                ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                outputStream.writeObject(""+newPort);

                inputStream.close();
                outputStream.close();
                clientSocket.close();
            } catch (IOException ex) {
                System.out.println("error cannot create this connection");
            }
        }
    }
}