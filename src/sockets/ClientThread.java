package sockets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

//import static sockets.Client.dos;

public class ClientThread extends Thread {

    Socket socket = null;

    private String username;
    private DataInputStream dis;
    private DataOutputStream dos;

    ClientThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
             dos = new DataOutputStream(socket.getOutputStream());
             dis = new DataInputStream(socket.getInputStream());

            while (true) {
                String type = dis.readUTF();
                if (type.equals("Signup")) {
                    ReadDatabase a = new ReadDatabase();
                    User u = new User();
                    u.setName(dis.readUTF());
                    u.setUsername(dis.readUTF());
                    u.setPassword(dis.readUTF());

                    a.saveDataOfUser(u);
                } else if (type.equals("Login")) {
                    // when login command appears
                    ReadDatabase a = new ReadDatabase();
                    User u = new User();

                    // we take its username and put in onlineUsers hashmap so that
                    // we know its online
                    username = dis.readUTF();
                    String password = dis.readUTF();

                    System.out.println(username + password);

                    // validating its credentials
                    boolean isValid;
                    isValid = a.validateCredentials(username, password);

                    if (isValid) {
                        Server.onlineUsers.put(username, this);
                        dos.writeUTF("Login_Success");
                    } else {
                        dos.writeUTF("Login_Failed");
                    }


                } else if (type.equals("Message")) {
                    String receiver = dis.readUTF();
                    String message = dis.readUTF();

                    // Check if receiver is online from the onlineUsers hashmap

                    System.out.println("to:" + receiver + " mess:" + message);
                    ClientThread receiverThread =
                            Server.onlineUsers.get(receiver);

                    if (receiverThread != null) {
                        // Forward message
                        System.out.println("forwarded");
                        receiverThread.sendMessage(username, message);
                    }
                } else if (type.equalsIgnoreCase("FriendRequest")) {

                    String sender = dis.readUTF();
                    dos.writeUTF("FriendRequest");
                    dos.writeUTF(sender);

                }


            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String sender, String message) {

        try {
            dos.writeUTF("rmessage");
            dos.writeUTF(sender);
            dos.writeUTF(message);
            dos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
