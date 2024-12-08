package coding;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class SearchManagement extends JFrame {
    private User user;
    private UserService service;
    private JPanel panel1;
    private String searchedString;

    SearchManagement(User user, UserService service, JPanel homePanel,String searchedString) {
        setTitle("Search Management");
        this.user = user;
        this.service = service;
        this.searchedString=searchedString;
        // If the home panel isn't provided
        if (homePanel == null) {
            this.panel1 = new JPanel();
            this.panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS)); // Vertical layout
        } else {
            this.panel1 = homePanel;
        }

        // Create a scrollable panel for dynamic content
        JScrollPane scrollPane = new JScrollPane(panel1);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Add search results to the panel
        populateSearchResults(getSearchResults());

        setContentPane(scrollPane);

        if (homePanel == null) {
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setResizable(false);
            setBounds(100, 100, 700, 400);
            setLocationRelativeTo(null);
            setVisible(true);
        }
    }

    private ArrayList<User> getSearchResults(){
        ArrayList<User>searchedUsers=new ArrayList<>();
        for(int i=0;i<Database.getUsers().size();i++){
            if(Database.getUsers().get(i).getUserName().contains(searchedString)&&!Database.getUsers().get(i).getUserId().equals(user.getUserId())){
                searchedUsers.add(Database.getUsers().get(i));
            }
        }
        return searchedUsers;
    }

    private void populateSearchResults(ArrayList<User> searchResults) {
        // Clear the panel
        panel1.removeAll();

        if (searchResults.isEmpty()) {
            panel1.add(new JLabel("No results For this userName (No user has this name)"));
            refreshUI();
            return;
        }

        for (User searchedUser : searchResults) {
            // Create a panel to display user image and name
            JPanel userPanel = new JPanel();
            userPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

            // Add user image
            ImageIcon userIcon = new ImageIcon(new ImageIcon(searchedUser.getProfilepath()).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
            JLabel userImage = new JLabel(userIcon);
            userPanel.add(userImage);

            // Add user name
            JLabel userName = new JLabel(searchedUser.getUserName());
            userPanel.add(userName);

            // Create buttons for "Send Request" and "Ignore"
//            IgnorebuttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));  // Right-align buttons

            JButton addFriendButton = new JButton("Add Friend");
            addFriendButton.setBackground(Color.BLACK);
            addFriendButton.setForeground(Color.WHITE);
            userPanel.add(addFriendButton);

            JButton removeFriendButton = new JButton("Remove Friend");
            removeFriendButton.setBackground(Color.BLACK);
            removeFriendButton.setForeground(Color.WHITE);
            userPanel.add(removeFriendButton);

            JButton blockUserButton = new JButton("Block friend");
            blockUserButton.setBackground(Color.BLACK);
            blockUserButton.setForeground(Color.WHITE);
            userPanel.add(blockUserButton);

            JButton viewProfileButton = new JButton("View Profile");
            viewProfileButton.setBackground(Color.BLACK);
            viewProfileButton.setForeground(Color.WHITE);
            userPanel.add(viewProfileButton);

            //Add Friend Button
            addFriendButton.addActionListener(e -> {
                // Logic to send friend request (empty method for now)
                boolean flag=false;//indicating he is not your friend
            for(int i=0;i<user.getManager().getFriends().size();i++){
                if(user.getManager().getFriends().get(i).getUserId().equals(searchedUser.getUserId())){
                    JOptionPane.showMessageDialog(null,"This is already your friend");
                    flag=true;//they are friends
                }
            }
            if(!flag){
                user.getManager().getFriends().add(searchedUser);
                user.getFriendHandler().addFriend(user.getUserId(),searchedUser.getUserId());
            }
            });

            //Remove Friend Button
            removeFriendButton.addActionListener(e -> {
                // Logic to ignore the user (empty method for now)
                user.getManager().remove(searchedUser);
                JOptionPane.showMessageDialog(null,"Friend Removed Successfully");
            });

            //Block Friend Button
            blockUserButton.addActionListener(e -> {
                // Logic to ignore the user (empty method for now)
                user.getManager().block(searchedUser);//this user will block the searched user
                JOptionPane.showMessageDialog(null,"User Blocked Successfully");
            });

            viewProfileButton.addActionListener(e -> {
                new Profile(searchedUser);
            });

            // Add the user panel and button panel to the main panel
            panel1.add(userPanel);
//            panel1.add(buttonPanel);
        }

        // Refresh the UI after adding all components
        refreshUI();
    }

    private void refreshUI() {
        panel1.revalidate(); // Recalculate layout
        panel1.repaint();   // Redraw components
    }
}