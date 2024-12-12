package coding;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class View_Groups_List2 extends JFrame {
    private JPanel panel1;
    private Group group;
    private User otheradmin;
    Map<String,Group> groups;
    View_Groups_List2(User otheradmin,Map<String,Group> groups)
    {
        this.groups = groups;
        this.otheradmin = otheradmin;
        setTitle("view my Groups");

        this.panel1 = new JPanel();
        panel1.setLayout(new GridLayout(0,1));

        // Create a scrollable panel for dynamic content
        JScrollPane scrollPane = new JScrollPane(panel1);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Add suggestions to the panel
        populateGroups(otheradmin.getGroupManager().getOther());

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
            Custompanel2 customPanel = new Custompanel2(groups.get(key), "manage");
            panel1.add(customPanel);
            customPanel.setPreferredSize(new Dimension(700, 30));
            customPanel.button1.addActionListener(_ -> {
               new Otheradmin(otheradmin,groups.get(key));
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
