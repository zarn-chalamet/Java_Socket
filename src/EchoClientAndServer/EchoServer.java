package EchoClientAndServer;

import java.io.*;
import java.net.*;
public class EchoServer {
    public static void main(String[] args) {
        try {
            System.out.println("Waiting for clients....");
            ServerSocket serverSocket = new ServerSocket(9806);
            Socket socket = serverSocket.accept();
            System.out.println("Connection established");
            BufferedReader messageFromUser = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String str = messageFromUser.readLine();
            PrintWriter pW = new PrintWriter(socket.getOutputStream(),true);
            pW.println("server says "+str);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
