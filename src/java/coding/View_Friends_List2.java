package coding;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class View_Friends_List2 extends  JFrame{
    private JPanel panel1;
    private Group group;
    private User primaryadmin;
    View_Friends_List2(User primaryadmin,Group group)
    {
        this.primaryadmin = primaryadmin;
        this.group = group;
        setTitle("add members");

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
            if(group.getMembers().contains(friend))
                continue;
            CustomPanel customPanel = new CustomPanel(friend, "add");
            customPanel.setPreferredSize(new Dimension(700, 30));
            panel1.add(customPanel);
            customPanel.button1.addActionListener(_ -> {
                  group.getMembers().add(friend);
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
