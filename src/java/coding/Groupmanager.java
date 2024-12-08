package coding;

import javax.swing.*;
import java.util.ArrayList;

public class Groupmanager {
   private ArrayList<Group> groups;
   private Posts post;
   private User primaryadmin;
   private ArrayList<User> otheradmin;
   private ArrayList<Group> suggestions;
   private Friend_Manager friendManager;
   Groupmanager(Group group,User primaryadmin,ArrayList<User> otheradmin)
   {
       setPrimaryadmin(primaryadmin);
       setOtheradmin(otheradmin);
       this.groups = new ArrayList<>();
   }
   public void deletegroup(Group group,User primaryadmin,User otheradmin)
   {
       if(group.getOtheradmins().contains(otheradmin) || group.getPrimaryadmin().equals(primaryadmin))
           groups.remove(group);
       else
           JOptionPane.showMessageDialog(null,"user not an admin !");
   }
    public void deletepost(Group group,User primaryadmin,User otheradmin,Posts post)
    {
        if((group.getOtheradmins().contains(otheradmin) || group.getPrimaryadmin().equals(primaryadmin)) && group.getPosts().contains(post))
           group.getPosts().remove(post);
        else
            JOptionPane.showMessageDialog(null,"user not an admin !");
    }
    public boolean ismember(User user,Group group)
    {
        if(group.getMembers().contains(user))
            return true;
        else
            JOptionPane.showMessageDialog(null,"user not a member !");
        return false;
    }
    public void viewsuggestions(User user)
    {
        for (Group group : groups){
            if(!ismember(user,group) && !suggestions.contains(group) && !friendManager.getBlocked().contains(group.getPrimaryadmin()))
                suggestions.add(group);
        }

    }
    public ArrayList<Group> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }

    public Posts getPost() {
        return post;
    }

    public void setPost(Posts post) {
        this.post = post;
    }



    public User getPrimaryadmin() {
        return primaryadmin;
    }

    public void setPrimaryadmin(User primaryadmin) {
        this.primaryadmin = primaryadmin;
    }

    public void addGroup(Group group)
    {
        groups.add(group);
    }
    public void removemember(Group group,User member,User primaryadmin,User otheradmin)
    {
        if(group.getPrimaryadmin().equals(primaryadmin))
        group.getMembers().remove(member);
        else if (group.getOtheradmins().contains(otheradmin)) {
            if(!group.getOtheradmins().contains(member) && !group.getPrimaryadmin().equals(primaryadmin))
                 group.getMembers().remove(member);
                else
            JOptionPane.showMessageDialog(null,"not accessed to remove an admin");
        }
    }

    @Override
    public String toString() {
        return "Groupmanager{" +
                "friendManager=" + friendManager +
                ", groups=" + groups +
                ", post=" + post +
                ", primaryadmin=" + primaryadmin +
                ", otheradmin=" + otheradmin +
                ", suggestions=" + suggestions +
                '}';
    }

    public ArrayList<User> getOtheradmin() {
        return otheradmin;
    }

    public void setOtheradmin(ArrayList<User> otheradmin) {
        this.otheradmin = otheradmin;
    }
}
