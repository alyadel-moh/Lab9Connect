package coding;

import coding.ENUMS.GROUP_STATUS;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class Group  {
    private String profilePath;
    private Member primaryAdmin;
    private String description;
    private String name;

    private ArrayList<Member> members;
    private ArrayList<Posts> posts;
    private ArrayList<Group_Request> requests;
    private Notifications notifications;

    Group(Member primaryAdmin)
    {
        primaryAdmin.setGroup_status(GROUP_STATUS.PRIMARY);
        setPrimaryAdmin(primaryAdmin);

        this.members = new ArrayList<>();
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

    public ArrayList<Member> getOtherAdmins() {
        ArrayList<Member> otherAdmins = new ArrayList<>();

        for (Member member : members){
            if (member.getGroup_status() == GROUP_STATUS.ADMIN)
                otherAdmins.add(member);
        }

        return otherAdmins;
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
        this.primaryAdmin = (Member) primaryAdmin;
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<Member> members) {
        this.members = members;
    }

    public String getProfilepath() {
        return profilePath;
    }

    public void setProfilepath(String profilepath) {
        this.profilePath = profilepath;
    }

    public void setProfilepath(){this.profilePath = "images/account.png";}

    public void addMember(User primaryAdmin,Group group,User otherAdmin)
    {
        if(group.getPrimaryAdmin().equals(primaryAdmin)) {
            Member admin = (Member) otherAdmin;
            admin.setGroup_status(GROUP_STATUS.ADMIN);
            group.getMembers().add(admin);
        }else
            JOptionPane.showMessageDialog(null,"user not a primary admin !");
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
