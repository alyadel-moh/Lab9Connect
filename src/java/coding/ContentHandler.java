package coding;

import coding.interfaces.ContentObserver;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class ContentHandler {
    private static ContentHandler instance;
    private final ObjectMapper objectMapper;
    private final ArrayList<Content> contents;
    private final ArrayList<Stories> archieved;
    private final ArrayList<ContentObserver> observers;
    private String path="./JsonFilesContent/";

    public ContentHandler() {
        this.objectMapper = new ObjectMapper();
        this.contents = new ArrayList<>();
        this.archieved = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.objectMapper.registerModule(new JavaTimeModule());//JavaTimeModule helps to write the localTime to file
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs(); // Create folder if it doesn't exist
        }
    }

    public static synchronized ContentHandler getInstance(String path) {
        if (instance == null) {
            instance = new ContentHandler();
        }
        return instance;
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
        for (Content content : contents) {
            if (content instanceof Stories && content.isExpired()){
                contents.remove(content);
                archieved.add((Stories) content);
            }
            
        }
    }


    public void saveContent(User user){
        File file = new File(path  + user.getUserName() + ".json");//Creates a json file with the name of each user
        try {
            //Writes the list of contents of each user in the json file
            objectMapper.writeValue(file, user);
        } catch (IOException e) {
            System.out.println("Error happened while trying to write to the file");
        }
    }

    public void setPath(String path) {
        this.path = path;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }


    public void addContent(Content content){
        contents.add(content);//add posts or stories to the arraylist
        notifyObservers();
    }

    public ArrayList<Content> getContents(){
        return contents;
    }

    public void addObserver(ContentObserver observer){
        observers.add(observer);
    }

    public void removeObserver(ContentObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(){
        for (ContentObserver observer : observers){
            observer.update(contents);
        }
    }

    public ArrayList<Stories> getArchieved() {
        return archieved;
    }
}


