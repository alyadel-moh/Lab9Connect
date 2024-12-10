package coding;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Stories extends Content{

    Stories(){};

    Stories(String contentId, String authorId, String content, LocalDateTime timeStamp){
        super(contentId,authorId,content,timeStamp);
    }

    @Override
    @JsonIgnore
    public boolean isExpired(){
        LocalDateTime currentTime = LocalDateTime.now();//Gives us the current time
        long difference = ChronoUnit.HOURS.between(getTimeStamp(), currentTime);//gets the difference between the time of upload of story and the time now
        System.out.println("difference in time is "+difference);
        if(difference>=24){
            return true;//indicating that story has expired
        }
        return false;
    }
}
