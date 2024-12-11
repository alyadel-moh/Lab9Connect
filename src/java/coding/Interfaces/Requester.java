package coding.Interfaces;

import coding.FriendRequest;
import coding.Request;

public interface Requester<T>{
    public void sendRequest(T generic_receiver);
    public Request getRequest(T generic_receiver);
    public void updateReceiverRequests(Request request);
}
