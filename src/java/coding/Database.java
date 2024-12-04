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
    }

    public void addUser(User user) {
        users.add(user);
        saveUsers();
    }

    public ArrayList<User> getUsers() {
        return this.users;
    }

    public void saveUsers() {
        ObjectMapper mapper = new ObjectMapper();mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
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
        ObjectMapper mapper = new ObjectMapper();
        try{
            File file = new File("Users.json");
            if (file.exists()){
                users = mapper.readValue(file,mapper.getTypeFactory().constructCollectionType(ArrayList.class, User.class));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}