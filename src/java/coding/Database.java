package coding;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Database {
    private ArrayList<User> users;

    public Database() {
        users = new ArrayList<>();
        loadUsers();
    }

    public void addUser(User user) {
        users.add(user);
        saveUsers();
    }

    public ArrayList<User> getUsers() {
        return this.users;
    }

    public void saveUsers() {
        ObjectMapper mapper = createObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        // Register the JavaTimeModule to handle LocalDateTime serialization
        mapper.registerModule(new JavaTimeModule());
        //to show them as timeStamps
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        try{
            mapper.writeValue(new File("Users.json"), users);
        }catch (IOException e){
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
            for(User user : users){
                user.getHandler().loadHisOwnPosts(user.getUserId());
                user.getHandler().loadHisOwnStories(user.getUserId());
                user.getManager().setSuggestions(users);
            }

        }else users.clear();
    }
    private ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

}
