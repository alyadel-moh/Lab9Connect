package coding;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class View_Members_List extends JFrame{
    private JPanel panel1;
    private Group group;

    View_Members_List(Group group)
    {

        this.group = group;
        setTitle("view Members");

        this.panel1 = new JPanel();
        panel1.setLayout(new GridLayout(0,1));

        // Create a scrollable panel for dynamic content
        JScrollPane scrollPane = new JScrollPane(panel1);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Add suggestions to the panel
        populateMembers(group.getMembers());

        setContentPane(scrollPane);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setBounds(100, 100, 700, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void populateMembers(ArrayList<User> members) {
        // Clear the panel
        panel1.removeAll();

        if(members.isEmpty()){
            panel1.add(new JLabel("No Members to View!"));
            refreshUI();
            return;
        }

        for (User member : members) {
            CustomPanel customPanel = new CustomPanel(member, "Remove");
            customPanel.setPreferredSize(new Dimension(700, 30));
            panel1.add(customPanel);
            customPanel.button1.addActionListener(_ -> {
                group.getMembers().remove(member);
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
