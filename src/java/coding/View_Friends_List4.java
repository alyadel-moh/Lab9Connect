package coding;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class View_Friends_List4 extends JFrame {
    private final JPanel panel1;
    private final Group group;
    View_Friends_List4(Group group)
    {
        this.group = group;
        setTitle("view requests");

        this.panel1 = new JPanel();
        panel1.setLayout(new GridLayout(0,1));

        // Create a scrollable panel for dynamic content
        JScrollPane scrollPane = new JScrollPane(panel1);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Add suggestions to the panel
        populateFriends(group.getRequests());

        setContentPane(scrollPane);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setBounds(100, 100, 700, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void populateFriends(ArrayList<Group_Request> requests) {
        // Clear the panel
        panel1.removeAll();

        if(requests.isEmpty()){
            panel1.add(new JLabel("No Friends to View!"));
            refreshUI();
            return;
        }

        for (Group_Request request : requests) {
            CustomPanel customPanel = new CustomPanel(request.getSender(), "add","remove");
            panel1.add(customPanel);
            customPanel.setPreferredSize(new Dimension(700, 30));
            customPanel.button1.addActionListener(_ -> {
                group.getMembers().add(request.getSender());
                panel1.remove(customPanel);
                refreshUI();
            });
            customPanel.button2.addActionListener(_ -> {
                group.getRequests().remove(request);
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
