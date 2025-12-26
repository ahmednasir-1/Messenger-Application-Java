package sockets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatScreen extends JFrame {

    String[] list = {"Ahmed", "Ayesha", "Ali"};
    JComboBox<String> onlineUsers = new JComboBox<>(list);
    JLabel send = new JLabel("Sender: ");
    JLabel receiver = new JLabel("Receiver");
    JButton sendButton = new JButton("Send");
    JButton receiveButton = new JButton("Receive");
    JTextField sendTextField = new JTextField(20);
    JTextField receiveTextField = new JTextField(20);
    JButton logoutButton = new JButton("Logout");
    String selected;

    ChatScreen()
    {
        JPanel jp = new JPanel();
        setLayout(new FlowLayout());
        setSize(200,200);

        jp.add(onlineUsers);

        onlineUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selected = (String) onlineUsers.getSelectedItem();
            }
        });

        jp.add(send);
        jp.add(sendTextField);
        jp.add(receiver);
        jp.add(receiveTextField);



        jp.add(sendButton);
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
    }

}
