package coding;

import coding.Observer.ContentNotifier;
import coding.Observer.ContentObserver;

import javax.swing.*;
import java.util.List;

public class Notifications extends JFrame implements ContentObserver {
    private User user;
    private UserService service;
    private JPanel panel1;

    public Notifications(User user) {
        this.user = user;

        // Initialize the main panel
        panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS)); // Vertical layout for notifications

        // Populate the panel with existing notifications
        populateNotifications(user.getHandler().getNotifications());

        // Set up the JFrame
        setContentPane(new JScrollPane(panel1)); // Add scroll functionality for long lists
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Notifications");
        setResizable(false);
        setBounds(100, 100, 300, 400);
        setLocationRelativeTo(null);
        //setVisible(true);
    }

    /**
     * Populate the notification panel with a list of CustomPanels.
     */
    private void populateNotifications(List<CustomPanel> notifications) {
        panel1.removeAll(); // Clear existing notifications
        if (notifications.isEmpty()) {
            panel1.add(new JLabel("No New Notificatons!"));
            return;
        }

        for (CustomPanel customPanel : notifications) {
            setupCustomPanelActions(customPanel);
            panel1.add(customPanel);
        }
        refreshUI();
    }

    /**
     * Set up actions for CustomPanel buttons.
     */
    private void setupCustomPanelActions(CustomPanel customPanel) {
        // Accept Request
        customPanel.button1.addActionListener(e -> {
            user.getHandler().getNotifications().remove(customPanel);
            panel1.remove(customPanel);
            refreshUI();
        });

        // Decline Request
        customPanel.button2.addActionListener(e -> {
            user.getHandler().getNotifications().remove(customPanel);
            panel1.remove(customPanel);
            refreshUI();
        });
    }

    /**
     * Refresh the UI after updates.
     */
    private void refreshUI() {
        panel1.revalidate(); // Recalculate layout
        panel1.repaint();    // Redraw components
    }

    /**
     * Observer method to handle new notifications dynamically.
     */
    @Override
    public void update(User sender, String message) {
        SwingUtilities.invokeLater(() -> {
            CustomPanel newNotification = new CustomPanel(sender, "View", "Ignore");
            newNotification.add(message);
            setupCustomPanelActions(newNotification);
            panel1.add(newNotification);
            refreshUI();
        });
    }

     public static void main(String[] args) {
         ContentNotifier notifier = new ContentNotifier();
         User user = new User.UserBuilder()
                 .setUserName("John Doe")
                 .build();

         Notifications notificationWindow = new Notifications(user);
         notifier.addObserver(notificationWindow);

        // Simulating a new notification
         notifier.notifyObservers(new User.UserBuilder().setUserName("Jane Smith").build(), "You have a new friend request!", null);

     }
}
