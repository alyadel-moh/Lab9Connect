package coding;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Database {

    // Singleton instance
    private static Database instance;

    // User data
    private static ArrayList<User> users;

    // Private constructor to prevent instantiation
    private Database() {
        users = new ArrayList<>();
        loadUsers();
    }


    // Public static method to provide global access to the instance
    public static Database getInstance() {
        if (instance == null) { // First check
            synchronized (Database.class) {
                if (instance == null) { // Second check
                    instance = new Database();
                }
            }
        }
        return instance;
    }

    public void addUser(User user) {
        users.add(user);
        saveUsers();
    }

    public static User findUserById(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
    return null;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public void saveUsers() {
        ObjectMapper mapper = createObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            mapper.writeValue(new File("Users.json"), users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadUsers() {
        ObjectMapper mapper = createObjectMapper();
        File file = new File("Users.json");

        if (file.exists() && file.length() > 0) {
            try {
                users = mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(ArrayList.class, User.class));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e);
            }


            for (User user : users) {
                user.getHandler().loadHisOwnPosts(user.getUserId());
                user.getHandler().loadHisOwnStories(user.getUserId());
                user.getManager().setSuggestions(users);
                user.getFriendHandler().loadHisOwnFriends(user.getUserId());
                user.getManager().setFriends(user.getUserId());
                user.getFriendHandler().loadHisOwnFriendSuggestions(user.getUserId());
                user.getManager().loadHisOwnRequests(user.getUserId());
                user.getGroupManager().loadGroups();
                user.getGroupManager().loadSuggestionGroups();

                if(user.getProfilePath() == null){
                    user.setProfilepath();
                }
            }
        } else {
            users.clear();
        }
    }

    private ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }
}
