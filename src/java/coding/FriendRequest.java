package coding;

public class FriendRequest extends Request{
    public FriendRequest(){
        super();
    }

    public FriendRequest(User sender, User receiver) {
        super(sender, receiver);
    }

}
