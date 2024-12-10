package coding;

import coding.ENUMS.CONTENT_TYPE;

import java.time.LocalDateTime;

public class ContentFactory {
    public static Content createContent(CONTENT_TYPE type, String contentId, String authorId , String content, LocalDateTime timeStamp){
        return switch (type){
            case STORY -> new Stories(contentId, authorId, content, timeStamp);
            case POST -> new Posts(contentId, authorId, content, timeStamp);
            default -> throw new IllegalArgumentException("Unknown Type: " + type);
        };
    }
}
