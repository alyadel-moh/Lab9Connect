package coding;

import coding.Interfaces.Requester;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Group_Manager implements Requester {
    private Map<String, Group> groups;
    private Posts post;
    private ArrayList<Group> suggestions;
    private Friend_Manager friendManager;
    private static Map<String, Group> allgroups = new HashMap<>();

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        // Register the JavaTimeModule for handling LocalDate
        objectMapper.registerModule(new JavaTimeModule());
        // Optional: Configure the object mapper to handle dates more flexibly
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    Group_Manager() {
        this.groups = new HashMap<>();
    }

    public void deletegroup(Group group, User primaryadmin) {
        if (group.getPrimaryadmin().equals(primaryadmin)) {
            groups.remove(group.getName(), group);
            allgroups.remove(group.getName(), group);
        } else
            JOptionPane.showMessageDialog(null, "user not an admin !");
    }

    public void deletepost(Group group, User primaryadmin, User otheradmin, Posts post) {
        if ((group.getOtheradmins().contains(otheradmin) || group.getPrimaryadmin().equals(primaryadmin)) && group.getPosts().contains(post)) {
            group.getPosts().remove(post);
            saveGroups();
        } else
            JOptionPane.showMessageDialog(null, "user not an admin !");
    }

    public boolean isMember(User user, Group group) {
        if (group.getMembers().contains(user))
            return true;
        else
            JOptionPane.showMessageDialog(null, "user not a member !");
        return false;
    }

    public void viewsuggestions(User user) {
        for (String key : allgroups.keySet()) {
            if (!isMember(user, allgroups.get(key)) && !suggestions.contains(allgroups.get(key)) && !friendManager.getBlocked().contains(allgroups.get(key).getPrimaryadmin()))
                suggestions.add(allgroups.get(key));
            saveSuggestionGroups();
        }
    }


    public Posts getPost() {
        return post;
    }

    public void setPost(Posts post) {
        this.post = post;
    }


    public void addGroup(Group group) {
        if (group != null) {
            groups.put(group.getName(), group);
            allgroups.put(group.getName(), group);
            saveGroups();
        }
    }

    public void removemember(Group group, User member, User primaryadmin, User otheradmin) {
        if (group.getPrimaryadmin().equals(primaryadmin))
            group.getMembers().remove(member);
        else if (group.getOtheradmins().contains(otheradmin)) {
            if (!group.getOtheradmins().contains(member) && !group.getPrimaryadmin().equals(primaryadmin)) {
                group.getMembers().remove(member);
                saveGroups();
            } else
                JOptionPane.showMessageDialog(null, "not accessed to remove an admin");
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
        Group_Manager.allgroups = allgroups;
    }

    public void saveGroups() {
        try {
            objectMapper.writeValue(new File("Groups.json"), allgroups);
            System.out.println("Groups saved successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGroups() {
        File file = new File("Groups.json");
        if (!file.exists()) {
            System.out.println("Groups file not found. Initializing an empty group list.");
            this.groups = new HashMap<>();
            return;
        }
        try {
            this.groups = objectMapper.readValue(file, objectMapper.getTypeFactory().constructMapType(Map.class, String.class, Group.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveSuggestionGroups() {
        try {
            objectMapper.writeValue(new File("SuggestionGroups.json"), suggestions);
            System.out.println("Groups saved successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSuggestionGroups() {
        File file = new File("SuggestionGroups.json");
        if (!file.exists()) {
            System.out.println("Suggestion groups file not found. Initializing an empty suggestions list.");
            this.suggestions = new ArrayList<>();
            return;
        }
        try {
            this.suggestions = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Group.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendRequest(Object generic_receiver) {
        Group receiver = (Group) generic_receiver;
        //////////////to be implemented

    }
}


