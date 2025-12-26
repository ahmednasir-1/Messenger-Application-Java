package sockets;

public class User {

    private String username;
    private String password;
    private String name;
    private String fileName;

    String getUsername()
    {
        return username;
    }
    String getPassword()
    {
        return password;
    }
    String getName()
    {
        return name;
    }
    String getFileName()
    {
        return fileName;
    }
    void setUsername(String s)
    {
        username = s;
    }
    void setPassword(String s)
    {
        password = s;
    }
    void setFileName(String s)
    {
        fileName = s;
    }
    void setName(String s)
    {
        name = s;
    }


}
