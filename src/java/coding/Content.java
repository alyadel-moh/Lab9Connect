package coding;

import java.time.LocalDate;

public abstract class Content {
    private String contentId;
    private String authorId;
    private String content;
    private LocalDate timeStamp;

    public String getContentId(){
        return contentId;
    }
    public String getAuthorId(){
        return authorId;
    }
    public String getContent(){
        return content;
    }
    public LocalDate getDate(){
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

    public void setTimeStamp(LocalDate timeStamp) {
        this.timeStamp = timeStamp;
    }

    public abstract boolean isExpired();
}
