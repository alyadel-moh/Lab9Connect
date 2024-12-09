package coding;

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
    private ArrayList<Friend> friends;
    private ArrayList<FriendRequest> friendReqs;
    private ArrayList<FriendSuggestions> friendSuggestions;
    private static ArrayList<FriendRequest> allFriendReq= new ArrayList<>();//static to be shared with all instances to save all posts
    private static ArrayList<Friend> allFriends=new ArrayList<Friend>();//will intialize it with empty arraylist once the class is loaded
    private static ArrayList<FriendSuggestions> allFriendSuggestions=new ArrayList<FriendSuggestions>();

//    private String storyPath="./JsonFilesStories/";
//    private String postsPath="./JsonFilesPosts/";

    public FriendHandler() {
        this.objectMapper = new ObjectMapper();
        this.friends = new ArrayList<Friend>();
        this.friendReqs = new ArrayList<>();
        this.friendSuggestions =new ArrayList<>();
        this.objectMapper.registerModule(new JavaTimeModule());//JavaTimeModule helps to write the localTime to file
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //loadFriends();
        //loadFriendSuggestions();
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
            Friends newFriend2 = new Friends(friendId, userId);
            friends.add(newFriend);
            friends.add(newFriend2);
            allFriends.add(newFriend);
            allFriends.add(newFriend2);
            saveFriends();
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
                request.getSender().getUserId().equals(userId) && request.getReceiver().getUserId().equals(friendId)
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

    public ArrayList<Friend> getFriends(){
        return friends;
    }

    public ArrayList<FriendRequest> getFriendReqs(){
        return friendReqs;
    }

    //return arraylist of friends that is related by a certain user by its ID
    public ArrayList<Friend> getFriendsByUserId(String userId){
        ArrayList<Friend> friendsById=new ArrayList<>();
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

    public ArrayList<FriendRequest> getFriendReqsByUserId(String userId){
        ArrayList<FriendRequest> friendReqsById=new ArrayList<>();
        for(int i=0;i<allFriendReq.size();i++){
            if(allFriendReq.get(i).getSender().getUserId().equals(userId)){
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
                allFriends = objectMapper.readValue(file, new TypeReference<ArrayList<Friend>>() {});
            } catch (IOException e) {
                System.out.println("Error occurred while loading Friendsssss.");
                System.out.println(e);
            }
        } else {
            System.out.println("Friends file not found. Initializing an empty list.");
            allFriends = new ArrayList<Friend>();
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
            System.out.println("Suggestion file not found. Initializing an empty list.");
            allFriendSuggestions = new ArrayList<>();
        }
    }

    public void loadFriendReqs() {
        File file = new File("./FriendRequests.json");
        if (file.exists()) {
            try {
                allFriendReq = objectMapper.readValue(file, new TypeReference<ArrayList<FriendRequest>>() {});
                // Use an iterator to safely remove elements
                allFriendReq.removeIf(request -> !"Pending".equalsIgnoreCase(request.getState()));
            } catch (IOException e) {
                System.out.println("Error occurred while loading requests.");
                e.printStackTrace(); // Improved logging
            }
        } else {
            System.out.println("Request file not found. Initializing an empty list.");
            allFriendReq = new ArrayList<>();
        }
    }


    //load friends of each user according to their id
    public void loadHisOwnFriends(String userId){
        loadFriends();
        ArrayList<Friend> loadedFriends = getFriendsByUserId(userId);

        if(!loadedFriends.isEmpty()){
            for(int i=0;i<loadedFriends.size();i++){
                friends.add(loadedFriends.get(i));
            }
        }
        System.out.println("own friends "+loadedFriends.size());
        System.out.println("all friends "+allFriends.size());
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
        ArrayList<FriendRequest> loadedFriendReqs = getFriendReqsByUserId(userId);
        if(!loadedFriendReqs.isEmpty()){
            for(int i=0;i<loadedFriendReqs.size();i++){
                friendReqs.add(loadedFriendReqs.get(i));
            }
        }
    }

    public Friend getFriendByUserAndFriendId(User user,User friend){
        for(int i=0;i<allFriends.size();i++){
            if(allFriends.get(i).getUserId().equals(user.getUserId())&&allFriends.get(i).getFriendId().equals(friend.getUserId())){
                return allFriends.get(i);
            }
        }
        return null;
    }

    //Delete Friend
    public void deleteFriend(User user,User friend){
        System.out.println(allFriends.size() + " friends size");
        Friend friend1=getFriendByUserAndFriendId(user,friend);
        Friend friend2=getFriendByUserAndFriendId(friend,user);
        // Remove both directions of the friendship
        if(friend1!=null){
            allFriends.remove(friend1);
            friends.remove(friend1);
        }
        if(friend2!=null){
            allFriends.remove(friend2);
            friends.remove(friend2);
        }
        System.out.println(allFriends.size() + " after friends size");
        saveFriends();
        }


    public static ArrayList<Friend> getAllFriends() {
        return allFriends;
    }

    public static ArrayList<FriendRequest> getAllFriendReq() {
        return allFriendReq;
    }

    public static ArrayList<FriendSuggestions> getAllFriendSuggestions() {
        return allFriendSuggestions;
    }

}



