package coding;

import coding.ENUMS.STATE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

public class Homepage extends JFrame {
    private static Homepage instance;
    private final JPanel mainPanel;
    private final JPanel centralPanel;
    private final JPanel centerPanel;
    private final JPanel SocialArea;

    private JPanel postsPanel;
    private JPanel storiesPanel;
    private JPanel friendsPanel;
    private JPanel friendSuggestionsPanel;
    private JPanel subSuggestionPanel;
    private JPanel activePanel;
    private JPanel GroupSuggestionPanel;
    private JPanel subGroupsPanel;


    private JTextArea contentCreationArea;
    private JButton postButton,refreshButton;
    private final User user;
    private final UserService userService;


    public Homepage(UserService userService, User user) {
        this.user = user;
        this.userService = userService;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Homepage");
        setBounds(100,100,1400,900);
        setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1,2));

        centralPanel = new JPanel();
        centralPanel.setLayout(new BorderLayout());

        SocialArea = new JPanel();
        SocialArea.setLayout(new BoxLayout(SocialArea, BoxLayout.Y_AXIS));
        // friendsArea.setLayout(new GridLayout(2,1));

        createHeader();
        createContentArea();
        createSocialList();

        centerPanel.add(centralPanel);
        centerPanel.add(SocialArea);

        viewPosts();
        viewStory();
        displayStatus();
        displayFriendSuggestions();
        displayGroupSuggestion();

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        user.getHandler().deleteExpiredStories();
        user.getHandler().saveStories();
        user.populateObservers();
        Database.getInstance().populateObservers();
        checkObservers();

        add(mainPanel);
        setVisible(true);
    }


    private void viewStory() {
        storiesPanel.removeAll();
        ArrayList<User> users = Database.getUsers();

        if (users != null && !users.isEmpty()) {
            for (User user : users) {
                JButton button = createbutton("",storiesPanel);
                button.setBackground(Color.white);
                button.setBorder(BorderFactory.createLineBorder(Color.black));
                button.setFocusPainted(false);
                Image image = new ImageIcon(user.getProfilePath()).getImage();
                Image scaled = image.getScaledInstance(70,70,Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(scaled));
                storiesPanel.add(button);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Storiesview storiesview = new Storiesview(user, 0);
                    }
                });
            }
        } else {
            JOptionPane.showMessageDialog(null, "no Stories to display !");
        }

        storiesPanel.revalidate();
        storiesPanel.repaint();
        refreshUI();
    }



    private void viewPosts() {
        postsPanel.removeAll(); // Clear previous posts
        ArrayList<User> users = Database.getUsers();

        if (users != null) {
            users.forEach(user -> {
                ArrayList<Posts> posts = user.getHandler().getPosts();
                if (posts != null && !posts.isEmpty()) {
                    posts.forEach(this::displayPost);
                }
            });
        } else {
            JOptionPane.showMessageDialog(this, "No users found.");
        }

        // Refresh UI
        postsPanel.revalidate();
        postsPanel.repaint();
        refreshUI();
    }

    private void displayPost(Posts post) {
        String[] contentDelim = post.getContent().split("@");
        JLabel textLabel = new JLabel(contentDelim[0]);
        textLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        postsPanel.add(textLabel);

        if (contentDelim.length > 1 && !contentDelim[1].isEmpty()) {
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(contentDelim[1]).getImage()
                    .getScaledInstance(300, 300, Image.SCALE_SMOOTH));
            JLabel imageLabel = new JLabel(imageIcon);
            imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            postsPanel.add(imageLabel);
        }
    }



    private void displayStatus(){
        // clear panel
        friendsPanel.removeAll();

        // check if someone is active
        boolean found = false;

        // loop through each friend to check if active
        for (User friend : user.getManager().getFriends()){
            // if ("online".equalsIgnoreCase(friend.getStatus())){
            CustomPanel custom = new CustomPanel(friend, "Active");
            found = true;
            friendsPanel.add(custom);
            // }
        }

        if (!found){
            friendsPanel.add(new JLabel("No Active Friends!"));
            refreshUI();

        }
    }

    private void displayGroupSuggestion(){
        // Clear the panel
        GroupSuggestionPanel.removeAll();

        //// load group suggestions for user
        user.getGroupManager().viewSuggestions(user);

        //// remove group from suggestions if primary,admin or normal
        user.getGroupManager().getSuggestions().removeIf(suggested ->
                suggested.getMembers().contains(user) ||
                        user.equals(suggested.getPrimaryAdmin()) ||
                        suggested.getOtherAdmins().contains(user)
                    );

        if(user.getGroupManager().getSuggestions() == null || user.getGroupManager().getSuggestions().isEmpty()){
            GroupSuggestionPanel.add(new JLabel("No Suggestions to View!"));
            refreshUI();
            return;
        }

        for (Group suggested : user.getGroupManager().getSuggestions()){
            STATE state = null;

            if (user.getGroupManager().getRequest(suggested) != null){
                state = user.getGroupManager().getRequest(suggested).getState();
            }

            if (state == STATE.PENDING){
                // Create a panel for the "Pending" state
                CustomPanel pendingPanel = new CustomPanel(suggested, "Pending");
                pendingPanel.button1.addActionListener(e -> {
                    user.getGroupManager().cancelRequest(suggested);
                    GroupSuggestionPanel.remove(pendingPanel);

                    CustomPanel sendRequestPanel = createSendRequestPanel(suggested, GroupSuggestionPanel, "Join Group");
                    GroupSuggestionPanel.add(sendRequestPanel);
                    refreshUI();
                });

                GroupSuggestionPanel.add(pendingPanel);
            } else {
                // Create a panel for the "Send Request" and "Ignore" actions
                CustomPanel sendRequestPanel = createSendRequestPanel(suggested, GroupSuggestionPanel, "Join Group");
                GroupSuggestionPanel.add(sendRequestPanel);
            }
        }

        // Refresh the UI after adding all panels
        refreshUI();
    }


    private void displayFriendSuggestions() {
        // Clear the panel
        friendSuggestionsPanel.removeAll();
        if(user.getSuggestions().isEmpty()){
            friendSuggestionsPanel.add(new JLabel("No Suggestions to View!"));
            refreshUI();
            return;
        }

        user.getSuggestions().removeIf(suggested -> user.getManager().getFriends().contains(suggested));

        for (User suggested : user.getSuggestions()) {
            if(suggested instanceof User)
            {
            if (user.getManager().getFriends().contains(suggested)){
                //user.getSuggestions().remove(suggested);
                continue;
            }

            STATE state = null;

            if (user.getManager().getRequest(suggested) != null) {
                state = user.getManager().getRequest(suggested).getState();
            }

            if (state == STATE.PENDING) {
                // Create a panel for the "Pending" state
                CustomPanel pendingPanel = new CustomPanel(suggested, "Pending");
                pendingPanel.button1.addActionListener(e -> {
                    user.getManager().cancelRequest(suggested);
                    friendSuggestionsPanel.remove(pendingPanel);

                    CustomPanel sendRequestPanel = createSendRequestPanel(suggested, friendSuggestionsPanel, "Send Request");
                    friendSuggestionsPanel.add(sendRequestPanel);
                    refreshUI();
                });

                friendSuggestionsPanel.add(pendingPanel);
            } else {
                // Create a panel for the "Send Request" and "Ignore" actions
                CustomPanel sendRequestPanel = createSendRequestPanel(suggested, friendSuggestionsPanel, "Send Request");
                friendSuggestionsPanel.add(sendRequestPanel);
            }
        }


}

        // Refresh the UI after adding all panels
        refreshUI();
    }

    private CustomPanel createSendRequestPanel(Object suggested, JPanel panel, String text) {
        CustomPanel customPanel = new CustomPanel(suggested, text, "Ignore");

        // Send Request Action
        customPanel.button1.addActionListener(e -> {
                    //try {
            if (suggested instanceof User) {
                user.getManager().sendRequest(suggested);
                panel.remove(customPanel);

                CustomPanel pendingPanel = new CustomPanel(suggested, "Pending");
                pendingPanel.button1.addActionListener(_ -> {
                    user.getManager().cancelRequest(suggested);
                    panel.remove(pendingPanel);
                    panel.add(customPanel);
                    refreshUI();
                });

                panel.add(pendingPanel);
                refreshUI();

            } else if (suggested instanceof Group) {
                user.getGroupManager().sendRequest(suggested);
                panel.remove(customPanel);

                // Create a new panel indicating the request is pending
                CustomPanel pendingPanel = new CustomPanel(suggested, "Pending");
                pendingPanel.button1.addActionListener(_ -> {
                    try {
                        // Attempt to cancel the group request
                        user.getGroupManager().cancelRequest(suggested);

                        // If successful, update the UI to show the original panel
                        panel.remove(pendingPanel);
                        panel.add(customPanel);

                    } catch (IllegalArgumentException ex) {
                        // Log or display error to the user without crashing the app

                        ((Group) suggested).getRequests().removeIf(groupRequest -> suggested.equals(groupRequest.getReceiver()));
                        panel.remove(pendingPanel);
                        panel.add(customPanel);
                        System.out.println("Error canceling request: " + ex.getMessage());
                    } finally {
                        // Ensure the UI is refreshed regardless of the outcome
                        refreshUI();
                    }
                });

                // Add the pending panel to the UI and refresh
                panel.add(pendingPanel);
                refreshUI();
            }
        });



                // Ignore Action
        customPanel.button2.addActionListener(e -> {
            if (suggested instanceof User) {
                user.getSuggestions().remove(suggested);
                System.out.println("User removed");
            } else if (suggested instanceof Group) {
                user.getGroupManager().getSuggestions().remove(suggested);
                System.out.println("Group removed");
            }

            panel.remove(customPanel);
            refreshUI();
        });

        return customPanel;
    }

    private void refreshUI() {
        SocialArea.revalidate(); // Recalculate layout
        SocialArea.repaint();   // Redraw components

        centralPanel.revalidate();
        centralPanel.repaint();

        centerPanel.revalidate();
        centerPanel.repaint();
    }


    public void setActivePanel(JPanel newPanel) {
        activePanel = friendsPanel;
        remove(activePanel);
        activePanel = newPanel;
        add(new JScrollPane(activePanel), BorderLayout.NORTH);
        revalidate();
        repaint();
    }

    private void createHeader() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel Title = new JLabel("Connect Hub");
        Title.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(Title);


        //Search Field
        JTextField searchField = new JTextField(20);
        searchField.setText("Search");
        headerPanel.add(searchField);
        // Add a button with a PNG image
        JButton searchButton = new JButton();
        searchButton.setPreferredSize(new Dimension(25, 25));
        try {
            // Load the image from a PNG file
            ImageIcon searchIcon = new ImageIcon("./download.jpg");
            Image scaledImage = searchIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH); // Scale image
            searchButton.setIcon(new ImageIcon(scaledImage)); // Set the scaled image as icon
            // Remove button borders and background to make it look like just an image
            searchButton.setFocusPainted(false);
            searchButton.setContentAreaFilled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        headerPanel.add(searchButton);

        // Add ActionListener to the search button
        searchButton.addActionListener(e -> {
            String searchedText = searchField.getText();
            if(searchedText.isEmpty()){
                JOptionPane.showMessageDialog(null,"Enter something to search for ", "Error",JOptionPane.ERROR_MESSAGE);
            }
            new SearchManagement(user,userService,null,searchedText);
            System.out.println("pressed search String is : "+searchedText );
        });

        JButton friendButton = createbutton("Manage Friends",headerPanel);
        JButton notificationButton = createbutton("Notifications",headerPanel);
        JButton profileButton =createbutton("Profile Management",headerPanel);
        JButton addPostButton =createbutton("Create Content",headerPanel);
        JButton logoutButton = createbutton("Logout",headerPanel);
        refreshButton =  createbutton("Refresh",headerPanel);
        JButton groupManagement = createbutton("Group Management",headerPanel);

        if (!user.getObserver().getNotifications().isEmpty()){
            notificationButton.setBackground(Color.CYAN);
        }else
            notificationButton.setBackground(Color.BLACK);

        headerPanel.setBackground(Color.LIGHT_GRAY);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Event Listeners
        profileButton.addActionListener(e -> new ProfileManagement(user, userService));

        notificationButton.addActionListener(e -> (user.getObserver()).setVisible(true));

        groupManagement.addActionListener(e -> new GroupGui(user));

        friendButton.addActionListener(e -> {
            centerPanel.removeAll();
            new FriendManagement(user, userService);
        });

        addPostButton.addActionListener(e -> {
            centerPanel.removeAll();
            new ContentCreation(user);
        });


        logoutButton.addActionListener(e -> {

            User loggedOutUser = userService.logout();
            if (loggedOutUser != null) {
                setVisible(false);
                new Window1(userService);
            }
        });

        refreshButton.addActionListener(e -> refresh()); // Link refresh button to refresh method
    }

    private void createContentArea(){
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        storiesPanel = new JPanel();
        storiesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        storiesPanel.setBorder(BorderFactory.createTitledBorder("Stories"));
        storiesPanel.setMinimumSize(new Dimension(0,300));


        postsPanel = new JPanel();
        postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS));
        postsPanel.setBorder(BorderFactory.createTitledBorder("Posts"));
        //postsPanel.setMinimumSize(new Dimension(400,300));

        JScrollPane scrollPane = new JScrollPane(storiesPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        contentPanel.add(scrollPane, BorderLayout.NORTH);
        contentPanel.add(new JScrollPane(postsPanel), BorderLayout.CENTER);


        centralPanel.add(contentPanel, BorderLayout.CENTER);

        //mainPanel.add(contentPanel, BorderLayout.CENTER);
        //centerPanel.add(contentPanel);
        // mainPanel.add(centerPanel, BorderLayout.CENTER);


    }

    private void createSocialList(){
        JPanel friendListPanel = new JPanel();
        friendListPanel.setLayout(new GridLayout(3,1));

        friendsPanel = new JPanel();
        friendsPanel.setLayout(new GridLayout(0, 1));
        friendsPanel.setBorder(BorderFactory.createTitledBorder("Friends"));

        friendSuggestionsPanel = new JPanel();
        friendSuggestionsPanel.setLayout(new GridLayout(0, 1));
        friendSuggestionsPanel.setBorder(BorderFactory.createTitledBorder("Friend Suggestions"));

        subSuggestionPanel = new JPanel();
        subSuggestionPanel.setLayout(new GridLayout(0, 1));
        subSuggestionPanel.setBorder(BorderFactory.createTitledBorder("List View"));
        friendSuggestionsPanel.add(subSuggestionPanel);

        GroupSuggestionPanel = new JPanel();
        GroupSuggestionPanel.setLayout(new GridLayout(0, 1));
        GroupSuggestionPanel.setBorder(BorderFactory.createTitledBorder("Group Suggestions"));

        subGroupsPanel = new JPanel();
        subGroupsPanel.setLayout(new GridLayout(0, 1));
        subGroupsPanel.setBorder(BorderFactory.createTitledBorder("List View"));
        GroupSuggestionPanel.add(subGroupsPanel);

        friendListPanel.add(new JScrollPane(friendsPanel));
        friendListPanel.add(new JScrollPane(friendSuggestionsPanel));
        friendListPanel.add(new JScrollPane(GroupSuggestionPanel));

        SocialArea.add(friendListPanel);
        //friendsArea.add(friendSuggestionsPanel);

    }

    public void refresh() {
        try {
            // Clear and reload content
            postsPanel.removeAll();
            storiesPanel.removeAll();
            friendsPanel.removeAll();
            friendSuggestionsPanel.removeAll();
            GroupSuggestionPanel.removeAll();

            refreshUI();
            viewPosts();
            viewStory();
            displayStatus();
            displayFriendSuggestions();
            displayGroupSuggestion();

            System.out.println(user.getHandler().getStories().size());
            System.out.println(user.getHandler().getStoriesByUserId(user.getUserId()).size());
            System.out.println(user.getHandler().getStories().size());
            System.out.println(user.getHandler().getStoriesByUserId(user.getUserId()).size());
            user.getHandler().saveStories();

            user.getGroupManager().saveGroups();

            // Revalidate and repaint
            mainPanel.invalidate();
            mainPanel.revalidate();
            mainPanel.repaint();

            setVisible(false);
            new Homepage(userService, user);

            for (Window window : Window.getWindows()){
                if(!(window instanceof Homepage))
                    window.dispose();
            }


            JOptionPane.showMessageDialog(this, "Page Refreshed");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while refreshing: " + e.getMessage());
        }
    }

    private void checkObservers(){
        if (user.getNotifier().getFriendObservers().isEmpty()){
            System.out.println("No Observers yet");
        }else {
            System.out.println(user.getUserName() + "Friend Observers: " + user.getNotifier().getFriendObservers().size());
        }

        if (Database.getGeneralNotifier().getGeneralObserver().isEmpty()){
            System.out.println("No General Observers yet");
        }else {
            System.out.println( "General Observers: " + Database.getGeneralNotifier().getGeneralObserver().size());
        }
    }


    public JButton createbutton(String text,JPanel panel)
    {
        JButton button = new JButton();
        button.setBackground(Color.black);
        button.setForeground(Color.white);
        button.setFocusable(false);
        button.setText(text);
        panel.add(button);
        return button;
    }

    public static void main(String[] args) {
        Database database =Database.getInstance();
        database.loadUsers();
        ArrayList<User> users = Database.getUsers();
        UserService userService = new UserService(database);
        // User user = users.getFirst();
        LocalDate date = LocalDate.now();
        User user = new User.UserBuilder().setUserId("user22").setDateOfBirth(date).setUserName("Fady").setPassword("33eeff").setEmail("marco@dkd").setStatus("online").build();
        new Homepage(userService, user);

    }
}