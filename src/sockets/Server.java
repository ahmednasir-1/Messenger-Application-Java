package sockets;

import chatapp.ClientHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {


    public static Map<String, ClientThread> onlineUsers =
            new ConcurrentHashMap<>();



    public static void main() {
        try {
            ServerSocket server = new ServerSocket(4333);

            while (true) {
                System.out.println("Waiting for clients");
                Socket socket = server.accept();
                System.out.println("Connection Established");
                new ClientThread(socket).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}


