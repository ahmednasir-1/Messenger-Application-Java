package sockets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignupScreen extends JFrame {
    JLabel file = new JLabel("Upload your image:");
    JLabel name = new JLabel("Enter your name: ");
    JTextField nameTextField = new JTextField(20);
    JLabel username = new JLabel("Enter your username: ");
    JLabel password = new JLabel("Password: ");
    JTextField usernameTextField = new JTextField(20);
    JTextField passwordTextField = new JTextField(20);
    JButton signupButton = new JButton("Confirm");
    JButton cancelButton = new JButton("Cancel");
    JButton chooseAFileButton = new JButton("Choose a File");

    JPanel jp = new JPanel(new FlowLayout());

    SignupScreen() {
        setSize(380, 400);
        setTitle("Signup");


        jp.add(chooseAFileButton);

        chooseAFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser userImage = new JFileChooser();
                userImage.setDialogTitle("Choose a file to set: ");


            }
        });
        jp.add(file);
//        jp.add(userImage);

        jp.add(name);
        jp.add(nameTextField);

        jp.add(password);
        jp.add(passwordTextField);

        jp.add(username);
        jp.add(usernameTextField);

        jp.add(signupButton);
        jp.add(cancelButton);

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Client.signup(name.getText(), username.getText(), password.getText());
                dispose();
                new LoginScreen();
            }
        });

        add(jp);
        setVisible(true);

    }

    public static void main() {
        new SignupScreen();
    }

}
