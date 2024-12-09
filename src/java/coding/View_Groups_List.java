package coding;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class View_Groups_List extends JFrame {
    private JPanel panel1;
    private Group group;
    private User primaryadmin;
    Map<String,Group> groups;
    private Groupmanager groupmanager;
    View_Groups_List(User primaryadmin,Map<String,Group> groups)
    {
        this.groupmanager = primaryadmin.getGroupmanager();
        this.groups = groups;
        this.primaryadmin = primaryadmin;
        this.primaryadmin = primaryadmin;
        this.group = group;
        setTitle("view Created Groups");

        this.panel1 = new JPanel();
        panel1.setLayout(new GridLayout(0,1));

        // Create a scrollable panel for dynamic content
        JScrollPane scrollPane = new JScrollPane(panel1);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Add suggestions to the panel
        populateGroups(primaryadmin.getGroupmanager().getGroups());

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
            Group currentGroup = groups.get(key);
            if(currentGroup == null){
                System.out.println("Null group");
                continue;
            }
            Custompanel2 customPanel = new Custompanel2(groups.get(key), "manage","remove");
            panel1.add(customPanel);
            customPanel.setPreferredSize(new Dimension(700, 30));
            customPanel.button1.addActionListener(_ -> {
                new PrimaryAdminManagment(primaryadmin,groups.get(key));
                refreshUI();
            });
            customPanel.button2.addActionListener(_ -> {
                if (this.groupmanager != null) {
                    this.groupmanager.saveGroups();
                } else {
                    System.err.println("Groupmanager is not initialized!");
                }
                groupmanager.deletegroup(currentGroup, primaryadmin);
                groups.remove(key);
                groupmanager.saveGroups();
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
