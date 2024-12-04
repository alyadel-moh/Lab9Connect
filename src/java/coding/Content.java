package coding;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class Content {
    private String contentId;
    private String authorId;
    private String content;
    @JsonProperty("timestamp")
    private LocalDateTime timeStamp;

    Content(){};

    Content(String contentId,String authorId,String content,LocalDateTime timeStamp){
        this.contentId=contentId;
        this.authorId=authorId;
        this.content=content;
        this.timeStamp=timeStamp;
    }

    public String getContentId(){
        return contentId;
    }
    public String getAuthorId(){
        return authorId;
    }
    public String getContent(){
        return content;
    }
    public LocalDateTime getTimeStamp(){
        return timeStamp;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public void setContent(String content) {
        this.content = content;//contains text for a story or a post - "Optional"a url for an image
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public abstract boolean isExpired();
}
