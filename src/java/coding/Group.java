package coding;

import java.util.ArrayList;

public class Group {
    private ArrayList<User> members;
    private String profilepath;
    private User primaryadmin;
    private String description;
    private String name;
    private ArrayList<User> otheradmins;
    Group(User primaryadmin,ArrayList<User> members,String profilepath,String description,String name,ArrayList<User> otheradmins) {
        setPrimaryadmin(primaryadmin);
        setDescription(description);
        setMembers(members);
        setProfilepath(profilepath);
        setName(name);
        setOtheradmins(otheradmins);
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
}
