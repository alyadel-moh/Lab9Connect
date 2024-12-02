package coding;

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
    private ArrayList<Content>contents;


    public User(String userId, String password, String userName, String email, LocalDate dateOfBirth, String status) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.status = status;
        this.receivedRequest = false;

        this.manager = new Friend_Manager(this);
        this.contents=new ArrayList<>();//intialize it as an empty arraylist at first

    }

    public void addContent(Content content){
        contents.add(content);//add posts or stories to the arraylist
    }

    public ArrayList<Content> getContentList(){
        return contents;
    }

    public ArrayList<Stories> getActiveStories(){
        ArrayList <Stories>activeStories =new ArrayList<Stories>();
        for(int i=0;i<contents.size();i++){
            if(contents.get(i)instanceof Stories && !contents.get(i).isExpired()){
                activeStories.add((Stories) contents.get(i));
            }
        }
        return activeStories;
    }

    public void deleteExpiredStories(){
        for(int i=0;i<contents.size();i++){
            if(contents.get(i)instanceof Stories && contents.get(i).isExpired()){
                contents.remove(contents.get(i));
            }
        }
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

    public ImageIcon getProfile() {
        return profile;
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
