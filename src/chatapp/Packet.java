package chatapp;

import java.io.Serializable;

public class Packet implements Serializable {
        public String type;      // LOGIN, SIGNUP, MESSAGE, FILE, STATUS, FRIEND
        public String sender;
        public String receiver;
        public Object data;

        public Packet(String type, String sender, String receiver, Object data) {
            this.type = type;
            this.sender = sender;
            this.receiver = receiver;
            this.data = data;
        }
    }


