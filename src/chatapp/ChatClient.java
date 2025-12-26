package chatapp;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.Scanner;

public class ChatClient {

    private static ObjectInputStream in;
    private static ObjectOutputStream out;
    private static String username;

    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("localhost", 5000);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        Scanner sc = new Scanner(System.in);

        System.out.print("Signup or Login? (s/l): ");
        String choice = sc.nextLine();

        System.out.print("Username: ");
        username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        if (choice.equalsIgnoreCase("s")) {
            out.writeObject(new Packet("SIGNUP", username, null, password));
            System.out.println(in.readObject());
        }

        out.writeObject(new Packet("LOGIN", username, null, password));
        System.out.println(in.readObject());

        // Receiver thread
        new Thread(() -> {
            try {
                while (true) {
                    Object obj = in.readObject();

                    if (obj instanceof Packet) {
                        Packet p = (Packet) obj;

                        if (p.type.equals("MESSAGE")) {
                            System.out.println(p.sender + ": " + p.data);
                        } else if (p.type.equals("FILE")) {
                            saveFile(p);
                        } else if (p.type.equals("STATUS")) {
                            System.out.println(p.sender + " is " + p.data);
                        }
                    }
                }
            } catch (Exception e) {}
        }).start();

        // Sending loop
        while (true) {
            System.out.println("1.Send Msg  2.Send File  3.Logout");
            int opt = Integer.parseInt(sc.nextLine());

            if (opt == 1) {
                System.out.print("To: ");
                String to = sc.nextLine();
                System.out.print("Message: ");
                String msg = sc.nextLine();

                out.writeObject(
                        new Packet("MESSAGE", username, to, msg));

            } else if (opt == 2) {
                System.out.print("To: ");
                String to = sc.nextLine();
                System.out.print("File path: ");
                String path = sc.nextLine();

                byte[] fileData = Files.readAllBytes(Paths.get(path));
                out.writeObject(
                        new Packet("FILE", username, to, fileData));
            } else {
                out.writeObject(
                        new Packet("LOGOUT", username, null, null));
                socket.close();
                break;
            }
        }
    }

    private static void saveFile(Packet p) throws IOException {
        FileOutputStream fos =
                new FileOutputStream("received_from_" + p.sender);
        fos.write((byte[]) p.data);
        fos.close();
        System.out.println("File received from " + p.sender);
    }
}

