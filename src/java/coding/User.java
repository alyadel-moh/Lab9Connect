package coding;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
@JsonDeserialize(builder = User.UserBuilder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private String profilepath;
    private String  coverpath;
    private String bio;
    private String password;
    private String email;
    private String status;

    private final String userId;
    private final String userName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate dateOfBirth;
    @JsonIgnore
    private final JFileChooser jFileChooser = new JFileChooser();
    @JsonIgnore
    private Friend_Manager manager;
    @JsonIgnore
    private ContentHandler handler;
@JsonIgnore
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
        this.profilepath = getProfilepath();
        this.coverpath = getCoverpath();
        this.bio = getBio();

    }

    public String getUserId() {
        return userId;
    }


// Builder class for User
@JsonPOJOBuilder(withPrefix = "set")
    public static class UserBuilder {
        private String userId;
        private String password;
        private String userName;
        private String email;
        private LocalDate dateOfBirth;
        private String status;
    private String profilepath;
    private String  coverpath;
    private String bio;

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
    public UserBuilder setProfilepath(String profilepath) {
        this.profilepath = profilepath;
        return this;
    }

    public UserBuilder setCoverpath(String coverpath) {
        this.coverpath = coverpath;
        return this;
    }
    public UserBuilder setBio(String bio){
            this.bio = bio;
            return this;
    }

        public User build() {
             User user = new User(userId, password, userName, email, dateOfBirth, status);
            user.profilepath = this.profilepath;
            user.coverpath = this.coverpath;
            user.bio = this.bio;
            return user;
        }
    }

    // Other methods
//    public ArrayList<Content> getContentList() {
//        return handler.getContents();
//    }

    public void setCover() {
        JFileChooser fileChooser = new JFileChooser();//Create the JfileChooser to show the save dialog
        fileChooser.setDialogTitle("Choose an Image");
        int userChoice = fileChooser.showSaveDialog(null);//shows the save dialog//null is to be centered to the screen//returns 0 if the user clicked save//returns 1 then the user canceled//-1 error occured
        if (userChoice == -1) {
            JOptionPane.showMessageDialog(null, "An error has occurred");
        } else if (userChoice == 1) {
            JOptionPane.showMessageDialog(null, "The user Cancelled");
        } else {
            File selectedFile = fileChooser.getSelectedFile();
            if(selectedFile.exists()){

                coverpath = selectedFile.getAbsolutePath();
                JOptionPane.showMessageDialog(null,"Image Chosen successfully");
            }
            else{
                JOptionPane.showMessageDialog(null, "Please Choose an Image!", "Message", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void setProfile() {
        JFileChooser fileChooser = new JFileChooser();//Create the JfileChooser to show the save dialog
        fileChooser.setDialogTitle("Choose an Image");
        int userChoice = fileChooser.showSaveDialog(null);//shows the save dialog//null is to be centered to the screen//returns 0 if the user clicked save//returns 1 then the user canceled//-1 error occured
        if (userChoice == -1) {
            JOptionPane.showMessageDialog(null, "An error has occurred");
        } else if (userChoice == 1) {
            JOptionPane.showMessageDialog(null, "The user Cancelled");
        } else {
            File selectedFile = fileChooser.getSelectedFile();
            if(selectedFile.exists()){

                profilepath = selectedFile.getAbsolutePath();
                JOptionPane.showMessageDialog(null,"Image Chosen successfully");
            }
            else{
                JOptionPane.showMessageDialog(null, "Please Choose an Image!", "Message", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void setCoverpath(){
        this.coverpath = "images/account.png";
    }

    public void setProfilepath() {
        this.profilepath = "images/account.png";
    }

    public String getProfilepath() {
        return profilepath;
    }

    public String getCoverpath() {
        return coverpath;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
    public String getBio(){return bio;}

    public void setRequestState(boolean state) {
        this.receivedRequest = state;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isReceivedRequest() {
        return receivedRequest;
    }

    @JsonIgnore
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

    @JsonIgnore
    public ArrayList<User> getSuggestions(){
        return manager.getSuggestions();
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public void setStatus(String newStatus){
        this.status = newStatus;
    }

    public String toString(){
        return "UserId" + userId +
                "UserName" + userName +
                "UserEmail" + email +
                "password" + password;

    }



}
