package coding;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Test {
    public static void main(String[] args) {
        // Step 1: Create a user
        LocalDate dateOfBirth = LocalDate.of(1995, 12, 15);

       User user = new User.UserBuilder()
                .setUserId("user1")
                .setPassword("password123")
                .setUserName("Menna")
                .setEmail("menna@gmail.com")
                .setDateOfBirth(dateOfBirth)
                .setStatus("online")
                .build();


        // Step 2: Add content (posts and stories) for the user
        LocalDateTime currentTime = LocalDateTime.now();
        Posts post = (Posts) ContentFactory.createContent("post","post1", "user1", "Hello, this is my first post!", currentTime);
        Stories story = (Stories) ContentFactory.createContent("story","story1", "user1", "This is my first story!", currentTime.minusHours(23));

        // Step 3: add content
//        user.getHandler().addContent(post); // Add post to user
//        user.getHandler().addContent(story); // Add story to user
//
//        // Step 4: Save the user's content to JSON
//        user.getHandler().saveContent(user);
        System.out.println("User{" + user.getUserName()+  "} content saved successfully!");

    }
}
