package sockets;

import javax.swing.*;
import java.awt.*;

public class practice extends JFrame {

    practice() {
        JPanel panel = new JPanel();
//        setLayout(new BoxLayout().X_AXIS);
//        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Enter your name:");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField textField = new JTextField(20);
        textField.setMaximumSize(textField.getPreferredSize());
        textField.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton button = new JButton("Submit");
        button.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(Box.createVerticalStrut(10));
        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
        panel.add(textField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(button);
        add(panel);
        setSize(300,300);
        setVisible(true);
    }



    public static void good()
    {
        List users = new List();
        users.add("ali");
        users.add("ahmed");

        for(int i=0 ;i< users.getItemCount(); i++)
        {
            System.out.println(users.getSelectedIndex());
        }
    }

    public static void main() {
        practice.good();
    }
}
