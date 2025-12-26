package chatapp;

import java.net.*;
import java.util.*;

public class ChatServer {

    public static Map<String, User> users =
            Collections.synchronizedMap(new HashMap<>());

    public static Map<String, ClientHandler> onlineUsers =
            Collections.synchronizedMap(new HashMap<>());

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Chat Server started on port 5000...");

        while (true) {
            Socket socket = serverSocket.accept();
            new ClientHandler(socket).start();
        }
    }
}

