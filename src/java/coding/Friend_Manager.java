package coding;

import coding.interfaces.ContentObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Friend_Manager {
    private User user;
    private final ArrayList<FriendRequest> requests;
    private final ArrayList<User> friends;
    private final ArrayList<User> suggestions;
    private final ArrayList<User> blocked;

    Friend_Manager(User user) {
        this.user = user;
        this.requests = new ArrayList<>();
        this.friends = new ArrayList<>();
        this.suggestions = new ArrayList<>();
        this.blocked = new ArrayList<>();
    }

    public void setSuggestions(ArrayList<User> users){
        for(User user : users){
            if (!friends.contains(user) && !blocked.contains(user) && !user.equals(this.user)){
                suggestions.add(user);
            }
        }
    }

    public void sendRequest(User receiver) {
        if (receiver == null) {
            throw new IllegalArgumentException("Receiver cannot be null.");
        }

        if (friends.contains(receiver)) {
            throw new IllegalArgumentException("Friend already added.");
        }

        // Check if a similar request already exists
        for (FriendRequest req : receiver.getRequests()) {
            if (req.getSender().equals(this.user) && req.getReceiver().equals(receiver)) {
                if (req.getState().equalsIgnoreCase("Pending")) {
                    throw new IllegalArgumentException("Request already pending.");
                }
            }
        }

        // Create and send new request
        FriendRequest newRequest = new FriendRequest(this.user, receiver);
        receiver.getManager().setReceivedRequest(newRequest);
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

        request.getReceiver().setRequestState(true);

        // Prevent duplicates
        if (!requests.contains(request)) {
            requests.add(request);
        }
    }

    public void accept(FriendRequest request) {
        if (!requests.contains(request)) {
            JOptionPane.showMessageDialog(null, "Request not found.");
            return;
        }

        User sender = request.getSender();
        if (friends.contains(sender)) {
            JOptionPane.showMessageDialog(null, "Already friends.");
            return;
        }

        suggestions.remove(sender);

        request.accept(); // Update request state
        requests.remove(request); // Remove request
        friends.add(sender); // Add to friends list
        user.getHandler().addObserver((ContentObserver) sender);

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
        user.getHandler().removeObserver((ContentObserver) friend);
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
        user.getHandler().removeObserver((ContentObserver) friend);
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
                    ImageIcon profilePic = new ImageIcon(friend.getProfilepath());
                    if (profilePic != null) {
                        JLabel profileLabel = createCircularLabel(profilePic);
                        activePanel.add(profileLabel);
                    }
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

    public ArrayList<User> getSuggestions() {
        return suggestions;
    }
}