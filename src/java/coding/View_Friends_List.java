package coding;

import javax.swing.*;
import java.util.List;

public class View_Friends_List extends JFrame {
    private User user;
    private UserService service;
    private JPanel panel1;

    View_Friends_List(User user, UserService service) {
        this.user = user;
        this.service = service;
        this.panel1 = new JPanel();

        // Create a scrollable panel for dynamic content
        JScrollPane scrollPane = new JScrollPane(panel1);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Add suggestions to the panel
        populateFriends(user.getManager().getFriends());

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

        for (User friend : friends) {
            CustomPanel customPanel = new CustomPanel(friend, "Block", "Remove");

            // Block Action
            customPanel.button1.addActionListener(e -> {
                user.getManager().block(friend);
                panel1.remove(customPanel);

                CustomPanel newCustomPanel = new CustomPanel(friend, "Undo");
                newCustomPanel.button1.addActionListener(_ -> {
                    user.getManager().unblock(friend);
                    panel1.remove(newCustomPanel);
                    panel1.add(customPanel);
                    refreshUI();
                });

                panel1.add(newCustomPanel);
                refreshUI();
            });

            // Remove Action
            customPanel.button2.addActionListener(e -> {
                user.getManager().getFriends().remove(friend);
                panel1.remove(customPanel);
                refreshUI();
            });

            panel1.add(customPanel);
        }

        // Refresh UI after all updates
        refreshUI();
    }

    private void refreshUI() {
        panel1.revalidate(); // Recalculate layout
        panel1.repaint(); // Redraw components
    }
}
