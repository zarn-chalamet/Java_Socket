package java_group_chat_video;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private String username;

    public Client(Socket socket, String username) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.username = username;

            // Send the username to the server upon connection
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage() {
        try {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String messageToSend = scanner.nextLine();
                bufferedWriter.write(username + ": " + messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            System.out.println("Connection to the server has been lost.");
        } finally {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void listenForMessages() {
        new Thread(() -> {
            try {
                String msgFromGroupChat;
                while ((msgFromGroupChat = bufferedReader.readLine()) != null) {
                    System.out.println(msgFromGroupChat);
                }
            } catch (IOException e) {
                System.out.println("Connection to the server has been lost.");
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter your username for the group chat: ");
        String username = keyboard.nextLine();

        try {
            Socket socket = new Socket("localhost", 1234);
            Client client = new Client(socket, username);
            client.listenForMessages();
            client.sendMessage();
        } catch (IOException e) {
            System.out.println("Could not connect to the server.");
        }
    }
}
