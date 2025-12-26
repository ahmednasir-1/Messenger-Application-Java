package chatapp;

import java.io.*;
import java.net.*;

public class ClientHandler extends Thread {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String username;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            while (true) {
                Packet packet = (Packet) in.readObject();

                switch (packet.type) {
                    case "SIGNUP": handleSignup(packet); break;
                    case "LOGIN": handleLogin(packet); break;
                    case "MESSAGE": forwardMessage(packet); break;
                    case "FILE": forwardFile(packet); break;
                    case "LOGOUT": logout(); return;
                }
            }
        } catch (Exception e) {
            logout();
        }
    }

    private void handleSignup(Packet p) throws IOException {
        if (ChatServer.users.containsKey(p.sender)) {
            out.writeObject("Username already exists");
        } else {
            ChatServer.users.put(p.sender,
                    new User(p.sender, (String) p.data));
            out.writeObject("Signup successful");
        }
    }

    private void handleLogin(Packet p) throws IOException {
        User user = ChatServer.users.get(p.sender);

        if (user != null && user.password.equals(p.data)) {
            username = p.sender;
            ChatServer.onlineUsers.put(username, this);
            out.writeObject("Login successful");
            notifyFriends("ONLINE");
        } else {
            out.writeObject("Invalid credentials");
        }
    }

    private void forwardMessage(Packet p) throws IOException {
        ClientHandler receiver =
                ChatServer.onlineUsers.get(p.receiver);
        if (receiver != null) {
            receiver.out.writeObject(p);
        }
    }

    private void forwardFile(Packet p) throws IOException {
        ClientHandler receiver =
                ChatServer.onlineUsers.get(p.receiver);
        if (receiver != null) {
            receiver.out.writeObject(p);
        }
    }

    private void notifyFriends(String status) throws IOException {
        User user = ChatServer.users.get(username);
        for (String friend : user.friends) {
            ClientHandler ch = ChatServer.onlineUsers.get(friend);
            if (ch != null) {
                ch.out.writeObject(
                        new Packet("STATUS", username, friend, status));
            }
        }
    }

    private void logout() {
        try {
            if (username != null) {
                ChatServer.onlineUsers.remove(username);
                notifyFriends("OFFLINE");
            }
            socket.close();
        } catch (Exception ignored) {}
    }
}
