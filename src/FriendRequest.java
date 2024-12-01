public class FriendRequest {
    private String state;
    private User sender;
    private User receiver;

    public FriendRequest(User sender,User receiver) {
        this.state = "Pending";
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public User getSender(){
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void decline() {
        state = "Declined";
    }

    public void accept() {
        state = "Accepted";

    }
}
