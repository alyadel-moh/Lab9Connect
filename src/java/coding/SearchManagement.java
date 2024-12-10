package coding;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class SearchManagement extends JFrame {
    private final User user;
    private final UserService service;
    private final JPanel panel1;
    private final String searchedString;
    private final Group_Manager groupmanager;
    private Group group;

    SearchManagement(User user, UserService service, JPanel homePanel,String searchedString) {
        setTitle("Search Management");
        this.user = user;
        this.service = service;
        this.searchedString=searchedString;
        this.groupmanager = user.getGroupManager();
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

    private ArrayList<Object> getSearchResults(){
        ArrayList<Object> searchResults = new ArrayList<>();
        for(int i=0;i<Database.getUsers().size();i++){
            if(Database.getUsers().get(i).getUserName().contains(searchedString)&&!Database.getUsers().get(i).getUserId().equals(user.getUserId())){
                searchResults.add(Database.getUsers().get(i));
            }
        }

        // Search for groups
        for (Map.Entry<String, Group> entry : Group_Manager.getAllgroups().entrySet()) {
            System.out.println("Group in manager: " + entry.getKey() + " - " + entry.getValue().getName());
            Group group = entry.getValue();
            if (group.getName().toLowerCase().contains(searchedString.toLowerCase())) {
                searchResults.add(group); // Add group to search results
            }
        }
        return searchResults;
    }

    private void populateSearchResults(ArrayList<Object> searchResults) {
        // Clear the panel
        panel1.removeAll();

        if (searchResults.isEmpty()) {
            panel1.add(new JLabel("No results found for: " + searchedString));
            refreshUI();
            return;
        }

        // Iterate through both user and group results
        for (Object result : searchResults) {
            if (result instanceof User searchedUser) {
                JPanel userPanel = createUserPanel(searchedUser);
                panel1.add(userPanel);
            } else if (result instanceof Group searchedGroup) {
                System.out.println("Displaying group: " + searchedGroup.getName());
                JPanel groupPanel = createGroupPanel(searchedGroup);
                panel1.add(groupPanel);
            }
        }

        // Refresh the UI after adding all components
        refreshUI();
    }

    private JPanel createUserPanel(User searchedUser) {
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        ImageIcon userIcon = new ImageIcon(new ImageIcon(searchedUser.getProfilepath()).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        JLabel userImage = new JLabel(userIcon);
        userPanel.add(userImage);

        JLabel userName = new JLabel(searchedUser.getUserName());
        userPanel.add(userName);

        JButton addFriendButton = new JButton("Add Friend");
        addFriendButton.setBackground(Color.BLACK);
        addFriendButton.setForeground(Color.WHITE);
        userPanel.add(addFriendButton);

        JButton removeFriendButton = new JButton("Remove Friend");
        removeFriendButton.setBackground(Color.BLACK);
        removeFriendButton.setForeground(Color.WHITE);
        userPanel.add(removeFriendButton);

        JButton blockUserButton = new JButton("Block User");
        blockUserButton.setBackground(Color.BLACK);
        blockUserButton.setForeground(Color.WHITE);
        userPanel.add(blockUserButton);

        JButton viewProfileButton = new JButton("View Profile");
        viewProfileButton.setBackground(Color.BLACK);
        viewProfileButton.setForeground(Color.WHITE);
        userPanel.add(viewProfileButton);

        // Add Friend Button
        addFriendButton.addActionListener(e -> {
            boolean flag = false; // indicating they are not your friend
            for (int i = 0; i < user.getManager().getFriends().size(); i++) {
                if (user.getManager().getFriends().get(i).getUserId().equals(searchedUser.getUserId())) {
                    JOptionPane.showMessageDialog(null, "This is already your friend");
                    flag = true; // they are friends
                }
            }
            if (!flag) {
                user.getManager().getFriends().add(searchedUser);
                user.getFriendHandler().addFriend(user.getUserId(), searchedUser.getUserId());
            }
        });

        // Remove Friend Button
        removeFriendButton.addActionListener(e -> {
            user.getManager().remove(searchedUser);
            JOptionPane.showMessageDialog(null, "Friend Removed Successfully");
        });

        // Block Friend Button
        blockUserButton.addActionListener(e -> {
            user.getManager().block(searchedUser); // this user will block the searched user
            JOptionPane.showMessageDialog(null, "User Blocked Successfully");
        });

        // View Profile Button
        viewProfileButton.addActionListener(e -> {
            new Profile(searchedUser);
        });

        return userPanel;
    }

    private JPanel createGroupPanel(Group searchedGroup) {
        JPanel groupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel groupName = new JLabel(searchedGroup.getName());
        groupPanel.add(groupName);

        JButton joinGroupButton = new JButton("Join Group");
        JButton leaveGroupButton = new JButton("Leave Group");
        JButton viewGroupButton = new JButton("View Group");

        joinGroupButton.addActionListener(e -> joinGroup(searchedGroup));
        leaveGroupButton.addActionListener(e -> leaveGroup(searchedGroup));
        viewGroupButton.addActionListener(e -> viewGroup(searchedGroup));

        groupPanel.add(joinGroupButton);
        groupPanel.add(leaveGroupButton);
        groupPanel.add(viewGroupButton);

        return groupPanel;
    }

    // Join group logic
    private void joinGroup(Group searchedGroup) {
        if (searchedGroup.getMembers().contains(user)) {
            JOptionPane.showMessageDialog(null, "You are already a member of this group.");
            return;
        }
        searchedGroup.getMembers().add(user);
        JOptionPane.showMessageDialog(null, "Joined group successfully.");
    }

    // Leave group logic
    private void leaveGroup(Group searchedGroup) {
        if (!searchedGroup.getMembers().contains(user)) {
            JOptionPane.showMessageDialog(null, "You are not a member of this group.");
            return;
        }
        searchedGroup.getMembers().remove(user);
        JOptionPane.showMessageDialog(null, "Left group successfully.");
    }

    // View group logic
    private void viewGroup(Group searchedGroup) {
        if (!searchedGroup.getMembers().contains(user)) {
            JOptionPane.showMessageDialog(null, "You need to join the group to view its posts.");
        }
        // new GroupProfile(searchedGroup, user);
    }

    private void refreshUI() {
        panel1.revalidate(); // Recalculate layout
        panel1.repaint();   // Redraw components
    }
}