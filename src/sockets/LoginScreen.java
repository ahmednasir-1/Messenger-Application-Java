package sockets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JFrame {

    JLabel username = new JLabel("Enter your username");
    JTextField usernameTextField = new JTextField(20);

    JLabel password = new JLabel("Enter your password: ");
    JTextField passwordTextField = new JTextField(20);

    JButton loginButton = new JButton("Login");

    JPanel jp = new JPanel();
    LoginScreen()
    {
        setTitle("Login Screen");
        setSize(800,200);
        setLayout(new FlowLayout());

        jp.add(username);
        jp.add(usernameTextField);

        jp.add(password);
        jp.add(passwordTextField);

        jp.add(loginButton);

        add(jp);
        setVisible(true);


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boolean success = Client.login(usernameTextField.getText(), passwordTextField.getText());
                if(success)
                {
                        dispose();

                        String username = usernameTextField.getText();
                        ChatScreen chat = new ChatScreen(username);
                        chat.setVisible(true);

                        Client.startListener(chat);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Invalid Credentials");
                }
            }
        });

    }

    public static void main() {
        new LoginScreen();
    }
}
