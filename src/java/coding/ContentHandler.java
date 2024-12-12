package coding;

import coding.Observer.NotificationObserver;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static coding.ENUMS.CONTENT_TYPE.POST;
import static coding.ENUMS.CONTENT_TYPE.STORY;


public class ContentHandler {
    private static ContentHandler instance;
    private final ObjectMapper objectMapper;
    private ArrayList<Posts>posts;
    private final ArrayList<Stories> archieved;
    private ArrayList<Stories>stories;
    private final ArrayList<NotificationObserver> observers;
    private static ArrayList<Posts>allPosts=new ArrayList<Posts>();//static to be shared with all instances to save all posts
    private static ArrayList<Stories>allStories=new ArrayList<Stories>();//will intialize it with empty arraylist once the class is loaded
    private ArrayList<CustomPanel> notifications;

//    private String storyPath="./JsonFilesStories/";
//    private String postsPath="./JsonFilesPosts/";



    public ContentHandler() {
        this.objectMapper = new ObjectMapper();
        this.posts = new ArrayList<>();
        this.archieved = new ArrayList<>();
        this.stories=new ArrayList<>();
        this.observers = new ArrayList<>();
        this.notifications = new ArrayList<>();
        this.objectMapper.registerModule(new JavaTimeModule());//JavaTimeModule helps to write the localTime to file
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);


        // Register the JavaTimeModule to handle LocalDateTime serialization
        objectMapper.registerModule(new JavaTimeModule());
        //to show them as timeStamps
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public ArrayList<Stories> getActiveStories() {
        ArrayList<Stories> activeStories = new ArrayList<>();
        for (Stories story : stories) {
            if (!story.isExpired()) {
                activeStories.add(story);
            }
        }
        return activeStories;
    }

    public void deleteExpiredStories() {
        for(int i=0;i<allStories.size();i++){
            if(allStories.get(i).isExpired()){
                allStories.remove(allStories.get(i));
            }
        }

        for(int i=0;i<stories.size();i++){
            if(stories.get(i).isExpired()){
                stories.remove(stories.get(i));
            }
        }
    }

    public void addNotification(CustomPanel notification) {
        this.notifications.add(notification);
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

    public void addPost(Posts post, User user){
        posts.add(post);
        allPosts.add(post);
        savePosts();
        user.getNotifier().notifyObservers(user, POST, null);
    }

    public void addStory(Stories story, User user){
        stories.add(story);
        allStories.add(story);
        saveStories();
        user.getNotifier().notifyObservers(user, STORY, null);
    }

    public ArrayList<Stories> getStories(){
        return stories;
    }

    public ArrayList<Posts> getPosts(){
        return posts;
    }

    public ArrayList<CustomPanel> getNotifications(){
        return notifications;
    }



    public ArrayList<Stories> getArchieved() {
        return archieved;
    }

    //return arraylist of stories that is related by a certain user by its ID
    public ArrayList<Stories> getStoriesByUserId(String userId){
        ArrayList<Stories>storiesById=new ArrayList<>();
        for(int i=0;i<allStories.size();i++){
            if(allStories.get(i).getAuthorId().equals(userId)){
                storiesById.add(allStories.get(i));
            }
        }
        return storiesById;
    }

    //return Arraylist of posts that is related to a certain user by the user id
    public ArrayList<Posts> getPostsByUserId(String userId){
        ArrayList<Posts>postsById=new ArrayList<>();
        for(int i=0;i<allPosts.size();i++){
            if(allPosts.get(i).getAuthorId().equals(userId)){
                postsById.add(allPosts.get(i));
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

    //load posts of each user according to their id
        public void loadHisOwnPosts(String userId){
        loadPosts();
        ArrayList<Posts> loadedPosts = getPostsByUserId(userId);

        if(!loadedPosts.isEmpty()){
            for(int i=0;i<loadedPosts.size();i++){
                posts.add(loadedPosts.get(i));
            }
        }
    }

        //load stories of each user according to their id

    public void loadHisOwnStories(String userId){
        loadStories();
        ArrayList<Stories>loadedStories=getStoriesByUserId(userId);
        if(!loadedStories.isEmpty()){
            for(int i=0;i<loadedStories.size();i++){
                stories.add(loadedStories.get(i));
            }
        }
    }

        public static ArrayList<Posts> getAllPosts() {
        return allPosts;
    }

    public static ArrayList<Stories> getAllStories() {
        return allStories;
    }

}



