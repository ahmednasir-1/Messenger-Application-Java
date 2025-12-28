package sockets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Client {

    static DataOutputStream dos;
    static DataInputStream dis;

    Client() {
        try {
            Socket client = new Socket("localhost", 4333);

            dos = new DataOutputStream(client.getOutputStream());
            dis = new DataInputStream(client.getInputStream());

            JFrame jFrame = new JFrame();
            JButton signUpButton = new JButton("Signup");
            JButton loginButton = new JButton("Login ");
            JPanel jp = new JPanel();


            signUpButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new SignupScreen();
                }
            });

            loginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new LoginScreen();
                }
            });


            jp.add(loginButton);
            jp.add(signUpButton);
            jFrame.setTitle("Messenger Application");
            jFrame.setSize(400, 400);
            jFrame.setLayout(new FlowLayout());
            jFrame.add(jp);
            jFrame.setVisible(true);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void signup(String name, String username, String password) {
        try {
            dos.writeUTF("Signup");
            dos.writeUTF(name);
            dos.writeUTF(username);
            dos.writeUTF(password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean login(String username, String password) {
        String response = "";
        try {
            dos.writeUTF("Login");
            dos.writeUTF(username);
            dos.writeUTF(password);
            dos.flush();

            System.out.println(username + " , " + password + " sent");
            response = dis.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.equals("Login_Success");
    }

    public static void sendMessage(String text, String receiver) {
        try {
            dos.writeUTF("Message");
            dos.writeUTF(receiver);
            dos.writeUTF(text);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendFile(String receiver, String sender, File file) {
        try {
            dos.writeUTF("File");
            dos.writeUTF(receiver);
            dos.writeUTF(sender);
            dos.writeUTF(file.getName());

            long fileSize = file.length();
            dos.writeLong(fileSize);

            FileInputStream fis = new FileInputStream(file);

            // sending file in the form of 4KB chunks
            byte[] buffer = new byte[4096];

            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                dos.write(buffer, 0, bytesRead);
            }

            dos.flush();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendRequest(String toWhom) {
        try {
            dos.writeUTF("FriendRequest");
            dos.writeUTF(toWhom);
//            dos.writeUTF(user);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void startListener(ChatScreen chat) {

        System.out.println("always listening");
        new Thread(() ->
        {
            try {

                while (true) {
                    String command = dis.readUTF();

                    System.out.println(command + "command in listener thread");
                    if (command.equalsIgnoreCase("rmessage")) {
                        String sender = dis.readUTF();
                        String message = dis.readUTF();

                        SwingUtilities.invokeLater(() -> {
                            chat.displayMessage(sender, message);
                        });
                    }

                    if (command.equalsIgnoreCase("FriendRequest")) {
                        String s = dis.readUTF();
                        String from = dis.readUTF();

                        SwingUtilities.invokeLater(() -> {
                            chat.friendRequestDialogBox(s, from);
                        });
                    }


                    if (command.equalsIgnoreCase("FILE")) {

                        String from = dis.readUTF();      // sender username
                        String fileName = dis.readUTF();
                        long fileSize = dis.readLong();

                        Path saveDir = Paths.get("downloads", from);
                        Files.createDirectories(saveDir);

                        Path filePath = saveDir.resolve(
                                System.currentTimeMillis() + "_" + fileName);

                        FileOutputStream fos = new FileOutputStream(filePath.toFile());

                        byte[] buffer = new byte[4096];
                        long remaining = fileSize;
                        int bytesRead;

                        while (remaining > 0 &&
                                (bytesRead = dis.read(buffer, 0,
                                        (int)Math.min(buffer.length, remaining))) != -1) {

                            fos.write(buffer, 0, bytesRead);
                            remaining -= bytesRead;
                        }

                        fos.close();

                        System.out.println("File received from " + from +
                                " → " + filePath);
                    }


                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public static void main() {
        new Client();
    }
}
