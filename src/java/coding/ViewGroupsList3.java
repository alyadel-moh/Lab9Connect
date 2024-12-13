package coding;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ViewGroupsList3 extends JFrame{
    private JPanel panel1;
    private Group group;
    private User normaluser;
    Map<String,Group> groups;
    ViewGroupsList3(User normaluser,Map<String,Group> groups)
    {
        this.groups = groups;
        this.normaluser = normaluser;
        setTitle("view joined Groups");

        this.panel1 = new JPanel();
        panel1.setLayout(new GridLayout(0,1));

        // Create a scrollable panel for dynamic content
        JScrollPane scrollPane = new JScrollPane(panel1);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Add suggestions to the panel
        populateGroups(normaluser.getGroupManager().getGroups());

        setContentPane(scrollPane);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setBounds(100, 100, 700, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void populateGroups(Map<String,Group> groups) {
        // Clear the panel
        panel1.removeAll();

        if(groups.isEmpty()){
            panel1.add(new JLabel("No Groups to View!"));
            refreshUI();
            return;
        }

        for (String key : groups.keySet()) {
            Custompanel2 customPanel = new Custompanel2(groups.get(key), "add Post","Leave");
            panel1.add(customPanel);
            customPanel.setPreferredSize(new Dimension(700, 30));
            customPanel.button1.addActionListener(_ -> {
                new AddGroupPost(groups.get(key),normaluser);
                refreshUI();
            });
            customPanel.button2.addActionListener(_ -> {
                normaluser.getGroupManager().leavegroup(groups.get(key));
                normaluser.getGroupManager().getSuggestions().remove(groups.get(key));
                panel1.remove(customPanel);
                System.out.println("After removal: " + groups);
                System.out.println("After removal: " + normaluser.getGroupManager().getGroups());
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
