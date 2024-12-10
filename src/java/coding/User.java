package coding;

import coding.Observer.ContentNotifier;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import javax.swing.*;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

@JsonDeserialize(builder = User.UserBuilder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    // User information
    private String profilepath;
    private String coverpath;
    private String bio;
    private String password;
    private String email;
    private String status;
    private boolean receivedRequest;

    // Core components
    @JsonIgnore private final JFileChooser jFileChooser = new JFileChooser();
    @JsonIgnore private final FriendHandler friendHandler;
    @JsonIgnore private final Friend_Manager manager;
    @JsonIgnore private final ContentHandler handler;
    @JsonIgnore private final ContentNotifier notifier;
    @JsonIgnore private Group_Manager groupManager;

    // User properties
    private final String userId;
    private final String userName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate dateOfBirth;

    // Notifications
    private Notifications notificationWindow;

    // Constructor
    private User(String userId, String password, String userName, String email, LocalDate dateOfBirth, String status) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.status = status;

        this.manager = new Friend_Manager(this);
        this.handler = new ContentHandler();
        this.friendHandler = new FriendHandler();
        this.notifier = new ContentNotifier();
        this.groupManager = new Group_Manager();

        this.profilepath = "images/account.png";
        this.coverpath = "images/account.png";
        this.bio = "";
        this.receivedRequest = false;
    }

    // Observer creation
    public void createObserver() {
        this.notificationWindow = new Notifications(this);
    }

    // Builder for User
    @JsonPOJOBuilder(withPrefix = "set")
    public static class UserBuilder {
        private String userId;
        private String password;
        private String userName;
        private String email;
        private LocalDate dateOfBirth;
        private String status;
        private String profilepath;
        private String coverpath;
        private String bio;
        private boolean receivedRequest;

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

        public UserBuilder setBio(String bio) {
            this.bio = bio;
            return this;
        }

        public UserBuilder setReceivedRequest(boolean receivedRequest) {
            this.receivedRequest = receivedRequest;
            return this;
        }

        public User build() {
            User user = new User(userId, password, userName, email, dateOfBirth, status);
            user.profilepath = this.profilepath != null ? this.profilepath : "images/account.png";
            user.coverpath = this.coverpath != null ? this.coverpath : "images/account.png";
            user.bio = this.bio != null ? this.bio : "";
            user.receivedRequest = this.receivedRequest;
            return user;
        }
    }

    // Setters and Getters
    public String getUserId() { return userId; }
    public String getUserName() { return userName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getStatus() { return status; }
    public String getProfilepath() { return profilepath; }
    public String getCoverpath() { return coverpath; }
    public String getBio() { return bio; }
    public boolean isReceivedRequest() { return receivedRequest; }

    public void setProfilepath(String profilepath) { this.profilepath = profilepath; }
    public void setCoverpath(String coverpath) { this.coverpath = coverpath; }
    public void setBio(String bio) { this.bio = bio; }
    public void setRequestState(boolean state) { this.receivedRequest = state; }
    public void setPassword(String password) { this.password = password; }
    public void setStatus(String status) { this.status = status; }

    // Handlers and Managers
    public FriendHandler getFriendHandler() { return friendHandler; }
    public Friend_Manager getManager() { return manager; }
    public ContentHandler getHandler() { return handler; }
    public ContentNotifier getNotifier() { return notifier; }
    public Group_Manager getGroupManager() { return groupManager; }
    public void setGroupManager(Group_Manager groupManager) { this.groupManager = groupManager; }

    // File Dialog Handling (shared logic extracted for DRY principle)
    private void setImage(String type) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose an Image");
        int userChoice = fileChooser.showSaveDialog(null);

        if (userChoice == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (selectedFile.exists()) {
                if ("profile".equals(type)) {
                    profilepath = selectedFile.getAbsolutePath();
                } else {
                    coverpath = selectedFile.getAbsolutePath();
                }
                Database.getInstance().saveUsers();
                JOptionPane.showMessageDialog(null, "Image chosen successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Invalid file. Please choose an image!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, userChoice == JFileChooser.CANCEL_OPTION ? "Action canceled." : "An error occurred.");
        }
    }

    public void setProfile() { setImage("profile"); }
    public void setCover() { setImage("cover"); }

    // Additional Methods
    @JsonIgnore public ArrayList<FriendRequest> getRequests() { return manager.getRequests(); }
    @JsonIgnore public ArrayList<User> getSuggestions() { return manager.getSuggestions(); }
    public Notifications getObserver() { return notificationWindow; }
}
