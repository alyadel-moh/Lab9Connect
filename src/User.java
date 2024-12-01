import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class User {
    private ImageIcon profile;
    private ImageIcon cover;
    private String bio;
    private final String userId;
    private String password;
    private final String userName;
    private String email;
    private final LocalDate dateOfBirth;
    private String status;
    private final JFileChooser jFileChooser = new JFileChooser();

    private Friend_Manager manager;

    private boolean receivedRequest;


    public User(String userId, String password, String userName, String email, LocalDate dateOfBirth, String status) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.status = status;
        this.receivedRequest = false;

        this.manager = new Friend_Manager(this);

    }

    public void setCover() {
        int response = jFileChooser.showOpenDialog(null);
        if(response == JFileChooser.APPROVE_OPTION){
            ImageIcon image = new ImageIcon(jFileChooser.getSelectedFile().getAbsolutePath());
            this.cover = image;
        }
    }

    public void setProfile() {
        int response = jFileChooser.showOpenDialog(null);
        if(response == JFileChooser.APPROVE_OPTION){
            ImageIcon image = new ImageIcon(jFileChooser.getSelectedFile().getAbsolutePath());
            this.profile = image;
        }
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setRequestState(boolean state){
        this.receivedRequest = state;
    }

    public boolean isReceivedRequest() {
        return receivedRequest;
    }

    public ArrayList<FriendRequest> getRequests(){
        return manager.getRequests();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName(){
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Friend_Manager getManager(){
        return manager;
    }
}