package sockets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class Client {

    static DataOutputStream dos;
    DataInputStream dis;
    Client() {
        try {
            Socket client  = new Socket("localhost", 4333);

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
            jFrame.setSize(400,400);
            jFrame.setLayout(new FlowLayout());
            jFrame.add(jp);
            jFrame.setVisible(true);



        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void signup(String name, String username, String password)
    {
        try {
            dos.writeUTF("Signup");
            dos.writeUTF(name);
            dos.writeUTF(username);
            dos.writeUTF(password);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void login(String username, String password)
    {
        try {
            dos.writeUTF("Login");
            dos.writeUTF(username);
            dos.writeUTF(password);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main() {
        new Client();
    }
}
