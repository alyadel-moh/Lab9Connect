package coding;

import coding.ENUMS.GROUP_STATUS;
import coding.ENUMS.NOTIFICATIONS.GROUP;

import coding.Observer.Notifier;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class Group  {
    private String profilePath;
    private User primaryAdmin;
    private String description;
    private String name;

    private ArrayList<User> members;
    private ArrayList<User> otherAdmins;
    private ArrayList<Posts> posts;
    private ArrayList<Group_Request> requests;

    @JsonIgnore private Notifier notifier;

    Group(){}
    Group(User primary)
    {
        this.members = new ArrayList<>();
        this.posts = new ArrayList<>();
        this.requests = new ArrayList<>();
        this.otherAdmins = new ArrayList<>();
        this.notifier = new Notifier();
        this.primaryAdmin=primary;
    }

    public void populateObservers(){
        if (members.isEmpty()){
            System.out.println("No members to populate");
            return;
        }
        for (User member : members){
            notifier.addGroupObserver(member.getGroup_observer());
        }

        for (User admin : otherAdmins){
            notifier.addGroupObserver(admin.getGroup_observer());
        }

        notifier.addGroupObserver(primaryAdmin.getGroup_observer());
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<User> members) {
        this.members = members;
    }

    public Notifier getNotifier() {
        return notifier;
    }

    public ArrayList<Posts> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Posts> posts) {
        this.posts = posts;
    }

    public void addPost(Posts post)
    {
        posts.add(post);
        System.out.println("Post added: " + post);
        System.out.println("Total posts: " + posts.size());
        System.out.println(posts.getFirst());
        populateObservers();
        notifier.notifyGroupObservers(this, GROUP.POST, null);
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getPrimaryAdmin() {
        return primaryAdmin;
    }

    public void setPrimaryAdmin(User primaryAdmin) {
        this.primaryAdmin =  primaryAdmin;
    }

    public String getProfilepath() {
        return profilePath;
    }

    public void setProfilepath(String profilepath) {
        this.profilePath = profilepath;
    }

    public void setProfilepath(){this.profilePath = "images/account.png";}
    public ArrayList<User> getOtherAdmins() {
        return otherAdmins;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public void setOtherAdmins(ArrayList<User> otherAdmins) {
        this.otherAdmins = otherAdmins;
    }

    @Override
    public String toString() {
        return "Group{" +
                "description='" + description + '\'' +
                ", members=" + members +
                ", profile path='" + profilePath + '\'' +
                ", primary admin=" + primaryAdmin +
                ", name='" + name + '\'' +
                ", other admins= " + getOtherAdmins() +
                ", posts=" + posts +
                '}';
    }

    public ArrayList<Group_Request> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<Group_Request> requests) {
        this.requests = requests;
    }

    public void promote(Group group,User member){
            members.remove(member);
            otherAdmins.add(member);
            populateObservers();
            notifier.notifyGroupObservers(group, GROUP.CHANGE_STATUS, member.getGroup_observer());
    }

    public void demote(Group group,User member) {
        otherAdmins.remove(member);
        members.add(member);
        populateObservers();
        notifier.notifyGroupObservers(group, GROUP.CHANGE_STATUS, member.getGroup_observer());
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
}
