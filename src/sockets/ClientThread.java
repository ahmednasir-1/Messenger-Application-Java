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

                        broadcastOnlineUsers();
                    } else {
                        dos.writeUTF("Login_Failed");
                    }


                }
                else if(type.equals("Logout"))
                {
                    String user = dis.readUTF();
                    Server.onlineUsers.remove(user);

                    broadcastOnlineUsers();

                }else if (type.equals("Message")) {
                    String receiver = dis.readUTF();
                    String message = dis.readUTF();

                    // Check if receiver is online from the onlineUsers hashmap

                    System.out.println("to:" + receiver + " mess:" + message);
                    ClientThread receiverThread =
                            Server.onlineUsers.get(receiver);

                    if (receiverThread != null) {
                        // Forward message
                        System.out.println("forwarded");
                        receiverThread.dos.writeUTF("rmessage");
                        receiverThread.dos.writeUTF(username);
                        receiverThread.dos.writeUTF(message);
                        receiverThread.dos.flush();
                    }
                } else if (type.equalsIgnoreCase("FriendRequest")) {

                    String receiver = dis.readUTF();

                    ClientThread receiverThread = Server.onlineUsers.get(receiver);
                    if (receiverThread != null) {
                        receiverThread.dos.writeUTF("request");
                        receiverThread.dos.writeUTF(username);
//                        dos.writeUTF(sender);

                    }

                } else if (type.equals("File")) {

                    String receiver = dis.readUTF();
                    String sender = dis.readUTF();
                    String fileName = dis.readUTF();
                    long fileSize = dis.readLong();

                    ClientThread receiverThread = Server.onlineUsers.get(receiver);
                    if (receiverThread != null) {
                        receiverThread.dos.writeUTF("File");
                        receiverThread.dos.writeUTF(sender);
//                    dos.writeUTF(receiver);
                        receiverThread.dos.writeUTF(fileName);
                        receiverThread.dos.writeLong(fileSize);

                        byte[] buffer = new byte[4096];
                        long remaining = fileSize;
                        int bytesRead;

                        while (remaining > 0 &&
                                (bytesRead = dis.read(buffer, 0,
                                        (int)Math.min(buffer.length, remaining))) != -1) {

                            receiverThread.dos.write(buffer, 0, bytesRead);
                            remaining -= bytesRead;
                        }

                        System.out.println("file send in client thread route from " + username);

                    }


                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void broadcastOnlineUsers()
    {
        try {

            String[] users = Server.onlineUsers.keySet().toArray(new String[0]);

            for (ClientThread ct : Server.onlineUsers.values()) {
                ct.dos.writeUTF("OnlineUsersList");
                ct.dos.writeInt(users.length);

                for(String user : users)
                {
                    ct.dos.writeUTF(user);
                }

                ct.dos.flush();

            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }


}
