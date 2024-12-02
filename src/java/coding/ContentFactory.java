package coding;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ContentFactory {
    public static Content createContent(String type, String contentId,String authorId ,String content, LocalDateTime timeStamp){
        return switch (type.toLowerCase()){
            case "story" -> new Stories(contentId, authorId, content, timeStamp);
            case "post" -> new Posts(contentId, authorId, content, timeStamp);
            default -> throw new IllegalArgumentException("Unknown Type: " + type);
        };
    }
}
