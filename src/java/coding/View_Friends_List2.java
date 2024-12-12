package coding;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class View_Friends_List2 extends  JFrame {
    private final JPanel panel1;
    private final Group group;
    private User primaryadmin;
    private JButton saveButton;

    View_Friends_List2(User primaryadmin, Group group) {
        this.primaryadmin = primaryadmin;
        this.group = group;
        setTitle("add members");

        this.panel1 = new JPanel();
        panel1.setLayout(new GridLayout(0, 1));

        // Create a scrollable panel for dynamic content
        JScrollPane scrollPane = new JScrollPane(panel1);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Add suggestions to the panel
        ArrayList<User> original = primaryadmin.getManager().getFriends();
        //ArrayList<Member> converted = changeToMembers(primaryadmin.getManager().getFriends());
        populateFriends(original);

        setContentPane(scrollPane);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setBounds(100, 100, 700, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void populateFriends(ArrayList<User> friends) {
        // Clear the panel
        panel1.removeAll();
        AtomicInteger count = new AtomicInteger(friends.size());

        if (friends.isEmpty() || count.get() <= 0) {
            panel1.add(new JLabel("No Friends to View!"));
            refreshUI();
            return;
        }

        for (User friend : friends) {
            // Check if the user is a Member
            if (!(friend instanceof Member)) {
                System.out.println("Skipping non-member user: " + friend.getUserName());
               // continue;
            }

            assert friend instanceof Member;
            //Member member = (Member) friend;

            // Skip if the member is already in the group
            if (group.getMembers().contains(friend)) {
                System.out.println("Skipping member already in group: " + friend.getUserName());
                continue;
            }

            // Create a custom panel for the member
            CustomPanel customPanel = new CustomPanel(friend, "add");
            customPanel.setPreferredSize(new Dimension(700, 30));
            panel1.add(customPanel);

            // Add action listener to the button
            customPanel.button1.addActionListener(event -> {
                System.out.println("Adding member to group: " + friend.getUserName());

                // Add the member to the group
                group.getMembers().add(friend);

                // Update the member's group
                friend.getGroupManager().getGroups().put(group.getName(), group);

                // Remove the custom panel and decrement count
                panel1.remove(customPanel);
                count.set(count.get() - 1);
                refreshUI();
            });
        }
        // Refresh UI after all updates
        refreshUI();
    }

    private void refreshUI() {
        panel1.revalidate(); // Recalculate layout
        panel1.repaint(); // Redraw components
    }

    private ArrayList<Member> changeToMembers(ArrayList<User> users) {
        ArrayList<Member> members = new ArrayList<>();
        for (User user : users){
            members.add((Member) user);
        }
        return members;
    }
}
