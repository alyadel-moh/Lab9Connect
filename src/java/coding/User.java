package coding;

import com.fasterxml.jackson.annotation.JsonFormat;


import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class User {
    private ImageIcon profile;
    private ImageIcon cover;
    private String bio;
    private String password;
    private String email;
    private String status;

    private final String userId;
    private final String userName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate dateOfBirth;
    private final JFileChooser jFileChooser = new JFileChooser();
    private Friend_Manager manager;
    private ContentHandler handler;

    private boolean receivedRequest;

    // Private constructor for User
    private User(String userId, String password, String userName, String email, LocalDate dateOfBirth, String status) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.status = status;
        this.manager = new Friend_Manager(this);
        this.handler = new ContentHandler();
        this.receivedRequest = false;
    }
//    public User(String userId, String password, String userName, String email, LocalDate dateOfBirth, String status){
//        this.userId = userId;
//        this.password = password;
//        this.userName = userName;
//        this.email = email;
//        this.dateOfBirth = dateOfBirth;
//        this.status = status;
//    }

// Builder class for User
    public static class UserBuilder {
        private String userId;
        private String password;
        private String userName;
        private String email;
        private LocalDate dateOfBirth;
        private String status;

        public UserBuilder() {
            this.userId = userId;
            this.password = password;
            this.userName = userName;
            this.email = email;
            this.dateOfBirth = dateOfBirth;
            this.status = status;
        }
        public UserBuilder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder setDateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public UserBuilder setStatus(String status) {
            this.status = status;
            return this;
        }

        public User build() {
            return new User(userId, password, userName, email, dateOfBirth, status);
        }
    }

    // Other methods
//    public ArrayList<Content> getContentList() {
//        return handler.getContents();
//    }

    public void setCover() {
        int response = jFileChooser.showOpenDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            ImageIcon image = new ImageIcon(jFileChooser.getSelectedFile().getAbsolutePath());
            this.cover = image;
        }
    }

    public void setProfile() {
        int response = jFileChooser.showOpenDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            ImageIcon image = new ImageIcon(jFileChooser.getSelectedFile().getAbsolutePath());
            this.profile = image;
        }
    }

    public ImageIcon getProfile() {
        return profile;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setRequestState(boolean state) {
        this.receivedRequest = state;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isReceivedRequest() {
        return receivedRequest;
    }

    public ArrayList<FriendRequest> getRequests() {
        return manager.getRequests();
    }

    public String getStatus() {
        return status;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Friend_Manager getManager() {
        return manager;
    }

    public ContentHandler getHandler() {
        return handler;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public void setStatus(String newStatus){
        this.status = newStatus;
    }

}
