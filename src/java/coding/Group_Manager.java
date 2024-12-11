package coding;

import coding.ENUMS.REQUEST;
import coding.ENUMS.STATE;
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
    private static ArrayList<Group_Request> allRequests = new ArrayList<>();
    private User user;

    static {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        // Register the JavaTimeModule for handling LocalDate
        objectMapper.registerModule(new JavaTimeModule());
        // Optional: Configure the object mapper to handle dates more flexibly
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }


    Group_Manager(User user) {
        this.groups = new HashMap<>();
        this.user = user;
    }

    public void deletegroup(Group group, User primaryadmin) {
        if (group.getPrimaryadmin().equals(primaryadmin)) {
            groups.remove(group.getName(), group);
            allgroups.remove(group.getName(), group);
        } else
            JOptionPane.showMessageDialog(null, "user not an admin !");
    }
    public void leavegroup(Group group) {
            groups.remove(group.getName(), group);
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

    public void viewSuggestions(User user) {
        loadGroups();
        loadSuggestionGroups();

        for (String key : allgroups.keySet()) {
            if (!isMember(user, allgroups.get(key)) && !suggestions.contains(allgroups.get(key)) && !friendManager.getBlocked().contains(allgroups.get(key).getPrimaryadmin()))
                suggestions.add(allgroups.get(key));
            saveSuggestionGroups();
        }
    }

    public ArrayList<Group> getSuggestions() {
        return suggestions;
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

    public void removeMember(Group group, User member, User primaryadmin, User otheradmin) {
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
        return "Group manager{" +
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

        if (receiver == null) {
            throw new IllegalArgumentException("Receiver cannot be null.");
        }

        if (isMember(this.user, receiver)) {
            JOptionPane.showMessageDialog(null,"Already a member of the group!");
            throw new IllegalArgumentException("Already a member of the group!");
        }

        // Check if a similar request already exists
        for (Group_Request req : receiver.getRequests()) {
            if (req.getSender().equals(this.user) && req.getReceiver().equals(receiver)) {
                if (req.getState() == STATE.PENDING) {
                    JOptionPane.showMessageDialog(null,"Request already pending!");
                    throw new IllegalArgumentException("Request already pending.");
                }
            }
        }

        // Create and send new request
        Group_Request newRequest = (Group_Request) RequestFactory.createRequest(REQUEST.GROUPREQUEST, this.user, receiver.getName());
        updateReceiverRequests(newRequest);
        user.getNotifier().notifyObservers(user, " requested to join group", receiver);


        //////////////to be implemented

    }

    @Override
    ////// Get request to receiver by this user
    public Group_Request getRequest(Object generic_receiver){
        Group receiver = (Group) generic_receiver;

        if (receiver != null && !groups.containsKey(receiver.getName())){
            for (Group_Request request : receiver.getRequests()) {
                if (user.equals(request.getSender()))
                    return request;
            }
        }
        return null;
    }

    @Override
    public void updateReceiverRequests(Request old_request) {
        Group_Request request = (Group_Request) old_request;

        if (request == null || request.getReceiver() == null) {
            throw new IllegalArgumentException("Invalid FriendRequest");
        }

        Group receiver = (Group) request.getReceiver();

        // Prevent duplicates
        if (!receiver.getRequests().contains(request)) {
            receiver.getRequests().add(request);
            allRequests.add(request);
            //saveRequests();
            System.out.println("Request Sent");
        }
    }
}


