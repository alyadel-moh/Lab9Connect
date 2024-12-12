package coding;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class FriendRequest extends Request {
    public FriendRequest() {
        super();
    }

    public FriendRequest(User sender, User receiver) {
        super(sender, receiver);
    }


    /// ////////////////////////////////////////////////////
    public User getReceiver() {
//        System.out.println(this.receiver);
//        System.out.println(receiver.getClass().getName());
        //since it is generics first time it will be loaded as a linked hashmap then here we change it to user
        if (this.receiver.getClass().getName().equals("java.util.LinkedHashMap")) {
            ArrayList<User> users = Database.getUsers();
            //searching for the user with the same id to link it with the hashmap loaded from the file
            for (User user : users) {
                if (user.getUserId().equals(((LinkedHashMap) receiver).get("userId"))) {
                    this.receiver = user;
                    return user;
                }
            }
        }
            return (User) this.receiver;
    }
}
