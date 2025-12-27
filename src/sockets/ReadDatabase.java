package sockets;

import java.sql.*;

public class ReadDatabase {
    private final String username = "root";
    private final String password = "ahmed123";
    private final String url = "jdbc:mysql://127.0.0.1:3306/messenger";


    public void saveDataOfUser(User u) {
        try {
            Connection con = DriverManager.getConnection(url, username, password);
            Statement stat = con.createStatement();
            String query =
                    "INSERT INTO `messenger`.`userinfo` (`Name`, `username`,`password`,`fileName`) VALUES (\"" +
                            u.getName() + "\",\"" +
                            u.getUsername() + "\",\"" +
                            u.getPassword() + "\",\"" +
                            u.getFileName() + "\");";
            stat.executeUpdate(query);
            System.out.println("Data inserted successfully");

            stat.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean validateCredentials(String user, String pass) {
//        boolean flag;
        User u = new User();
        try {
            Connection con = DriverManager.getConnection(url, username, password);
            Statement stat = con.createStatement();
            String query =
                    "Select * from `messenger`.`userinfo` where `userinfo`.`username` = \"" +
                            user + "\" && `userinfo`.`password` = \"" +
                            pass + "\"";
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                u.setName(rs.getString("Name"));
                System.out.println(u.getName());
                u.setFileName(rs.getString("fileName"));
                System.out.println(u.getName());
            }

            stat.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        if (u.getName().equalsIgnoreCase("")) {
//            System.out.println("invalid");
//            return false;
//        } else {
//            System.out.println("valid");
            return true;
//        }
    }

}
