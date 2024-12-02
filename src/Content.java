import java.util.Date;

public abstract class Content {
    private String contentId;
    private String authorId;
    private String content;
    private Date timeStamp;

    public String getContentId(){
        return contentId;
    }
    public String getAuthorId(){
        return authorId;
    }
    public String getContent(){
        return content;
    }
    public Date getDate(){
        return timeStamp;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}
