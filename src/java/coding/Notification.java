package coding;

import coding.ENUMS.CONTENT_TYPE;
import com.fasterxml.jackson.annotation.JsonProperty;
import coding.ENUMS.Mapper;

import java.io.Serializable;

public class Notification implements Serializable {

    @JsonProperty("sender")
    private String sender;

    @JsonProperty("message")
    private String message;

    @JsonProperty("type")
    private CONTENT_TYPE type;

    // Constructor
    public Notification(Object user, Enum code) {
        // Assuming `user` can be a string for simplicity; otherwise, modify accordingly
        this.sender = user instanceof User ? ((User) user).getUserName() : "Unknown";  // Assuming sender is a User's username
        this.message = Mapper.getMessage(code);  // Message as per the type of notification
        this.type = CONTENT_TYPE.valueOf(code.name());  // The type, such as "POST" or "STORY"
    }
    public Notification(){

    }

    // Getters and Setters
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CONTENT_TYPE getType() {
        return type;
    }

    public void setType(CONTENT_TYPE type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "sender='" + sender + '\'' +
                ", message='" + message + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
