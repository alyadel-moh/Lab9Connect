package coding;

import java.time.LocalDateTime;

public class Posts extends Content{

    Posts(String contentId, String authorId, String content, LocalDateTime timeStamp){
        super(contentId,authorId,content,timeStamp);
    }

    @Override
    public boolean isExpired(){
        return false;
    }
}
