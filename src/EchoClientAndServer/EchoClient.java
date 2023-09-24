package EchoClientAndServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoClient {
    public static void main(String[] args) {
        try {
            System.out.println("Client started");
            Socket socket = new Socket("localhost",9806);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            String input = userInput.readLine();
            PrintWriter request = new PrintWriter(socket.getOutputStream(),true);
            request.println(input);
            BufferedReader bR = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println(bR.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
