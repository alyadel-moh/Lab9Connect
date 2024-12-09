package coding;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Groupmanager {
    private Map<String, Group> groups;
   private Posts post;
   private ArrayList<Group> suggestions;
   private Friend_Manager friendManager;
    static Map<String,Group> allgroups = new HashMap<>();
   Groupmanager()
   {
       this.groups = new HashMap<>();
   }
   public void deletegroup(Group group,User primaryadmin,User otheradmin)
   {
       if(group.getOtheradmins().contains(otheradmin) || group.getPrimaryadmin().equals(primaryadmin))
       {
           groups.remove(group.getName(),group);
           allgroups.remove(group.getName(),group);
       }
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
        for (String key : allgroups.keySet()){
            if(!ismember(user,allgroups.get(key)) && !suggestions.contains(allgroups.get(key)) && !friendManager.getBlocked().contains(allgroups.get(key).getPrimaryadmin()))
                suggestions.add(allgroups.get(key));
        }

    }
    public Posts getPost() {
        return post;
    }

    public void setPost(Posts post) {
        this.post = post;
    }



    public void addGroup(Group group)
    {
        if(group != null ) {
            groups.put(group.getName(),group);
            allgroups.put(group.getName(),group);
        }
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
                ", suggestions=" + suggestions +
                '}';
    }

    public Map<String, Group> getGroups() {
        return groups;
    }

    public void setGroups(Map<String, Group> groups) {
        this.groups = groups;
    }

    public static Map<String, Group> getAllgroups() {
        return allgroups;
    }

    public static void setAllgroups(Map<String, Group> allgroups) {
        Groupmanager.allgroups = allgroups;
    }
}
