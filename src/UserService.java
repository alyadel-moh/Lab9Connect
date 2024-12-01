import javax.swing.*;
import java.util.Date;
import java.util.UUID;

public class UserService {
    private Database database;

    public UserService(Database database) {
        this.database = database;
    }

    public Database getDatabase() {
        return database;
    }

    public void signUp(String email, String userName, String password, Date dateOfBirth){
        if(!Validator.isValidEmail(email)){
            JOptionPane.showMessageDialog(null, "Invalid email address");
            return;
        }
        if(!Validator.fieldsProvided(email, userName, password)){
            JOptionPane.showMessageDialog(null, "All fields are required");
        }
        String hashedPass = Validator.hashPassword(password);
        String userId = UUID.randomUUID().toString();
        User newUser = new User(userId, hashedPass, userName, email, dateOfBirth, "online");
        database.addUser(newUser);
        JOptionPane.showMessageDialog(null, "User has been added");
    }
    public User login(String email, String password){
        String hashedPass = Validator.hashPassword(password);
        for (User user : database.getUsers()) {
            if(user.getEmail().equals(email) && user.getPassword().equals(hashedPass)){
                user.setStatus("online");
                JOptionPane.showMessageDialog(null, "User has been logged in");
                return user;
            }
        }
        return null;
    }
    public void logout(){
        for(User user : database.getUsers()){
            if(user.getStatus().equals("online")){
                user.setStatus("offline");
                JOptionPane.showMessageDialog(null, "User has been logged off");
                return;
            }
        }
    }
}