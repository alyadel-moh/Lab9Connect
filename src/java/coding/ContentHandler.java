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
    private ArrayList<Posts>posts;
    private final ArrayList<Stories> archieved;
    private ArrayList<Stories>stories;
    private final ArrayList<ContentObserver> observers;
    private static ArrayList<Posts>allPosts=new ArrayList<Posts>();//static to be shared with all instances to save all posts
    private static ArrayList<Stories>allStories=new ArrayList<Stories>();//will intialize it with empty arraylist once the class is loaded

//    private String storyPath="./JsonFilesStories/";
//    private String postsPath="./JsonFilesPosts/";

    public ContentHandler() {
        this.objectMapper = new ObjectMapper();
        this.posts = new ArrayList<>();
        this.archieved = new ArrayList<>();
        this.stories=new ArrayList<>();
        this.observers = new ArrayList<>();
        this.objectMapper.registerModule(new JavaTimeModule());//JavaTimeModule helps to write the localTime to file
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

//        File file = new File(storyPath);
//        if (!file.exists()) {
//            file.mkdirs(); // Create folder if it doesn't exist
//        }
//        File file2 = new File(postsPath);
//        if (!file2.exists()) {
//            file2.mkdirs(); // Create folder if it doesn't exist
//        }

        // Register the JavaTimeModule to handle LocalDateTime serialization
        objectMapper.registerModule(new JavaTimeModule());
        //to show them as timeStamps
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public static synchronized ContentHandler getInstance(String path) {
        if (instance == null) {
            instance = new ContentHandler();
        }
        return instance;
    }

    public ArrayList<Stories> getActiveStories() {
        ArrayList<Stories> activeStories = new ArrayList<>();
        for(int i=0;i<stories.size();i++){
            if(!stories.get(i).isExpired()){
                activeStories.add(stories.get(i));
            }
        }
        return activeStories;
    }

    public void deleteExpiredStories() {
        for(int i=0;i<stories.size();i++){
            if(stories.get(i).isExpired()){
                stories.remove(stories.get(i));
            }
        }
    }



    //Saving all Posts
    public void savePosts(){
        File file=new File("./Posts.json");
        try {
            objectMapper.writeValue(file, allPosts);
        } catch (IOException e) {
            System.out.println("Error happened when trying to save post.");
        }
    }

    //saveAllStories
    public void saveStories(){
        File file=new File("./Stories.json");
        try {
            objectMapper.writeValue(file, allStories);
        } catch (IOException e) {
            System.out.println("Error happened when trying to save post.");
        }
    }



    public void addPost(Posts post){
        posts.add(post);
        allPosts.add(post);
//        notifyObservers();
    }
    public void addStory(Stories story){
        stories.add(story);
        allStories.add(story);
//        notifyObservers();
    }

    public ArrayList<Stories> getStories(){
        return stories;
    }

    public ArrayList<Posts> getPosts(){
        return posts;
    }

    public void addObserver(ContentObserver observer){
        observers.add(observer);
    }

    public void removeObserver(ContentObserver observer) {
        observers.remove(observer);
    }

//    private void notifyObservers(){
//        for (ContentObserver observer : observers){
//            observer.update(contents);
//        }
//    }

    public ArrayList<Stories> getArchieved() {
        return archieved;
    }

    //return arraylist of stories that is related by a certain user by its ID
    public ArrayList<Stories> getStoriesByUserId(String userId){
        ArrayList<Stories>storiesById=new ArrayList<>();
        for(int i=0;i<allStories.size();i++){
            if(allStories.get(i).getAuthorId().equals(userId)){
                storiesById.add(stories.get(i));
            }
        }
        return storiesById;
    }

    //return Arraylist of posts that is related to a certain user by the user id
    public ArrayList<Posts> getPostsByUserId(String userId){
        ArrayList<Posts>postsById=new ArrayList<>();
        for(int i=0;i<allPosts.size();i++){
            if(allPosts.get(i).getAuthorId().equals(userId)){
                postsById.add(posts.get(i));
            }
        }
        return postsById;
    }

    //load All posts
    public void loadPosts() {
        File file = new File("./Posts.json");
        if (file.exists()) {
            try {
                allPosts = objectMapper.readValue(file, new TypeReference<ArrayList<Posts>>() {});
            } catch (IOException e) {
                System.out.println("Error occurred while loading posts.");
                System.out.println(e);
            }
        } else {
            System.out.println("Posts file not found. Initializing an empty list.");
            allPosts = new ArrayList<>();
        }
    }

    //load all stories
    public void loadStories() {
        File file = new File("./Stories.json");
        if (file.exists()) {
            try {
                allStories = objectMapper.readValue(file, new TypeReference<ArrayList<Stories>>() {});
            } catch (IOException e) {
                System.out.println("Error occurred while loading stories.");
            }
        } else {
            System.out.println("Stories file not found. Initializing an empty list.");
            allStories = new ArrayList<>();
        }
    }

}



