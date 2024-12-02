package coding;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class ContentHandler {
    private ObjectMapper objectMapper;//A tool to convert from java objects to json
    private String path;//the location where we store the json files at
    private User user;
    private ArrayList<Content> contents;

    public ContentHandler(User user, String path) {
        this.user = user;
        this.objectMapper = new ObjectMapper();
        this.contents = new ArrayList<>();
        this.path = path;

        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs(); // Create folder if it doesn't exist
        }
    }

    public ArrayList<Stories> getActiveStories() {
        ArrayList<Stories> activeStories = new ArrayList<>();
        for (Content content : contents) {
            if (content instanceof Stories && !content.isExpired()) {
                activeStories.add((Stories) content);
            }
        }
        return activeStories;
    }

    public void deleteExpiredStories() {
        contents.removeIf(content -> content instanceof Stories && content.isExpired());
    }


    public void saveContent(User user){
        File file = new File(path + "/" + user.getUserName() + ".json");//Creates a json file with the name of each user
        try {
            //Writes the list of contents of each user in the json file
            objectMapper.writeValue(file, user);
        } catch (IOException e) {
            System.out.println("Error happened while trying to write to the file");
        }
    }

    public void setPath(String path){
        this.path = path;
    }

    public ArrayList<Content> getContents(){
        return contents;
    }

    public void addContent(Content content){
        contents.add(content);//add posts or stories to the arraylist
    }

}
