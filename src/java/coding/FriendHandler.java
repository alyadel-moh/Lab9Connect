package coding;

import coding.interfaces.ContentObserver;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FriendHandler {
    //private static ContentHandler instance;
    private final ObjectMapper objectMapper;
    private ArrayList<Friends> friends;
    private ArrayList<FriendReq> friendReqs;
    private ArrayList<FriendSuggestions> friendSuggestions;
    private static ArrayList<FriendReq> allFriendReq=new ArrayList<FriendReq>();//static to be shared with all instances to save all posts
    private static ArrayList<Friends> allFriends=new ArrayList<Friends>();//will intialize it with empty arraylist once the class is loaded
    private static ArrayList<FriendSuggestions> allFriendSuggestions=new ArrayList<FriendSuggestions>();

//    private String storyPath="./JsonFilesStories/";
//    private String postsPath="./JsonFilesPosts/";

    public FriendHandler() {
        this.objectMapper = new ObjectMapper();
        this.friends = new ArrayList<>();
        this.friendReqs = new ArrayList<>();
        this.friendSuggestions =new ArrayList<>();
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

    //Saving all friends
    public void saveFriends(){
        File file=new File("./Friends.json");
        try {
            objectMapper.writeValue(file, allFriends);
        } catch (IOException e) {
            System.out.println("Error happened when trying to save post.");
        }
    }

    //saveAllfriendReq
    public void saveFriendReqs(){
        File file=new File("./FriendRequests.json");
        try {
            objectMapper.writeValue(file, allFriendReq);
        } catch (IOException e) {
            System.out.println("Error happened when trying to save post.");
        }
    }
    public void saveFriendSuggestions(){
        File file=new File("./FriendSuggestions.json");
        try {
            objectMapper.writeValue(file, allFriendSuggestions);
        } catch (IOException e) {
            System.out.println("Error happened when trying to save post.");
        }
    }

    // Add a friend
    public void addFriend(String userId, String friendId) {
        if (!isFriend(userId, friendId)) {
            Friends newFriend = new Friends(userId, friendId);
            friends.add(newFriend);
            allFriends.add(newFriend);
            saveFriends();
        }
    }
    // Add a friend request
    public void addFriendRequest(String userId, String friendId) {
        if (!isFriend(userId, friendId) && !hasPendingRequest(userId, friendId)) {
            FriendReq newRequest = new FriendReq(userId, friendId);
            friendReqs.add(newRequest);
            allFriendReq.add(newRequest);
            saveFriendReqs();
        }
    }
    // Add a friend suggestion
    public void addFriendSuggestion(String userId, String suggestionId) {
        if (!isFriend(userId, suggestionId) && !hasPendingRequest(userId, suggestionId)) {
            FriendSuggestions suggestion = new FriendSuggestions(userId, suggestionId);
            if (!isSuggested(userId, suggestionId)) {
                friendSuggestions.add(suggestion);
                allFriendSuggestions.add(suggestion);
                saveFriendSuggestions();
            }
        }
    }
    // Check if userId is friends with friendId
    public boolean isFriend(String userId, String friendId) {
        return allFriends.stream().anyMatch(friend ->
                (friend.getUserId().equals(userId) && friend.getFriendId().equals(friendId)) ||
                        (friend.getUserId().equals(friendId) && friend.getFriendId().equals(userId))
        );
    }

    // Check if a friend request is pending
    public boolean hasPendingRequest(String userId, String friendId) {
        return allFriendReq.stream().anyMatch(request ->
                request.getUserId().equals(userId) && request.getFriendId().equals(friendId)
        );
    }

    // Check if a user is already suggested
    public boolean isSuggested(String userId, String suggestionId) {
        return allFriendSuggestions.stream().anyMatch(suggestion ->
                suggestion.getUserId().equals(userId) && suggestion.getFriendId().equals(suggestionId)
        );
    }

    public ArrayList<FriendSuggestions> getFriendSuggestions(){
        return friendSuggestions;
    }

    public ArrayList<Friends> getFriends(){
        return friends;
    }

    public ArrayList<FriendReq> getFriendReqs(){
        return friendReqs;
    }

    //return arraylist of friends that is related by a certain user by its ID
    public ArrayList<Friends> getFriendsByUserId(String userId){
        ArrayList<Friends> friendsById=new ArrayList<>();
        for(int i=0;i<allFriends.size();i++){
            if(allFriends.get(i).getUserId().equals(userId)){
                friendsById.add(allFriends.get(i));
            }
        }
        return friendsById;
    }

    public ArrayList<FriendSuggestions> getFriendSuggestionsByUserId(String userId){
        ArrayList<FriendSuggestions> friendSuggestionsById=new ArrayList<>();
        for(int i=0;i<allFriendSuggestions.size();i++){
            if(allFriendSuggestions.get(i).getUserId().equals(userId)){
                friendSuggestionsById.add(allFriendSuggestions.get(i));
            }
        }
        return friendSuggestionsById;
    }

    public ArrayList<FriendReq> getFriendReqsByUserId(String userId){
        ArrayList<FriendReq> friendReqsById=new ArrayList<>();
        for(int i=0;i<allFriendReq.size();i++){
            if(allFriendReq.get(i).getUserId().equals(userId)){
                friendReqsById.add(allFriendReq.get(i));
            }
        }
        return friendReqsById;
    }

    //load All friends
    public void loadFriends() {
        File file = new File("./Friends.json");
        if (file.exists()) {
            try {
                allFriends = objectMapper.readValue(file, new TypeReference<ArrayList<Friends>>() {});
            } catch (IOException e) {
                System.out.println("Error occurred while loading posts.");
                System.out.println(e);
            }
        } else {
            System.out.println("Posts file not found. Initializing an empty list.");
            allFriends = new ArrayList<>();
        }
    }

    public void loadFriendSuggestions() {
        File file = new File("./FriendSuggestions.json");
        if (file.exists()) {
            try {
                allFriendSuggestions = objectMapper.readValue(file, new TypeReference<ArrayList<FriendSuggestions>>() {});
            } catch (IOException e) {
                System.out.println("Error occurred while loading posts.");
                System.out.println(e);
            }
        } else {
            System.out.println("Posts file not found. Initializing an empty list.");
            allFriendSuggestions = new ArrayList<>();
        }
    }

    public void loadFriendReqs() {
        File file = new File("./FriendRequests.json");
        if (file.exists()) {
            try {
                allFriendReq = objectMapper.readValue(file, new TypeReference<ArrayList<FriendReq>>() {});
            } catch (IOException e) {
                System.out.println("Error occurred while loading posts.");
                System.out.println(e);
            }
        } else {
            System.out.println("Posts file not found. Initializing an empty list.");
            allFriendReq = new ArrayList<>();
        }
    }

    //load friends of each user according to their id
    public void loadHisOwnFriends(String userId){
        loadFriends();
        ArrayList<Friends> loadedFriends = getFriendsByUserId(userId);

        if(!loadedFriends.isEmpty()){
            for(int i=0;i<loadedFriends.size();i++){
                friends.add(loadedFriends.get(i));
            }
        }
    }

    public void loadHisOwnFriendSuggestions(String userId){
        loadFriendSuggestions();
        ArrayList<FriendSuggestions> loadedFriendSuggestions = getFriendSuggestionsByUserId(userId);

        if(!loadedFriendSuggestions.isEmpty()){
            for(int i=0;i<loadedFriendSuggestions.size();i++){
                friendSuggestions.add(loadedFriendSuggestions.get(i));
            }
        }
    }

    public void loadHisOwnFriendReq(String userId){
        loadFriendReqs();
        ArrayList<FriendReq> loadedFriendReqs = getFriendReqsByUserId(userId);

        if(!loadedFriendReqs.isEmpty()){
            for(int i=0;i<loadedFriendReqs.size();i++){
                friendReqs.add(loadedFriendReqs.get(i));
            }
        }
    }

    public static ArrayList<Friends> getAllFriends() {
        return allFriends;
    }

    public static ArrayList<FriendReq> getAllFriendReq() {
        return allFriendReq;
    }

    public static ArrayList<FriendSuggestions> getAllFriendSuggestions() {
        return allFriendSuggestions;
    }

}



