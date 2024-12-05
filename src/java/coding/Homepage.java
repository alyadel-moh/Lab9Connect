package coding;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import coding.ContentCreation;
import coding.testtt.CircleButton;

public class Homepage extends JFrame {
    private static Homepage instance;
    private JPanel mainPanel;
    private JPanel centralPanel;
    private JPanel centerPanel;
    private JPanel postsPanel;
    private JPanel storiesPanel;
    private JPanel friendsPanel;
    private JPanel friendSuggestionsPanel;
    private JPanel activePanel;
    private JTextArea contentCreationArea;
    private JButton postButton,refreshButton;
    private User user;
    private UserService userService;



    public Homepage(UserService userService, User user) {
        this.user = user;
        this.userService = userService;

        setTitle("Homepage");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100,100,1400,900);
        setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        centralPanel = new JPanel();
        centralPanel.setLayout(new BorderLayout());

        centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());

        activePanel = new JPanel();
        add(new JScrollPane(activePanel), BorderLayout.NORTH);

        createHeader();
        createContentArea();
        createFriendList();

        viewPosts();
        viewStory();
        //displayStatus();

        add(mainPanel);
        setVisible(true);

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
                button.setSize(70,70);
                Image image = new ImageIcon(user.getProfilepath()).getImage();
                Image scaled = image.getScaledInstance(button.getHeight(),button.getWidth(),Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(scaled));
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

    private void viewStories() {
        coding.testtt.src.CircleButton button = new coding.testtt.src.CircleButton("userr");
        storiesPanel.add(button);

    }

    private void viewPosts() {
        postsPanel.removeAll(); // Clear previous posts
        ArrayList<User> users = userService.getDatabase().getUsers();
        for(User user : users) {
            ArrayList<Posts> posts = user.getHandler().getPosts();
            if (posts != null && !posts.isEmpty()) {
                System.out.println("Total posts to display: " + posts.size());
                for (Posts post : posts) {
                    // Extract and display text content
                    String content = post.getContent();
                    String[] contentDelim = content.split("@");
                    String text = contentDelim[0];

                    JLabel textLabel = new JLabel(text);
                    textLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Add padding
                    postsPanel.add(textLabel);

                    // Try to extract and display image if available
                    try {
                        String imagePath = contentDelim[1];
                        if (!imagePath.isEmpty()) {
                            ImageIcon imageIcon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH)); // Scale image
                            JLabel imageLabel = new JLabel(imageIcon);
                            imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
                            postsPanel.add(imageLabel);
                            System.out.println("Image path: " + imagePath);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("No image found for this post.");
                    }
                }
            } else {
                System.out.println("No posts available to display.");
            }
        }

        // Refresh UI
        postsPanel.revalidate();
        postsPanel.repaint();
    }


    private void displayStatus(){
        user.getManager().DisplayStatus(this);
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

        JTextField searchField = new JTextField(20);
        searchField.setText("Search");
        headerPanel.add(searchField);

        JButton friendRequests = createbutton("Manage Friends",headerPanel);
        JButton friendButton = createbutton("Manage Friends",headerPanel);
        JButton notificationButton = createbutton("Notifications",headerPanel);
        JButton profileButton =createbutton("Profile managment",headerPanel);
        JButton addPostButton =createbutton("Create Content",headerPanel);
        JButton logoutButton = createbutton("Logout",headerPanel);
        refreshButton =  createbutton("Refresh",headerPanel);

        headerPanel.setBackground(Color.LIGHT_GRAY);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Event Listeners
        profileButton.addActionListener(e -> new ProfileManagement(user, userService));

        friendRequests.addActionListener(e -> new FriendManagement(user,userService));

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

        contentPanel.add(storiesPanel, BorderLayout.NORTH);
        contentPanel.add(new JScrollPane(postsPanel), BorderLayout.CENTER);

       // centralPanel.add(contentPanel, BorderLayout.CENTER);

        mainPanel.add(contentPanel, BorderLayout.CENTER);
        //centerPanel.add(contentPanel);
       // mainPanel.add(centerPanel, BorderLayout.CENTER);
    }

    private void createFriendList(){
        JPanel friendListPanel = new JPanel();
        friendListPanel.setLayout(new BoxLayout(friendListPanel, BoxLayout.Y_AXIS));

        friendsPanel = new JPanel();
        friendsPanel.setBorder(BorderFactory.createTitledBorder("Friends"));

        friendSuggestionsPanel = new JPanel();
        friendSuggestionsPanel.setBorder(BorderFactory.createTitledBorder("Friend Suggestions"));

        friendListPanel.add(friendSuggestionsPanel);
        friendListPanel.add(friendsPanel);

        mainPanel.add(friendListPanel, BorderLayout.EAST);

    }

    public void refresh() {
        // Clear existing posts
        postsPanel.removeAll();
        storiesPanel.removeAll();

        // Fetch and display updated posts
        viewPosts();
        viewStory();

        // Ensure the UI is updated
        postsPanel.revalidate();
        postsPanel.repaint();

        storiesPanel.revalidate();
        storiesPanel.repaint();

        JOptionPane.showMessageDialog(this, "Page Refreshed");
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
        Database database = new Database();
        database.loadUsers();
        ArrayList<User> users = database.getUsers();
        UserService userService = new UserService(database);
        // User user = users.getFirst();
        LocalDate date = LocalDate.now();
        User user = new User.UserBuilder().setUserId("user22").setDateOfBirth(date).setUserName("Fady").setPassword("33eeff").setEmail("marco@dkd").setStatus("online").build();
        new Homepage(userService, user);

    }
}


