package coding;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class View_Friends_List3 extends JFrame {
    private final JPanel panel1;
    private final Group group;
    private final User primaryadmin;
    View_Friends_List3(User primaryadmin,Group group)
    {
        this.primaryadmin = primaryadmin;
        this.group = group;
        setTitle("add other admins");

        this.panel1 = new JPanel();
        panel1.setLayout(new GridLayout(0,1));

        // Create a scrollable panel for dynamic content
        JScrollPane scrollPane = new JScrollPane(panel1);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Add suggestions to the panel
        populateFriends(primaryadmin.getManager().getFriends());

        setContentPane(scrollPane);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setBounds(100, 100, 700, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void populateFriends(List<User> friends) {
        // Clear the panel
        panel1.removeAll();

        if(friends.isEmpty()){
            panel1.add(new JLabel("No Friends to View!"));
            refreshUI();
            return;
        }

        for (User friend : friends) {
            CustomPanel customPanel = new CustomPanel(friend, "add");
            panel1.add(customPanel);
            customPanel.setPreferredSize(new Dimension(700, 30));
            customPanel.button1.addActionListener(_ -> {
                group.getOtherAdmins().add(friend);
                friend.getGroupManager().getOther().put(group.getName(),group);
                if(group.getMembers().contains(friend))
                group.getMembers().remove(friend);
                friend.getGroupManager().saveGroups();
                panel1.remove(customPanel);
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
    }
