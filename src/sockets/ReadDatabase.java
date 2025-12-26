package sockets;

import java.sql.*;

public class ReadDatabase {
    private final String username = "root";
    private final String password = "ahmed123";
    private final String url = "jdbc:mysql://127.0.0.1:3306/messenger";


    public void saveDataOfUser(User u)
    {
        try{
            Connection con = DriverManager.getConnection(url,username,password);
            Statement stat = con.createStatement();
            String query =
                    "INSERT INTO `messenger`.`userinfo` (`Name`, `username`,`password`,`fileName`) VALUES (\"" +
                            u.getName() + "\",\"" +
                            u.getUsername() + "\",\"" +
                            u.getPassword() + "\",\"" +
                            u.getFileName() + "\")";
            stat.executeUpdate(query);
            System.out.println("Data inserted successfully");

            stat.close();
            con.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public User validateCredentials(String username, String password)
    {
        User u = new User();
        try{
            Connection con = DriverManager.getConnection(url,username,password);
            Statement stat = con.createStatement();
            String query =
                    "Select `messenger`.`userinfo` where 'userinfo`.`username` = \"" +
                            username + "\", `userinfo`.`password` = \"" +
                            password + "\"";
         ResultSet rs = stat.executeQuery(query);
         while(rs.next())
         {
                u.setName(rs.getString("Name"));
                u.setFileName(rs.getString("fileName"));
         }

            stat.close();
            con.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return u;
    }

}
