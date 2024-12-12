package coding;

import coding.Observer.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;


import javax.swing.*;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

import static coding.Group_Manager.objectMapper;

@JsonDeserialize(builder = User.UserBuilder.class)
@JsonIgnoreProperties(ignoreUnknown = true)

public class User {
    private String profilePath;
    private String coverPath;
    private String bio;
    private String password;
    private String email;
    private String status;
    private boolean receivedRequest;

    private final String userId;
    private final String userName;
    private final LocalDate dateOfBirth;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonIgnore private final JFileChooser jFileChooser = new JFileChooser();
    @JsonIgnore private final FriendHandler friendHandler;
    @JsonIgnore private final Friend_Manager manager;
    @JsonIgnore private final ContentHandler handler;
    @JsonIgnore private final Notifier notifier;
    @JsonIgnore private Group_Manager groupManager;

    @JsonIgnore private Content_Observer content_observer;
    @JsonIgnore private Request_Observer request_observer;
    @JsonIgnore private Group_Observer  group_observer;


    @JsonIgnore private Notifications notificationsWindow;
    @JsonIgnore @JsonProperty
    private String observer;
    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    // Private constructor for User
    protected User(String userId, String password, String userName, String email, LocalDate dateOfBirth, String status) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.status = status;

        this.manager = new Friend_Manager(this);
        this.handler = new ContentHandler();
        this.notifier = new Notifier();
        this.groupManager = new Group_Manager(this);

        this.friendHandler = new FriendHandler();
        this.receivedRequest = false;

        this.profilePath = getProfilePath();
        this.coverPath = getCoverPath();
        this.bio = getBio();

        this.content_observer = new Content_Observer(this);
        this.group_observer = new Group_Observer(this);
        this.request_observer = new Request_Observer(this);
    }

    public String getUserId() {
        return userId;
    }


    ////////////////////// Observer ///////////////////////////////////
    public void createObserver(){
        if (notificationsWindow == null)
            notificationsWindow = new Notifications(this);
    }


    public Notifications getObserver() {
        createObserver();
        return notificationsWindow;
    }

    public void populateObservers(){
        for (User friend : manager.getFriends()){
            notifier.addObserver(friend.getObserver());
        }
    }

    public Content_Observer getContent_observer() {
        return content_observer;
    }

    public void setContent_observer(Content_Observer content_observer) {
        this.content_observer = content_observer;
    }

    public Request_Observer getRequest_observer() {
        return request_observer;
    }

    public void setRequest_observer(Request_Observer request_observer) {
        this.request_observer = request_observer;
    }

    public Group_Observer getGroup_observer() {
        return group_observer;
    }

    public void setGroup_observer(Group_Observer group_observer) {
        this.group_observer = group_observer;
    }

    ////////////////////////////////////////////////////////////

    // Builder class for User
    @JsonPOJOBuilder(withPrefix = "set")
    public static class UserBuilder {
        private String userId;
        private String password;
        private String userName;
        private String email;
        private LocalDate dateOfBirth;
        private String status;
        private String profilePath;
        private String  coverPath;
        private String bio;
        private boolean receivedRequest;
        private String observer;


        public UserBuilder() {
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

        @JsonProperty("profilePath")
        public UserBuilder setProfilePath(String profilePath) {
            this.profilePath = profilePath;
            return this;
        }
        @JsonProperty("coverPath")
        public UserBuilder setCoverPath(String coverPath) {
            this.coverPath = coverPath;
            return this;
        }
    public UserBuilder setBio(String bio){
            this.bio = bio;
            return this;
    }
    public UserBuilder setReceivedRequest(boolean receivedRequest) {
        this.receivedRequest = receivedRequest;
        return this;
    }
        public UserBuilder observer(String observer) {
            this.observer = observer;
            return this;
        }

        public User build() {
             User user = new User(userId, password, userName, email, dateOfBirth, status);
            user.profilePath = this.profilePath;
            user.coverPath = this.coverPath;
            user.bio = this.bio;
            user.receivedRequest = this.receivedRequest;
            user.observer = this.observer;
            return user;
        }
    }


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

                coverPath = selectedFile.getAbsolutePath();
                Database database = Database.getInstance();
                database.saveUsers();
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

                profilePath = selectedFile.getAbsolutePath();
                Database database = Database.getInstance();
                database.saveUsers();
                JOptionPane.showMessageDialog(null,"Image Chosen successfully");
            }
            else{
                JOptionPane.showMessageDialog(null, "Please Choose an Image!", "Message", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void setCoverpath(){
        this.coverPath = "images/account.png";
    }

    public void setProfilepath() {
        this.profilePath = "images/account.png";
    }

    public String getProfilePath() {
        return profilePath;
    }

    public String getCoverPath() {
        return coverPath;
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

    public FriendHandler getFriendHandler() {
        return friendHandler;
    }

    public Friend_Manager getManager() {
        return manager;
    }

    public ContentHandler getHandler() {
        return handler;
    }

    public Notifier getNotifier(){ return notifier;}

    public Group_Manager getGroupManager() {
        return groupManager;
    }

    public void setGroupmanager(Group_Manager groupManager) {
        this.groupManager = groupManager;
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


    /*public String toString(){
        return "UserId: " + userId +
                "UserName: " + userName +
                "UserEmail: " + email +
                "password" + password +
                "Status: " + status +
                "Date of Birth: " + dateOfBirth;


    }*/



}
