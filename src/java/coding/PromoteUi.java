package coding;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PromoteUi extends JFrame {
    private final JPanel panel1;
    private final Group group;
    PromoteUi(Group group)
    {
        this.group = group;
        setTitle("Promote or demote other admins");

        this.panel1 = new JPanel();
        panel1.setLayout(new GridLayout(0,1));

        // Create a scrollable panel for dynamic content
        JScrollPane scrollPane = new JScrollPane(panel1);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Add suggestions to the panel
        populateFriends(group.getMembers());
        setContentPane(scrollPane);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setBounds(100, 100, 700, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void populateFriends(ArrayList<User> users) {
        // Clear the panel
        panel1.removeAll();

        if(users.isEmpty()){
            panel1.add(new JLabel("No Friends to View!"));
            refreshUI();
            return;
        }

        for (User user : users) {
            CustomPanel customPanel = new CustomPanel(user,"Promote");
            panel1.add(customPanel);
            customPanel.setPreferredSize(new Dimension(700, 30));
            customPanel.button1.addActionListener(_ -> {
                group.promote(group,user);
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
