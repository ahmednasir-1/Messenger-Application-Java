package sockets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ChatScreen extends JFrame {

    // online users list
    String[] list  = {""};

    JComboBox<String> onlineUsers = new JComboBox<>(list);
    JLabel send = new JLabel();
    JLabel receiver = new JLabel("Receiver");
    JButton sendMessageButton = new JButton("Send Message");

    JTextField sendMessageTextField = new JTextField(20);
    JTextField receiveMessageTextField = new JTextField(20);
    JButton logoutButton = new JButton("Logout");
    String selected;
    JPanel panel = new JPanel();

    //    String[] allUsersList = {"ahmed", "usman"};
//    JComboBox<String> friendsList = new JComboBox<>(list);
    String selectFriend;
    JButton sendRequestButton = new JButton("Send Request");
    JButton acceptButton = new JButton("Accept");
    JButton declineButton = new JButton("Decline");
    JButton sendFileButton = new JButton("Send File");


    // chat screen calls from loginScreen, so when user logins we also want the user  username's
    ChatScreen(String username) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        setSize(500, 300);
        setTitle(username + " Chat Screen");

        onlineUsers.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(onlineUsers);
//        panel.add(Box.createVerticalStrut(3));
//        panel.add(Box.createHorizontalStrut(10));

        // user selects from the list who he wants to send the message
        onlineUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                selected = (String) onlineUsers.getSelectedItem();
                System.out.println("you selected " + selected);
            }
        });

        String user = username;
        send.setText(user);


        receiver.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(receiver);
        panel.add(Box.createVerticalStrut(5));

        receiveMessageTextField.setMaximumSize(receiveMessageTextField.getPreferredSize());
        receiveMessageTextField.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panel.add(receiveMessageTextField);
        panel.add(Box.createVerticalStrut(6));


        send.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panel.add(send);
        panel.add(Box.createVerticalStrut(5));

        sendMessageTextField.setMaximumSize(sendMessageTextField.getPreferredSize());
        sendMessageTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(sendMessageTextField);
        panel.add(Box.createVerticalStrut(6));


        panel.add(sendMessageButton);
        sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println(selected + "a sender");
                Client.sendMessage(sendMessageTextField.getText(), selected);
                sendMessageTextField.setText("");

            }
        });



        panel.add(sendFileButton);
        sendFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//
                JFileChooser chooser = new JFileChooser(); // 1. Create chooser
                chooser.setCurrentDirectory(new File(".")); // Set initial dir

                int result = chooser.showOpenDialog(null); // 2. Show open dialog

                if (result == JFileChooser.APPROVE_OPTION) { // 3. Check result
                    File selectedFile = chooser.getSelectedFile(); // 4. Get file
                    System.out.println("Selected file: " + selectedFile.getName());



                    // receiver, sender , file
                    Client.sendFile(selected, username, selectedFile);
                }


            }
        });


//        panel.add(friendsList);


        panel.add(logoutButton);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client.logoutUser(username);
                dispose();
            }
        });
//        // user selects the friend who he wants to send the friend request
//        friendsList.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                selected = (String) onlineUsers.getSelectedItem();
//            }
//        });

        panel.add(sendRequestButton);
        sendRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // select friend is a receiver
                Client.sendRequest(selected);
            }
        });
        add(panel);
        setVisible(true);
    }

    // when user receives a message
    public void displayMessage(String sender, String text) {
        receiver.setText(sender);
        receiveMessageTextField.setText(text);
    }

    // when user receives friend request
    public void friendRequestDialogBox(String sender) {
        JOptionPane.showMessageDialog(null, sender + " wants to send you a friend request.");
//        panel.add(acceptButton);
//        panel.add(declineButton);
    }

    public void updateOnlineUsers(String[] users)
    {
        onlineUsers.removeAllItems();

        for (String user : users) {
                onlineUsers.addItem(user);
//            }
        }
    }


    public void fileMessage(String sender)
    {
        JOptionPane.showMessageDialog(null, "You received a file from " + sender );
    }

    public static void main() {
        new ChatScreen("ali");
    }

}
