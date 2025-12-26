package chatapp;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class User implements Serializable {
    public String username;
    public String password;
    public String status = "Available";
    public Set<String> friends = new HashSet<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

}

