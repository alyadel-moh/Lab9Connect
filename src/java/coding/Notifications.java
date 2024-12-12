package coding;

import coding.ENUMS.Mapper;
import coding.Observer.*;

import javax.swing.*;
import java.awt.*;
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
    private final JPanel notificationPanel;
    private final List<Notifications_Panel> notifications;

    public List<Notifications_Panel> getNotifications() {
        return notifications;
    }

    public void refreshUI() {
        this.repaint();
        this.revalidate();
    }

    public Notifications(User user) {
        this.user = user;
        this.notifications = new CopyOnWriteArrayList<>(); // Thread-safe notifications list
        this.notificationPanel = new JPanel();
        notificationPanel.setLayout(new BoxLayout(notificationPanel, BoxLayout.Y_AXIS));

        // Set up the JScrollPane and add to JFrame
        JScrollPane scrollPane = new JScrollPane(notificationPanel);
        setContentPane(scrollPane);

        // Set up JFrame
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Notifications { " + user.getUserName() + " }");
        setResizable(false);
        setBounds(100, 100, 800, 400);
        setLocationRelativeTo(null);
        //setVisible(true);

        // Populate with initial notifications
        populateNotifications(notifications);
    }

    /**
     * Populate the notification panel dynamically.
     */
    private void populateNotifications(List<Notifications_Panel> newNotifications) {
        notificationPanel.removeAll(); // Clear existing notifications
        if (newNotifications.isEmpty()) {
            JLabel emptyLabel = new JLabel("No New Notifications!");
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            emptyLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            notificationPanel.add(emptyLabel);
        } else {
            for (Notifications_Panel panel : newNotifications) {
                setupCustomPanelActions(panel);
                notificationPanel.add(panel); // Add notification panels to the panel
            }
        }
        refreshUI();
    }

    /**
     * Set up actions for buttons in each notification panel.
     */
    private void setupCustomPanelActions(Notifications_Panel customPanel) {
        switch (customPanel.getCode()) {
            case RECEIVE -> {
                customPanel.button1.addActionListener(e -> {
                    new Requests_Management(user);
                    removeNotification(customPanel);
                }); // Accept
                customPanel.button2.addActionListener(e -> removeNotification(customPanel)); // Decline
            }
            case ADDED, CHANGE_STATUS -> {
                customPanel.button1.addActionListener(e -> removeNotification(customPanel)); // Accept
                customPanel.button2.addActionListener(e -> removeNotification(customPanel)); // Decline
            }
            case POST -> {
                customPanel.button1.addActionListener(e -> {
                    new ViewPost(user);
                    removeNotification(customPanel);
                }); // Accept
                customPanel.button2.addActionListener(e -> removeNotification(customPanel)); // Decline
            }
            case STORY -> {
                customPanel.button1.addActionListener(e -> {
                    new Storiesview((User) customPanel.getUser(), 0);
                    removeNotification(customPanel);
                }); // Accept
                customPanel.button2.addActionListener(e -> removeNotification(customPanel)); // Decline
            }

            default -> throw new IllegalStateException("Unexpected value: " + customPanel.getCode());
        }
    }

    /**
     * Remove a notification from the list and update the UI.
     */
    private void removeNotification(Notifications_Panel customPanel) {
        notifications.remove(customPanel);
        populateNotifications(notifications);
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
            populateNotifications(notifications);
            System.out.println(user.getUserName() + ", " + notifications.size() + " notifications!");
        });
    }

    public static void main(String[] args) {
        User user = new User.UserBuilder().setUserName("John Doe").build();
        User user2 = new User.UserBuilder().setUserName("Mark Stan").build();
        User user3 = new User.UserBuilder().setUserName("Sheshtawy").build();
        Group group = new Group(user3);
        group.setName("3ala Allah");

        Notifier notifier = user.getNotifier();

        NotificationObserver observer1 = new Content_Observer(user);
        NotificationObserver observer2 = new Request_Observer(user2);
        NotificationObserver observer3 = new Group_Observer(user3);

        notifier.addObserver(observer1);
        notifier.addObserver(observer2);
        notifier.addObserver(observer3);

        System.out.println(user2.getContent_observer());


        user.getObserver().setVisible(true);
        // Simulating new notifications
        notifier.notifyObservers(new User.UserBuilder().setUserName("Jane Smith").build(),STORY, null);
        notifier.notifyObservers(new User.UserBuilder().setUserName("Michael Owens").build(),RECEIVE, null);
        notifier.notifyObservers(new User.UserBuilder().setUserName("Wagdyy Owens").build(),CHANGE_STATUS, null);
        notifier.notifyObservers(new User.UserBuilder().setUserName("Wael Fathy").build(),ADDED, null);
    }
}
