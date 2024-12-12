package coding;

import coding.ENUMS.Mapper;
import coding.Observer.Content_Observer;
import coding.Observer.Notifier;
import coding.Observer.NotificationObserver;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static coding.ENUMS.NOTIFICATIONS.REQUEST.RECEIVE;
import static coding.ENUMS.NOTIFICATIONS.REQUEST.SEND;
import static coding.ENUMS.NOTIFICATIONS.CONTENT.POST;
import static coding.ENUMS.NOTIFICATIONS.CONTENT.STORY;
import static coding.ENUMS.NOTIFICATIONS.GROUP.CHANGE_STATUS;
import static coding.ENUMS.NOTIFICATIONS.GROUP.ADDED;



public class Notifications extends JFrame implements NotificationObserver {
    private final User user;
    private UserService service;
    private final JPanel panel1;

    public ArrayList<Notifications_Panel> getNotifications() {
        return notifications;
    }

    private final ArrayList<Notifications_Panel> notifications;

    public Notifications(User user) {
        this.user = user;
         notifications = new ArrayList<>();

        // Initialize the main panel
        panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS)); // Vertical layout for notifications

        // Populate the panel with existing notifications
        populateNotifications(notifications);

        // Set up the JFrame
        setContentPane(new JScrollPane(panel1)); // Add scroll functionality for long lists
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Notifications");
        setResizable(false);
        setBounds(100, 100, 800, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Populate the notification panel with a list of CustomPanels.
     */
    private void populateNotifications(List<Notifications_Panel> notifications) {
        panel1.removeAll(); // Clear existing notifications
        if (notifications.isEmpty()) {
            panel1.add(new JLabel("No New Notificatons!"));
            return;
        }

        for (Notifications_Panel customPanel : notifications) {
            setupCustomPanelActions(customPanel);
            panel1.add(customPanel);
        }
        refreshUI();
    }

    /**
     * Set up actions for CustomPanel buttons.
     */
    private void setupCustomPanelActions(Notifications_Panel customPanel) {
        // Accept Request
        customPanel.button1.addActionListener(e -> {
            notifications.remove(customPanel);
            panel1.remove(customPanel);
            refreshUI();
        });

        // Decline Request
        customPanel.button2.addActionListener(e -> {
            notifications.remove(customPanel);
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
    public void update(User sender, Enum message) {
        SwingUtilities.invokeLater(() -> {
            Notifications_Panel newNotification = new Notifications_Panel(sender, message, "View", "Ignore");

            newNotification.add(Mapper.getMessage(message));
            setupCustomPanelActions(newNotification);
            notifications.add(newNotification);
            panel1.add(newNotification);
            refreshUI();
        });
    }

     public static void main(String[] args) {
         User user = new User.UserBuilder().setUserName("John Doe").build();
         User user2 = new User.UserBuilder().setUserName("Mark stan").build();
         User user3 = new User.UserBuilder().setUserName("Sheshtawy").build();
         Group group = new Group(user3);
         group.setName("3ala allah");

         Notifier notifier = new Notifier();

         Content_Observer observer1 = new Content_Observer(user);
         Content_Observer observer2 = new Content_Observer(user2);
         Content_Observer observer3 = new Content_Observer(user3);

         //Notifications notificationWindow = new Notifications(user);
         notifier.addObserver(observer1);
         notifier.addObserver(observer2);
         notifier.addObserver(observer3);


         System.out.println(user2.getContent_observer());

         // Simulating a new notification
         notifier.notifyObservers(new User.UserBuilder().setUserName("Jane Smith").build(), STORY , null);
         System.out.println();
         notifier.notifyObservers(new User.UserBuilder().setUserName("Michael Owens").build(), RECEIVE , user2.getObserver());
         System.out.println();
         notifier.notifyObservers(new User.UserBuilder().setUserName("Wagdyy Owens").build(), CHANGE_STATUS , null);
         System.out.println();
         notifier.notifyObservers(new User.UserBuilder().setUserName("wael fathy").build(), ADDED , null);



     }
}
