package coding;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class User {
    private ImageIcon profile;
    private ImageIcon cover;
    private String bio;
    private final String userId;
    private String password;
    private final String userName;
    private String email;
    private final LocalDate dateOfBirth;
    private String status;
    private final JFileChooser jFileChooser = new JFileChooser();

    private boolean recievedRequest;
    private final ArrayList<FriendRequest> requests;
    private final ArrayList<User> friends;
    private final ArrayList<User> suggestions;
    private final ArrayList<User> blocked;

    public User(String userId, String password, String userName, String email, LocalDate dateOfBirth, String status) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.status = status;
        this.recievedRequest = false;
        this.requests = new ArrayList<>();
        this.friends = new ArrayList<>();
        this.suggestions = new ArrayList<>();
        this.blocked = new ArrayList<>();
    }

    public void setCover() {
        int response = jFileChooser.showOpenDialog(null);
        if(response == JFileChooser.APPROVE_OPTION){
            ImageIcon image = new ImageIcon(jFileChooser.getSelectedFile().getAbsolutePath());
            this.cover = image;
        }
    }

    public void setProfile() {
        int response = jFileChooser.showOpenDialog(null);
        if(response == JFileChooser.APPROVE_OPTION){
            ImageIcon image = new ImageIcon(jFileChooser.getSelectedFile().getAbsolutePath());
            this.profile = image;
        }
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void sendRequest(User receiver) {
        if (receiver == null) {
            throw new IllegalArgumentException("Receiver cannot be null.");
        }

        if (friends.contains(receiver)) {
            throw new IllegalArgumentException("Friend already added.");
        }

        // Check if a similar request already exists
        for (FriendRequest req : receiver.requests) {
            if (req.getSender().equals(this) && req.getReceiver().equals(receiver)) {
                if (req.getState().equalsIgnoreCase("Pending")) {
                    throw new IllegalArgumentException("Request already pending.");
                }
            }
        }

        // Create and send new request
        FriendRequest newRequest = new FriendRequest(this, receiver);
        receiver.setReceivedRequest(newRequest);
    }

    public void setReceivedRequest(FriendRequest request) {
        if (request == null || request.getReceiver() == null) {
            throw new IllegalArgumentException("Invalid FriendRequest");
        }

        request.getReceiver().recievedRequest = true;

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

        if (requests.isEmpty()) {
            recievedRequest = false;
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
            recievedRequest = false;
        }
    }

    public void Block(User friend){
        if (friend == null){
            throw new IllegalArgumentException("Friend doesn't exist");
        }

        friends.remove(friend);
        blocked.add(friend);
    }

    public void Remove(User friend){
        if (friend == null){
            throw new IllegalArgumentException("Friend doesn't exist");
        }

        friends.remove(friend);
        suggestions.add(friend);

    }




    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName(){
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}