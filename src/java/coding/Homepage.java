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
    private JPanel centralPanel;
    private JPanel centerPanel;
    private JPanel postsPanel;
    private JPanel storiesPanel;
    private JPanel friendsArea;
    private JPanel friendsPanel;
    private JPanel friendSuggestionsPanel;
    private JPanel subSuggestionPanel;
    private JPanel activePanel;
    private JTextArea contentCreationArea;
    private JButton postButton,refreshButton;
    private User user;
    private UserService userService;



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

        friendsArea = new JPanel();
        friendsArea.setLayout(new BoxLayout(friendsArea, BoxLayout.Y_AXIS));
        // friendsArea.setLayout(new GridLayout(2,1));

        createHeader();
        createContentArea();
        createFriendList();

        centerPanel.add(centralPanel);
        centerPanel.add(friendsArea);

        viewPosts();
        viewStory();
        displayStatus();
        displaySuggestions();

        mainPanel.add(centerPanel, BorderLayout.CENTER);


        add(mainPanel);
        setVisible(true);
        user.getHandler().deleteExpiredStories();
        user.getHandler().saveStories();

    }

    public static synchronized Homepage getInstance(UserService service, User user) {
        if (instance == null) {
            instance = new Homepage(service, user);
        }
        return instance;
    }

    private void viewStory() {
        storiesPanel.removeAll();
        ArrayList<User> users = userService.getDatabase().getUsers();
        if (users != null && !users.isEmpty()) {
            for (User user : users) {
                JButton button = createbutton("",storiesPanel);
                button.setBackground(Color.white);
                button.setBorder(BorderFactory.createLineBorder(Color.black));
                button.setFocusPainted(false);
                Image image = new ImageIcon(user.getProfilepath()).getImage();
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
    }



    private void viewPosts() {
        postsPanel.removeAll(); // Clear previous posts
        ArrayList<User> users = userService.getDatabase().getUsers();

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

    private void displaySuggestions() {
        // Clear the panel
        friendSuggestionsPanel.removeAll();

        if(user.getSuggestions().isEmpty()){
            friendSuggestionsPanel.add(new JLabel("No Suggestions to View!"));
            refreshUI();
            return;
        }

        for (User suggested : user.getSuggestions()) {
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

                    CustomPanel sendRequestPanel = createSendRequestPanel(suggested);
                    friendSuggestionsPanel.add(sendRequestPanel);
                    refreshUI();
                });

                friendSuggestionsPanel.add(pendingPanel);
            } else {
                // Create a panel for the "Send Request" and "Ignore" actions
                CustomPanel sendRequestPanel = createSendRequestPanel(suggested);
                friendSuggestionsPanel.add(sendRequestPanel);
            }
        }


        // Refresh the UI after adding all panels
        refreshUI();
    }

    private CustomPanel createSendRequestPanel(User suggested) {
        CustomPanel customPanel = new CustomPanel(suggested, "Send Request", "Ignore");

        // Send Request Action
        customPanel.button1.addActionListener(e -> {
            user.getManager().sendRequest(suggested);
            friendSuggestionsPanel.remove(customPanel);

            CustomPanel pendingPanel = new CustomPanel(suggested, "Pending");
            pendingPanel.button1.addActionListener(_ -> {
                user.getManager().getRequestbySender(user, suggested).setState(STATE.CANCELLED);
                friendSuggestionsPanel.remove(pendingPanel);
                friendSuggestionsPanel.add(customPanel);
                refreshUI();
            });

            friendSuggestionsPanel.add(pendingPanel);
            refreshUI();
        });

        // Ignore Action
        customPanel.button2.addActionListener(e -> {
            user.getSuggestions().remove(suggested);
            friendSuggestionsPanel.remove(customPanel);
            refreshUI();
        });

        return customPanel;
    }

    private void refreshUI() {
        friendSuggestionsPanel.revalidate(); // Recalculate layout
        friendSuggestionsPanel.repaint();   // Redraw components
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
        JButton groupmanagment = createbutton("Group mangment",headerPanel);

        headerPanel.setBackground(Color.LIGHT_GRAY);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Event Listeners
        profileButton.addActionListener(e -> new ProfileManagement(user, userService));

        notificationButton.addActionListener(e -> new Notifications(user));
        groupmanagment.addActionListener(e -> new GroupGui(user));

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

    private void createFriendList(){
        JPanel friendListPanel = new JPanel();
        friendListPanel.setLayout(new GridLayout(2,1));

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


        friendListPanel.add(friendsPanel);
        friendListPanel.add(new JScrollPane(friendSuggestionsPanel));

        friendsArea.add(friendListPanel);
        //friendsArea.add(friendSuggestionsPanel);

    }

    public void refresh() {
        try {
            // Clear and reload content
            postsPanel.removeAll();
            storiesPanel.removeAll();
            friendsPanel.removeAll();
            friendSuggestionsPanel.removeAll();

            viewPosts();
            viewStory();
            displayStatus();
            displaySuggestions();
            System.out.println(user.getHandler().getStories().size());
            System.out.println(user.getHandler().getStoriesByUserId(user.getUserId()).size());
            System.out.println(user.getHandler().getStories().size());
            System.out.println(user.getHandler().getStoriesByUserId(user.getUserId()).size());
            user.getHandler().saveStories();

            // Revalidate and repaint
            mainPanel.invalidate();
            mainPanel.revalidate();
            mainPanel.repaint();

            setVisible(false);

            new Homepage(userService, user);

            JOptionPane.showMessageDialog(this, "Page Refreshed");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while refreshing: " + e.getMessage());
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
        ArrayList<User> users = database.getUsers();
        UserService userService = new UserService(database);
        // User user = users.getFirst();
        LocalDate date = LocalDate.now();
        User user = new User.UserBuilder().setUserId("user22").setDateOfBirth(date).setUserName("Fady").setPassword("33eeff").setEmail("marco@dkd").setStatus("online").build();
        new Homepage(userService, user);

    }
}