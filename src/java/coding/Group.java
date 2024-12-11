package coding;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class Group  {
    private ArrayList<User> members;
    private String profilepath;
    private User primaryadmin;
    private String description;
    private String name;
    private ArrayList<User> otheradmins;
    private ArrayList<Posts> posts;
    private ArrayList<Group_Request> requests;
    private Notifications notifications;

    Group(User primaryadmin)
    {
        setPrimaryadmin(primaryadmin);
        this.members = new ArrayList<>();
        this.otheradmins = new ArrayList<>();
        this.posts = new ArrayList<>();
        this.requests = new ArrayList<>();
    }

    public ArrayList<Posts> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Posts> posts) {
        this.posts = posts;
    }
    public void addpost(Posts post)
    {
        posts.add(post);
        System.out.println("Post added: " + post);
        System.out.println("Total posts: " + posts.size());
        System.out.println(posts.get(0));
    }
    public ArrayList<User> getOtheradmins() {
        return otheradmins;
    }
    
    public void setOtheradmins(ArrayList<User> otheradmins) {
        this.otheradmins = otheradmins;
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

    public User getPrimaryadmin() {
        return primaryadmin;
    }

    public void setPrimaryadmin(User primaryadmin) {
        this.primaryadmin = primaryadmin;
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<User> members) {
        this.members = members;
    }

    public String getProfilepath() {
        return profilepath;
    }

    public void setProfilepath(String profilepath) {
        this.profilepath = profilepath;
    }

    public void setProfilepath(){this.profilepath = "images/account.png";}

    public void addotheradmin(User primaryadmin,Group group,User otheradmin)
    {
        if(group.getPrimaryadmin().equals(primaryadmin))
            group.getOtheradmins().add(otheradmin);
        else
            JOptionPane.showMessageDialog(null,"user not a primary admin !");
    }

    @Override
    public String toString() {
        return "Group{" +
                "description='" + description + '\'' +
                ", members=" + members +
                ", profile path='" + profilepath + '\'' +
                ", primary admin=" + primaryadmin +
                ", name='" + name + '\'' +
                ", other admins=" + otheradmins +
                ", posts=" + posts +
                '}';
    }

    public ArrayList<Group_Request> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<Group_Request> requests) {
        this.requests = requests;
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
