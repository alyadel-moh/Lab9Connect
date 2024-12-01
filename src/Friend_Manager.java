import javax.swing.*;
import java.util.ArrayList;

public class Friend_Manager {
    private User user;
    private final ArrayList<FriendRequest> requests;
    private final ArrayList<User> friends;
    private final ArrayList<User> suggestions;
    private final ArrayList<User> blocked;

    Friend_Manager(User user){
        this.user = user;
        this.requests = new ArrayList<>();
        this.friends = new ArrayList<>();
        this.suggestions = new ArrayList<>();
        this.blocked = new ArrayList<>();
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

    public void block(User friend){
        if (friend == null){
            throw new IllegalArgumentException("Friend doesn't exist");
        }

        friends.remove(friend);
        blocked.add(friend);
    }

    public void remove(User friend){
        if (friend == null){
            throw new IllegalArgumentException("Friend doesn't exist");
        }

        friends.remove(friend);
        suggestions.add(friend);

    }

    public void DisplayStatus(Feedpage page) {
        FriendManagerUI ui = new FriendManagerUI();
        ui.displayStatus(friends);
        page.setActivePanel(ui.getActivePanel()); // Update the UI component in the `Feedpage`
    }


    public ArrayList<FriendRequest> getRequests() {
        return requests;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }


}
