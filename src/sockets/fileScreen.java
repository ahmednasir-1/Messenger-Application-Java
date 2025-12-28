package sockets;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class fileScreen extends JFrame {

    fileScreen(String username) {

        setLayout(new FlowLayout());
        setSize(300, 300);
        setTitle("Select a file");

        JFileChooser chooser = new JFileChooser(); // 1. Create chooser
        chooser.setCurrentDirectory(new File(".")); // Set initial dir

        int result = chooser.showOpenDialog(null); // 2. Show open dialog

        if (result == JFileChooser.APPROVE_OPTION) { // 3. Check result
            File selectedFile = chooser.getSelectedFile(); // 4. Get file
            System.out.println("Selected file: " + selectedFile.getName());
            // 5. Process file here (e.g., read content)
        }
    }


    public static void main() {
        new fileScreen("ali");
    }
}
