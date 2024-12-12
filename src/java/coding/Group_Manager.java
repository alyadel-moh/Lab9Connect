package coding;

import coding.ENUMS.GROUP_STATUS;
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

import static coding.ENUMS.NOTIFICATIONS.GROUP.POST;
import static coding.ENUMS.NOTIFICATIONS.GROUP.ADDED;
import static coding.ENUMS.NOTIFICATIONS.GROUP.CHANGE_STATUS;
import static coding.ENUMS.NOTIFICATIONS.REQUEST.SENDGROUP;


public class Group_Manager implements Requester {
    private static Map<String, Group> allgroups = new HashMap<>();
    private static ArrayList<Group_Request> allRequests = new ArrayList<>();
    static final ObjectMapper objectMapper = new ObjectMapper();

    private Map<String, Group> groups;
    private ArrayList<Group> suggestions;

    private Posts post;
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
        this.suggestions = new ArrayList<>();
        this.user = user;
    }

    public void deletegroup(Group group, User primaryadmin) {
        if (group.getPrimaryAdmin().getUserId().equals(primaryadmin.getUserId())) {
            System.out.println("Deleting group: " + group.getName());
            groups.remove(group.getName(), group);
            allgroups.remove(group.getName(), group);
            this.suggestions.removeIf(g -> g.getName().equals(group.getName()));
            System.out.println("Groups left after deletion: " + groups.keySet());
            System.out.println("All groups left after deletion: " + allgroups.keySet());
            System.out.println("Suggestions left after deletion: " + suggestions);
            saveSuggestionGroups();
            saveGroups();
            JOptionPane.showMessageDialog(null, "Group deleted successfully!");
        } else
            JOptionPane.showMessageDialog(null, "user not an admin !");
    }
    public void leavegroup(Group group) {
            groups.remove(group.getName(), group);
    }

    public void deletepost(Group group, User primaryAdmin, User otherAdmin, Posts post) {
        if ((group.getOtherAdmins().contains((Member) otherAdmin) || group.getPrimaryAdmin().equals(primaryAdmin)) && group.getPosts().contains(post)) {
            group.getPosts().remove(post);
            saveGroups();
        } else
            JOptionPane.showMessageDialog(null, "user not an admin !");
    }

    public boolean isMember(User user, Group group) {
        if (user instanceof Member) {
            if (group.getMembers().contains((Member) user) && !(((Member) user).getGroup_status() == GROUP_STATUS.NOTMEMBER))
                return true;
            else
                System.out.println("user not a member !");
            return false;
        }
        return false;
    }

    public void viewSuggestions(User user) {
//        loadGroups();
//        loadSuggestionGroups();

        for (String key : allgroups.keySet()) {
            Group group = allgroups.get(key);
            if (!isMember(user, group)
                    && suggestions.stream().noneMatch(g -> g.getName().equals(group.getName()))
                    && !user.getManager().getBlocked().contains(group.getPrimaryAdmin())){                suggestions.add(group);

            }

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

    public synchronized void addGroup(Group group) {
        if (allgroups.containsKey(group.getName())) {
            System.out.println("Group already exists!");
            return;
        }
        allgroups.put(group.getName(), group);
        this.suggestions.add(group);
        saveGroups();
        saveSuggestionGroups();
    }
//    public void addGroup(Group group) {
//        loadGroups();
//        if (allgroups.containsKey(group.getName())) {
//            System.out.println("Group already exists!");
//            return;
//        }
//        if (this.suggestions == null) {
//            System.out.println("Suggestions list is null. Initializing...");
//            this.suggestions = new ArrayList<>();
//        }
//        if (group != null) {
//            groups.put(group.getName(), group);
//            allgroups.put(group.getName(), group);
//            this.suggestions.add(group);
//            saveGroups();
//            saveSuggestionGroups();
//        }
//    }

    public void removeMember(Group group, User member, User primaryAdmin, User otherAdmin) {
        if (group.getPrimaryAdmin().equals(primaryAdmin))
            group.getMembers().remove((Member) member);

        else if (group.getOtherAdmins().contains((Member) otherAdmin)) {
            if (!group.getOtherAdmins().contains((Member) member) && !group.getPrimaryAdmin().equals(primaryAdmin)) {
                group.getMembers().remove(member);
                saveGroups();
            } else
                JOptionPane.showMessageDialog(null, "not accessed to remove an admin");
        }
    }

    public void addMember(Group group, User member){
        if (group == null || member == null)
            return;

        if (member instanceof Member castedUser) {
            if (isMember(member, group))
                return;

            castedUser.setGroup_status(GROUP_STATUS.NORMAL);
            group.addMember(castedUser);
            saveGroups();
            //group.getMembers().add(castedUser);
        }
    }

    @Override
    public String toString() {
        return "Group manager{" +
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

    public synchronized void saveGroups() {

        try {
            objectMapper.writeValue(new File("Groups.json"), allgroups);
            System.out.println("Groups saved successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void loadGroups() {
        File file = new File("Groups.json");
        if (!file.exists()) {
            System.out.println("Groups file not found. Initializing an empty group list.");
            allgroups = new HashMap<>();
            groups = new HashMap<>();
            return;
        }
        try {
            allgroups = objectMapper.readValue(file,
                    objectMapper.getTypeFactory().constructMapType(Map.class, String.class, Group.class));
            System.out.println("Groups loaded successfully.");
            groups = new HashMap<>(allgroups);
        } catch (IOException e) {
            e.printStackTrace();
            allgroups = new HashMap<>();
            groups = new HashMap<>();
        }
    }

    public void saveSuggestionGroups() {
        try {
            ArrayList<Group> uniqueSuggestions = new ArrayList<>();
            for (Group group : suggestions) {
                if (uniqueSuggestions.stream().noneMatch(g -> g.getName().equals(group.getName()))) {
                    uniqueSuggestions.add(group);
                }
            }
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
            ArrayList<Group> loadedSuggestions = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Group.class));
            // Remove duplicates while loading
            this.suggestions = new ArrayList<>();
            for (Group group : loadedSuggestions) {
                if (this.suggestions.stream().noneMatch(g -> g.getName().equals(group.getName()))) {
                    this.suggestions.add(group);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            this.suggestions = new ArrayList<>();
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
        user.getNotifier().notifyObservers(user, SENDGROUP, receiver.getPrimaryAdmin().getGroup_observer());

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

    @Override
    public void cancelRequest(Object generic_receiver){
        Group receiver = (Group) generic_receiver;

        if (receiver == null) {
            throw new IllegalArgumentException("Receiver cannot be null.");
        }

        if (isMember(this.user, receiver)) {
            JOptionPane.showMessageDialog(null,"Already a member of the group!");
            throw new IllegalArgumentException("Already a member of the group!");
        }

        Group_Request request = getRequest(receiver);

        if (request == null){
            throw new IllegalArgumentException("Request Doesn't exist anymore!");
        }

        if (request.getState() == STATE.PENDING){
            for (Group_Request group_request : receiver.getRequests()){
                if (group_request.equals(request)){
                    group_request.setState(STATE.CANCELLED);
                    receiver.getRequests().remove(group_request);
                    System.out.println("Friend Request Cancelled");
                }
            }
        }
    }

        public void promote(Group group,User member){
            if(group.getMembers().contains((Member) member)){
                switch (((Member) member).getGroup_status()){
                    case NORMAL -> ((Member) member).setGroup_status(GROUP_STATUS.ADMIN);
                    default -> throw new RuntimeException("Current Member status is not promotable");
                }
            }
        }

        public void delegate(Group group,User member){
            if(group.getMembers().contains((Member) member)){
                switch (((Member) member).getGroup_status()){
                    case ADMIN -> ((Member) member).setGroup_status(GROUP_STATUS.NORMAL);
                    default -> throw new RuntimeException("Cannot delegate Member!");
                }
            }
        }

        public void filterGroup(Group group){
            if (group != null && !group.getMembers().isEmpty()){
                group.getMembers().removeIf(member -> member.getGroup_status() == GROUP_STATUS.NOTMEMBER);
            }

        }


}


