package coding;

import coding.ENUMS.CONTENT_TYPE;
import coding.ENUMS.Mapper;
import coding.ENUMS.REQUEST;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class NotificationForRequest implements Serializable{

        @JsonProperty("sender")
        private String sender;

        @JsonProperty("message")
        private String message;

        @JsonProperty("type")
        private REQUEST type;

        // Constructor
        public NotificationForRequest(Object user, Enum code) {
            // Assuming `user` can be a string for simplicity; otherwise, modify accordingly
            this.sender = user instanceof User ? ((User) user).getUserName() : "Unknown";  // Assuming sender is a User's username
            this.message = Mapper.getMessage(code);  // Message as per the type of notification
            this.type = REQUEST.valueOf(code.name());  // The type, such as "POST" or "STORY"
        }
        public NotificationForRequest(){

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

        public REQUEST getType() {
            return type;
        }

        public void setType(REQUEST type) {
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
