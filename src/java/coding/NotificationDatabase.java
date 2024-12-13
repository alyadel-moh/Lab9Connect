package coding;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class NotificationDatabase {

    private static final String FILE_PATH = "NotificationsForContents.json";

    // Save notifications to a JSON file with custom structure
    public static void saveNotificationsToFile(List<Notification> notifications) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

            // Write the notifications list to file in the desired structure
            objectMapper.writeValue(new File(FILE_PATH), notifications);
            System.out.println("Notifications saved successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load notifications from a JSON file and return as Notification objects
    public static List<Notification> loadNotificationsFromFile() {
        try {
            File file = new File(FILE_PATH);

            // Create file if it doesn't exist
            if (!file.exists()) {
                file.createNewFile();  // Create the file if it doesn't exist
                System.out.println("Notifications file created: " + file.getAbsolutePath());
                return List.of();  // Return an empty list if the file does not exist yet
            }

            // If the file is empty, return an empty list
            if (file.length() == 0) {
                return List.of();  // Return an empty list if the file has no content
            }

            ObjectMapper objectMapper = new ObjectMapper();

            // Read the notifications from the file and return the list
            List<Notification> notifications = objectMapper.readValue(file,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Notification.class));
            return notifications;

        } catch (IOException e) {
            e.printStackTrace();
            return List.of();// Return empty list in case of error
        }
    }
}
