package coding;

import coding.ENUMS.Mapper;
import coding.Observer.Content_Observer;
import coding.Observer.Notifier;
import coding.Observer.NotificationObserver;

import javax.swing.*;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static coding.ENUMS.NOTIFICATIONS.REQUEST.RECEIVE;
import static coding.ENUMS.NOTIFICATIONS.REQUEST.SEND;
import static coding.ENUMS.NOTIFICATIONS.CONTENT.POST;
import static coding.ENUMS.NOTIFICATIONS.CONTENT.STORY;
import static coding.ENUMS.NOTIFICATIONS.GROUP.CHANGE_STATUS;
import static coding.ENUMS.NOTIFICATIONS.GROUP.ADDED;

public class Notifications extends JFrame implements NotificationObserver {
    private final User user;
    private final DefaultListModel<Notifications_Panel> model;
    private final JList<Notifications_Panel> notificationList;
    private final List<Notifications_Panel> notifications;

    public List<Notifications_Panel> getNotifications() {
        return notifications;
    }


    public Notifications(User user) {
        this.user = user;
        this.notifications = new CopyOnWriteArrayList<>(); // Thread-safe notifications list
        this.model = new DefaultListModel<>();
        this.notificationList = new JList<>(model);

        // Customize the JList
        notificationList.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> value);

        // Set up the JScrollPane and add to JFrame
        JScrollPane scrollPane = new JScrollPane(notificationList);
        setContentPane(scrollPane);

        // Set up JFrame
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Notifications { " + user.getUserName() + " }");
        setResizable(false);
        setBounds(100, 100, 800, 400);
        setLocationRelativeTo(null);
        setVisible(true);

        // Populate with initial notifications
        populateNotifications(notifications);
    }

    /**
     * Populate the notification list dynamically.
     */
    private void populateNotifications(List<Notifications_Panel> newNotifications) {
        model.clear(); // Clear existing notifications
        if (newNotifications.isEmpty()) {
            Notifications_Panel emptyPanel = new Notifications_Panel(user, null, "", ""); // Create an empty panel
            emptyPanel.add(new JLabel("No New Notifications!")); // Add the message to the panel
            model.addElement(emptyPanel); // Add the panel to the model
        } else {
            for (Notifications_Panel panel : newNotifications) {
                setupCustomPanelActions(panel);
                model.addElement(panel); // Add notification panels to the model
            }
        }
    }


    /**
     * Set up actions for buttons in each notification panel.
     */
    private void setupCustomPanelActions(Notifications_Panel customPanel) {
        customPanel.button1.addActionListener(e -> removeNotification(customPanel)); // Accept
        customPanel.button2.addActionListener(e -> removeNotification(customPanel)); // Decline
    }

    /**
     * Remove a notification from the list and update the UI.
     */
    private void removeNotification(Notifications_Panel customPanel) {
        notifications.remove(customPanel);
        model.removeElement(customPanel);
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
            model.addElement(newNotification);
        });
    }

    public static void main(String[] args) {
        User user = new User.UserBuilder().setUserName("John Doe").build();
        User user2 = new User.UserBuilder().setUserName("Mark Stan").build();
        User user3 = new User.UserBuilder().setUserName("Sheshtawy").build();
        Group group = new Group(user3);
        group.setName("3ala Allah");

        Notifier notifier = new Notifier();

        Content_Observer observer1 = new Content_Observer(user);
        Content_Observer observer2 = new Content_Observer(user2);
        Content_Observer observer3 = new Content_Observer(user3);

        notifier.addObserver(observer1);
        notifier.addObserver(observer2);
        notifier.addObserver(observer3);

        System.out.println(user2.getContent_observer());

        // Simulating new notifications
        notifier.notifyObservers(new User.UserBuilder().setUserName("Jane Smith").build(), STORY, null);
        System.out.println();
        notifier.notifyObservers(new User.UserBuilder().setUserName("Michael Owens").build(), RECEIVE, user2.getObserver());
        System.out.println();
        notifier.notifyObservers(new User.UserBuilder().setUserName("Wagdyy Owens").build(), CHANGE_STATUS, null);
        System.out.println();
        notifier.notifyObservers(new User.UserBuilder().setUserName("Wael Fathy").build(), ADDED, null);
    }
}
