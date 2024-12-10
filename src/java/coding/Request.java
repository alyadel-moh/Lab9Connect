package coding;

import coding.ENUMS.State;

public class Request<T> {
    private State state;
    private User sender;
    private T receiver;

    // Default constructor
    public Request() {
        this.state = State.PENDING; // Default state
    }

    // Parameterized constructor
    public Request(User sender, T receiver) {
        this();
        this.sender = sender;
        this.receiver = receiver;
    }


    // Getter for state
    public State getState() {
        return state;
    }

    // Setter for state
    public void setState(State state) {
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
        this.state = State.DECLINED;
    }

    // Accept the request
    public void accept() {
        this.state = State.ACCEPTED;
    }
}
