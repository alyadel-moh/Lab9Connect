package coding;

import javax.swing.*;
import java.util.ArrayList;

public class Group  {
    private ArrayList<User> members;
    private String profilepath;
    private User primaryadmin;
    private String description;
    private String name;
    private ArrayList<User> otheradmins;
    private ArrayList<Posts> posts;
    Group(User primaryadmin)
    {
        setPrimaryadmin(primaryadmin);
        this.members = new ArrayList<>();
        this.otheradmins = new ArrayList<>();
        this.posts = new ArrayList<>();
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
}
