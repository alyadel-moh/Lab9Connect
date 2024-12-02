import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Stories extends Content{

    @Override
    public boolean isExpired(){
        LocalDateTime currentTime = LocalDateTime.now();//Gives us the current time
        long difference = ChronoUnit.HOURS.between(getDate(), currentTime);//gets the difference between the time of upload of story and the time now
        if(difference>=24){
            return true;//indicating that story has expired
        }
        return false;
    }
}
