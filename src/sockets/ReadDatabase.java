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
//        String s2 = null;
        String s2 = null;
        try {
            Connection con = DriverManager.getConnection(url, username, password);
            Statement stat = con.createStatement();
            String query =
                    "Select `userinfo`.`password` from `messenger`.`userinfo` where `userinfo`.`username` = \"" + user + "\"";
            ResultSet rs = stat.executeQuery(query);
            if(rs.next()) {

                s2 = rs.getString("password");
            }
            else {
                return false;
            }


            stat.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


            return s2.equals(pass);

    }

}
