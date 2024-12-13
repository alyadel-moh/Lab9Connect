package coding;

import coding.ENUMS.Mapper;
import coding.Observer.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;

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
    private final Map<Notifications_Panel, Window> openWindows = new HashMap<>();


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

            /////////////// REQUEST //////////////////////////////////
            ///// Receive friend request
            case RECEIVE -> {
                customPanel.button1.addActionListener(e -> {
                    openOrFocusWindow(customPanel, () -> new Requests_Management(user)); // Opens Requests_Management and tracks the window
                    removeNotification(customPanel);
                }); // Accept
                customPanel.button2.addActionListener(e -> removeNotification(customPanel)); // Decline
            }

            ////////////////// GROUP //////////////////////////////
            ///// Added to group or change status: no specific action
            case ADDED, CHANGE_STATUS -> {
                customPanel.button1.addActionListener(e -> removeNotification(customPanel)); // Accept
                customPanel.button2.addActionListener(e -> removeNotification(customPanel)); // Decline
            }

            ///////////////// CONTENT /////////////////////////////////////
            //////// Post created by friend
            case POST -> {
                customPanel.button1.addActionListener(e -> {
                    openOrFocusWindow(customPanel, () -> new ViewPost((User) customPanel.getUser())); // Opens ViewPost and tracks the window
                    removeNotification(customPanel);
                }); // Accept
                customPanel.button2.addActionListener(e -> removeNotification(customPanel)); // Decline
            }

            /////// Story posted by friend
            case STORY -> {
                customPanel.button1.addActionListener(e -> {
                    openOrFocusWindow(customPanel, () -> new Storiesview((User) customPanel.getUser(), 0)); // Opens Storiesview and tracks the window
                    removeNotification(customPanel);
                }); // Accept
                customPanel.button2.addActionListener(e -> removeNotification(customPanel)); // Decline
            }

            default -> throw new IllegalStateException("Unexpected value: " + customPanel.getCode());
        }
    }

    /**
     * Ensures only one window per notification is opened, and tracks the windows.
     */
    private void openOrFocusWindow(Notifications_Panel customPanel, Supplier<Window> windowSupplier) {
        if (openWindows.containsKey(customPanel)) {
            // Bring the existing window to the front
            Window window = openWindows.get(customPanel);
            window.toFront();
            window.requestFocus();
        } else {
            // Create and track a new window
            Window window = windowSupplier.get();
            openWindows.put(customPanel, window);

            // Add a listener to remove the window from the map when closed
            window.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    openWindows.remove(customPanel);
                    disposeOtherWindows(); // Clean up other unrelated windows
                }
            });
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
     * Disposes of windows not associated with notifications.
     */
    private void disposeOtherWindows() {
        for (Window window : Window.getWindows()) {
            if (window != this && !(window instanceof Homepage) && !openWindows.containsValue(window)) {
                window.dispose();
            }
        }
    }

    /**
     * Observer method to handle new notifications dynamically.
     */
    @Override
    public void update(Object sender, Enum message) {
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

       // Notifier notifier = user.getNotifier();
        Notifier notifier = group.getNotifier();


        NotificationObserver observer1 = new Content_Observer(user);
        NotificationObserver observer2 = new Request_Observer(user2);
        NotificationObserver observer3 = new Group_Observer(user3);
        NotificationObserver observer4 = new Notifications(user2);

        notifier.addObserver(observer1);
        notifier.addObserver(observer2);
        notifier.addObserver(observer3);
        notifier.addObserver(observer4);

        System.out.println(user2.getContent_observer());


        user.getObserver().setVisible(true);
        user2.getObserver().setVisible(true);
        user3.getObserver().setVisible(true);

        // Simulating new notifications
        notifier.notifyObservers(new User.UserBuilder().setUserName("Jane Smith").build(),STORY, null);
        notifier.notifyObservers(new User.UserBuilder().setUserName("Michael Owens").build(),RECEIVE, null);
        notifier.notifyObservers(new User.UserBuilder().setUserName("Wagdyy Owens").build(),CHANGE_STATUS, null);
        notifier.notifyObservers(new User.UserBuilder().setUserName("Wael Fathy").build(),ADDED, null);
        notifier.notifyObservers(group, CHANGE_STATUS, null);

    }
}
