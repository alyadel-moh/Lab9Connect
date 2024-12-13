package coding;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class View_Posts_List extends JFrame{
    private Group group;
    private UserService service;
    private JPanel panel1;
View_Posts_List(Group group)
{
    this.group = group;
    setTitle("view Posts");
    this.panel1 = new JPanel();
    panel1.setLayout(new GridLayout(0,1));

    // Create a scrollable panel for dynamic content
    JScrollPane scrollPane = new JScrollPane(panel1);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

    // Add suggestions to the panel
    populateMembers(group.getPosts());

    setContentPane(scrollPane);

    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setResizable(false);
    setBounds(100, 100, 700, 400);
    setLocationRelativeTo(null);
    setVisible(true);
}

    private void populateMembers(ArrayList<Posts> posts) {
        // Clear the panel
        panel1.removeAll();

        if(posts.isEmpty()){
            panel1.add(new JLabel("No Members to View!"));
            refreshUI();
            return;
        }

        for (Posts post : posts) {
            Custompanel3 customPanel = new Custompanel3(group, "Remove","edit");
            customPanel.setPreferredSize(new Dimension(700, 30));
            panel1.add(customPanel);
            customPanel.button1.addActionListener(_ -> {
                group.getPosts().remove(post);
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
