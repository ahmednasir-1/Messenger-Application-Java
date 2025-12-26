package sockets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientThread extends Thread{

    Socket socket = null;
    ClientThread(Socket socket)
    {
        this.socket = socket;
    }

    public void run()
    {
        try{
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());

            while(true)
            {
                String type = dis.readUTF();
                if(type == "Signup")
                {
                    ReadDatabase a = new ReadDatabase();
                    User u = new User();
                    u.setName(dis.readUTF());
                    u.setUsername(dis.readUTF());
                    u.setPassword(dis.readUTF());

                    a.saveDataOfUser(u);
                }

                else if(type == "login")
                {
                    ReadDatabase a = new ReadDatabase();
                    User u = new User();
                    String username = dis.readUTF();
                    String password = dis.readUTF();
                    u = a.validateCredentials(username, password);

                }
            }




        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }


}
