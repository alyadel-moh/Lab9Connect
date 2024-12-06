package coding;

import java.time.LocalDateTime;

public abstract class Friend {
    private String userId;
    private String friendId;

    Friend(){};

    Friend(String userId,String friendId){
        this.userId =userId;
        this.friendId = friendId;
    }

    public String getUserId(){
        return userId;
    }
    public String getFriendId(){
        return friendId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

}

