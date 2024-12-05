package coding;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public class Posts extends Content{

    Posts(){}

    Posts(String contentId, String authorId, String content, LocalDateTime timeStamp){
        super(contentId,authorId,content,timeStamp);
    }

    @Override
    @JsonIgnore

    public boolean isExpired(){
        return false;
    }
}
