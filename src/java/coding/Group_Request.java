package coding;

public class Group_Request extends Request{
    Group_Request(){
        super();
    }

    Group_Request(User sender, Group receiver){
        super(sender, receiver);
    }

    public Group getReceiver(){
        return (Group) receiver;
    }
}
