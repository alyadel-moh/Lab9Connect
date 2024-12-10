package coding;

import coding.ENUMS.REQUEST;
import coding.ENUMS.STATE;
import coding.Interfaces.Requester;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Friend_Manager implements Requester{
    private final User user;
    private final ArrayList<FriendRequest> requests;
    private final ArrayList<User> friends;
    private final ArrayList<User> suggestions;
    private final ArrayList<User> blocked;
    private final ObjectMapper objectMapper;
    private static ArrayList<FriendRequest>allRequests=new ArrayList<>();

    Friend_Manager(User user) {
        this.user = user;
        this.requests = new ArrayList<>();
        this.friends = new ArrayList<>();
        this.suggestions = new ArrayList<>();
        this.blocked = new ArrayList<>();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());//JavaTimeModule helps to write the localTime to file
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public void setFriends(String userId){
        ArrayList<Friend>loadedFriends=user.getFriendHandler().getFriendsByUserId(user.getUserId());
        for (Friend loadedFriend : loadedFriends) {
            User loadedUser = Database.findUserById(loadedFriend.getFriendId());
            friends.add(loadedUser);
        }
    }

    public void saveRequests(){
        File file=new File("./FriendRequests.json");
        try {
            objectMapper.writeValue(file, allRequests);
        } catch (IOException e) {
            System.out.println("Error happened when trying to save request.");
        }
    }

    public void loadRequests(){
        File file = new File("./FriendRequests.json");
        if (file.exists()) {
            try {
                allRequests = objectMapper.readValue(file, new TypeReference<ArrayList<FriendRequest>>() {});
            } catch (IOException e) {
                System.out.println("Error occurred while loading posts.");
                System.out.println(e);
            }
        } else {
            System.out.println("Request file not found. Initializing an empty list.");
            allRequests = new ArrayList<>();
        }
    }

    //load posts of each user according to their id
    public void loadHisOwnRequests(String userId){
        loadRequests();
        ArrayList<FriendRequest> loadedRequests = getFriendRequestByUserId(userId);

        if(loadedRequests != null && !loadedRequests.isEmpty()){
            requests.addAll(loadedRequests);
        }

        System.out.println("his own requests size "+requests.size());
        System.out.println("all requests size "+allRequests.size());
    }

    public ArrayList<FriendRequest> getFriendRequestByUserId(String userId) {
        ArrayList<FriendRequest> friendRequestsByUserId = new ArrayList<>();

        for (FriendRequest request : allRequests) {
            if (request.getReceiver() instanceof User receiver) {
                if (receiver.getUserId().equals(userId)) {
                    friendRequestsByUserId.add(request);
                }
            }
            return friendRequestsByUserId;
        }
        return null;
    }

    public void setSuggestions(ArrayList<User> users){
        for(User user : users){
            if (!friends.contains(user) && !blocked.contains(user) && !user.equals(this.user) && !suggestions.contains(user)){
                suggestions.add(user);
            }
        }
    }

    public void cancelRequest(User receiver){
        if (receiver == null) {
            throw new IllegalArgumentException("Receiver cannot be null.");
        }

        if (receiver.getManager().getFriends().contains(this.user)){
            throw new IllegalArgumentException("Already Friends");
        }

        FriendRequest request = receiver.getManager().getRequestbySender(this.user, receiver);

        if (request == null){
            throw new IllegalArgumentException("Request Doesn't exist anymore!");
        }

        if (request.getState() == STATE.PENDING){
            receiver.getManager().getRequest(receiver).setState(STATE.CANCELLED);
            receiver.getManager().getRequests().remove(request);
            FriendHandler.getAllFriendReq().remove(request);
        }


    }

    public FriendRequest getRequest(User receiver) {
        if (receiver != null && !friends.contains(receiver)) {
            for (FriendRequest request : requests){
                if (receiver.equals(request.getReceiver()))
                    return request;
            }
        }
        return null;
    }

    public ArrayList<User> getBlocked() {
        return blocked;
    }

    public void sendRequest(Object general_receiver) {
        User receiver = (User)general_receiver;

        if (receiver == null) {
            throw new IllegalArgumentException("Receiver cannot be null.");
        }

        if (friends.contains(receiver)) {
            throw new IllegalArgumentException("Friend already added.");
        }

        // Check if a similar request already exists
        for (FriendRequest req : receiver.getRequests()) {
            if (req.getSender().equals(this.user) && req.getReceiver().equals(receiver)) {
                if (req.getState() == STATE.PENDING) {
                    throw new IllegalArgumentException("Request already pending.");
                }
            }
        }

        // Create and send new request
        FriendRequest newRequest = (FriendRequest) RequestFactory.createRequest(REQUEST.FRIENDREQUEST, this.user, receiver.getUserId());
        receiver.getManager().setReceivedRequest(newRequest);
        user.getNotifier().notifyObservers(user, " sent you a friend request", receiver);
    }

    public FriendRequest getRequestbySender(User sender,User receiver){
        for (FriendRequest request : receiver.getRequests()){
            if (sender.equals(request.getSender()))
                return request;
        }
        JOptionPane.showMessageDialog(null,"Request Not Found");
        return null;
    }

    public void setReceivedRequest(FriendRequest request) {
        if (request == null || request.getReceiver() == null) {
            throw new IllegalArgumentException("Invalid FriendRequest");
        }

        ((User)request.getReceiver()).setRequestState(true);

        // Prevent duplicates
        if (!requests.contains(request)) {
            requests.add(request);
            allRequests.add(request);
            saveRequests();
            System.out.println("added");
        }
    }

    public void accept(FriendRequest request) {
        if (!requests.contains(request)) {
            JOptionPane.showMessageDialog(null, "Request not found.");
            return;
        }

        User sender = request.getSender();
        User receiver = (User) request.getReceiver();

        if (friends.contains(sender)) {
            JOptionPane.showMessageDialog(null, "Already friends.");
            return;
        }

        suggestions.remove(sender);

        request.accept(); // Update request state
        requests.remove(request); // Remove request
        allRequests.remove(request);//remove from allRequests

        saveRequests();
        friends.add(sender); // Add to friends list

        sender.getManager().getFriends().add(receiver);

        sender.createObserver();
        user.getNotifier().addObserver(sender.getObserver());

        user.getFriendHandler().addFriend(((User) request.getReceiver()).getUserId(),request.getSender().getUserId());
        user.getFriendHandler().saveFriends();

        if (requests.isEmpty()) {
            this.user.setRequestState(false);
        }

        // Notify success
        JOptionPane.showMessageDialog(null, sender.getUserName() + " is now your friend!");
    }

    public void decline(FriendRequest request) {
        if (!requests.contains(request)) {
            JOptionPane.showMessageDialog(null, "Request not found.");
            return;
        }

        request.decline(); // Update request state
        requests.remove(request); // Remove request
        allRequests.remove(request);
        saveRequests();

        User sender = request.getSender();
        if (!suggestions.contains(sender))
            suggestions.add(sender);

        if (requests.isEmpty()) {
            this.user.setRequestState(false);
        }
    }

    public void block(User friend) {
        if (friend == null) {
            throw new IllegalArgumentException("Friend doesn't exist");
        }

        friends.remove(friend);
        user.getFriendHandler().deleteFriend(user,friend);
        friend.getManager().getFriends().remove(user);
        user.getNotifier().removeObserver(friend.getObserver());
        blocked.add(friend);
    }

    public void unblock(User account){
        if(account == null){
            throw new IllegalArgumentException("Account doesn't exist");
        }

        if(this.blocked.contains(account)){
            this.blocked.remove(account);
            suggestions.add(account);
            JOptionPane.showMessageDialog(null, "Account has been unblocked");
        }else{
            JOptionPane.showMessageDialog(null, "Account was not unblocked");
        }
    }

    public void remove(User friend) {
        if (friend == null) {
            throw new IllegalArgumentException("Friend doesn't exist");
        }

        friends.remove(friend);
//        friend.getManager().getFriends().remove(user);
        user.getFriendHandler().deleteFriend(user,friend);
        user.getNotifier().removeObserver(friend.getObserver());
        suggestions.add(friend);
    }

    public void DisplayStatus(Homepage page) {
        UI ui = new UI();
        ui.displayStatus(friends);
        page.setActivePanel(ui.getActivePanel()); // Update the UI component in the `Feedpage`
    }


    public ArrayList<FriendRequest> getRequests() {
        return requests;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    public ArrayList<User> getSuggestions() {
        return suggestions;
    }

    /////////////////////////  UI class ///////////////////////////////////////
    public static class UI {
        private final JPanel activePanel;

        public UI() {
            activePanel = new JPanel();
            activePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            activePanel.setBackground(Color.WHITE); // Optional: Customize background color
        }

        public void displayStatus(ArrayList<User> friends) {
            activePanel.removeAll(); // Clear previous items

            for (User friend : friends) {
                if ("online".equalsIgnoreCase(friend.getStatus())) {
                    // Get friend's profile picture
                    ImageIcon profilePic = new ImageIcon(friend.getProfilePath());
                    JLabel profileLabel = createCircularLabel(profilePic);
                    activePanel.add(profileLabel);
                }
            }

            activePanel.revalidate();
            activePanel.repaint();
        }

        private JLabel createCircularLabel(ImageIcon imageIcon) {
            int size = 50; // Desired size for profile picture
            Image scaledImage = imageIcon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
            BufferedImage circularImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2 = circularImage.createGraphics();
            g2.setClip(new Ellipse2D.Double(0, 0, size, size));
            g2.drawImage(scaledImage, 0, 0, null);
            g2.dispose();

            return new JLabel(new ImageIcon(circularImage));
        }

        public JPanel getActivePanel() {
            return activePanel;
        }
    }


}
