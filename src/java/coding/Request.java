package coding;

import coding.ENUMS.STATE;

public class Request<T> {
    private STATE state;
    private User sender;
    protected T receiver;

    // Default constructor
    public Request() {
        this.state = STATE.PENDING; // Default state
    }

    // Parameterized constructor
    public Request(User sender, T receiver) {
        this();
        this.sender = sender;
        this.receiver = receiver;
    }


    // Getter for state
    public STATE getState() {
        return state;
    }

    // Setter for state
    public void setState(STATE state) {
        if (state == null) {
            throw new IllegalArgumentException("State cannot be null.");
        }
        this.state = state;
    }

    // Getter for sender
    public User getSender() {
        return sender;
    }

    // Getter for receiver
    public T getReceiver() {
        return receiver;
    }

    // Decline the request
    public void decline() {
        this.state = STATE.DECLINED;
    }

    // Accept the request
    public void accept() {
        this.state = STATE.ACCEPTED;
    }
}
