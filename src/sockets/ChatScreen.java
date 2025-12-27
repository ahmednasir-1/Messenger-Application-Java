package sockets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatScreen extends JFrame {

    String[] list = {"ahmed", "usman"};
    JComboBox<String> onlineUsers = new JComboBox<>(list);
    JLabel send = new JLabel("Sender: ");
    JLabel receiver = new JLabel("Receiver");
    JButton sendButton = new JButton("Send");
    JButton receiveButton = new JButton("Receive");
    JTextField sendTextField = new JTextField(20);
    JTextField receiveTextField = new JTextField(20);
    JButton logoutButton = new JButton("Logout");
    String selected;
    JPanel jp = new JPanel();

//    String[] allUsersList = {"ahmed", "usman"};
    JComboBox<String> friendRequest = new JComboBox<>(list);
    String selectFriend;
    JButton sendRequestButton = new JButton("Send Request");
    JButton acceptButton = new JButton("Accept");
    JButton declineButton = new JButton("Decline");

    ChatScreen() {
        setLayout(new FlowLayout());
        setSize(800, 200);
        setTitle("Chat Screen");

        jp.add(onlineUsers);

        onlineUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                selected = (String) onlineUsers.getSelectedItem();
                System.out.println("you selected "+ selected);
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

                System.out.println(selected + "a sender");
                Client.sendMessage(sendTextField.getText(), selected);
                sendTextField.setText("");

            }
        });


        jp.add(friendRequest);
        friendRequest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectFriend = (String) friendRequest.getSelectedItem();
                Client.sendRequest(selectFriend);
            }
        });

        jp.add(sendRequestButton);

        add(jp);
        setVisible(true);
    }

    public  void displayMessage(String sender, String text)
    {
        receiver.setText(sender);
        receiveTextField.setText(text);
    }

    public void friendRequestDialogBox(String sender)
    {
        JOptionPane.showMessageDialog(null, sender + " wants to send you a friend request.");
        jp.add(acceptButton);
        jp.add(declineButton);
    }

    public static void main() {
        new ChatScreen();
    }

}
