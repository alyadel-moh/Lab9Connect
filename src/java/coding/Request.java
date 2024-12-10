package coding;

public class Request<T> {
    private String state;
    private User sender;
    private T receiver;

    public Request(){

    }

    public Request(User sender, T receiver) {
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

    public T getReceiver() {
        return receiver;
    }

    public void decline() {
        state = "Declined";
    }

    public void accept() {
        state = "Accepted";
    }
}
