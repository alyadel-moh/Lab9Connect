package coding;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Homepage extends JFrame {
    private JPanel mainPanel;
    private JPanel postsPanel;
    private JPanel storiesPanel;
    private JPanel friendsPanel;
    private JPanel friendSuggestionsPanel;
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
        createHeader();
        createContentArea();
        createFriendList();
        add(mainPanel);
        setVisible(true);
    }
    private void createHeader(){
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel Title = new JLabel("Connect Hub");
        Title.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(Title);

        JTextField searchField = new JTextField(20);
        searchField.setText("Search");
        headerPanel.add(searchField);

        JButton friendRequestsButton = new JButton("FriendRequests");
        JButton notificationButton = new JButton("Notifications");
        JButton profileButton = new JButton("Profile");
        JButton addPostButton = new JButton("Add Post");
        JButton logoutButton = new JButton("Logout");
        headerPanel.add(logoutButton);
        headerPanel.add(friendRequestsButton);
        headerPanel.add(notificationButton);
        headerPanel.add(profileButton);
        headerPanel.add(addPostButton);


        headerPanel.setBackground(Color.LIGHT_GRAY);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
       // mainPanel.add(refreshButton, BorderLayout.SOUTH);
        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProfileManagement(user,userService);
            }
        });
        addPostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddPost(user);
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user =  userService.logout();
                if(user != null) {
                    setVisible(false);
                    new Window1(userService);
                }
            }
        });
//        refreshButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JOptionPane.showMessageDialog(null,"Page Refreshed");
//            }
//        });
    }
    private void createContentArea(){
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(3, 1));
        storiesPanel = new JPanel();
        storiesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        storiesPanel.setBorder(BorderFactory.createTitledBorder("Stories"));
        postsPanel = new JPanel();
        postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS));
        contentPanel.add(storiesPanel);
        contentPanel.add(postsPanel);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
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
}
