package coding;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Suggestions_Management extends JFrame {
    private User user;
    private UserService service;
    private JPanel panel1;

    Suggestions_Management(User user, UserService service, JPanel homePanel) {
        this.user = user;
        this.service = service;

        // If desired panel isn't given
        if (homePanel == null) {
            this.panel1 = new JPanel();
            this.panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        } else {
            this.panel1 = homePanel;
        }

        // Create a scrollable panel for dynamic content
        JScrollPane scrollPane = new JScrollPane(panel1);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Add suggestions to the panel
        populateSuggestions(user.getSuggestions());

        setContentPane(scrollPane);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setBounds(100, 100, 300, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void populateSuggestions(List<User> suggestions) {
        panel1.removeAll(); // Clear existing components

        for (User suggested : suggestions) {
            CustomPanel customPanel = new CustomPanel(suggested, "Send Request", "Ignore");

            // Send Request
            customPanel.button1.addActionListener(e -> {
                user.getManager().sendRequest(suggested);
                suggestions.remove(suggested);
                refreshUI();
            });

            // Ignore Request
            customPanel.button2.addActionListener(e -> {
                suggestions.remove(suggested);
                refreshUI();
            });

            panel1.add(customPanel);
        }

        refreshUI(); // Ensure UI updates
    }

    private void refreshUI() {
        panel1.revalidate(); // Recalculate layout
        panel1.repaint(); // Redraw components
    }
}
