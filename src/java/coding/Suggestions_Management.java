package coding;

import javax.swing.*;
import java.util.ArrayList;

public class Suggestions_Management extends JFrame {
    private User user;
    private UserService service;
    private JPanel panel1;

    Suggestions_Management(User user, UserService service, JPanel homePanel) {
        setTitle("Friends Suggestions");
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

        if (homePanel == null) {
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setResizable(false);
            setBounds(100, 100, 700, 400);
            setLocationRelativeTo(null);
            setVisible(true);
        }
    }

    private void populateSuggestions(ArrayList<User> suggestions) {
        // Clear the panel
        panel1.removeAll();

        if (suggestions.isEmpty()) {
            panel1.add(new JLabel("No Suggestions to View!"));
            refreshUI();
            return;
        }

        for (User suggested : suggestions) {
            String state = "not available";

            FriendRequest request = (FriendRequest) RequestFactory.createRequest("friend request", user, suggested.getUserId());

            if (user.getManager().getRequests().contains(request)) {
                state = user.getManager().getRequestbySender(user, suggested).getState();
            }

            if ("Pending".equalsIgnoreCase(state)) {
                // Create a panel for the "Pending" state
                CustomPanel pendingPanel = new CustomPanel(suggested, "Pending");
                pendingPanel.button1.addActionListener(e -> {
                    user.getManager().getRequestbySender(user, suggested).setState("new");
                    panel1.remove(pendingPanel);

                    CustomPanel sendRequestPanel = createSendRequestPanel(suggested);
                    panel1.add(sendRequestPanel);
                    refreshUI();
                });

                panel1.add(pendingPanel);
            } else {
                // Create a panel for the "Send Request" and "Ignore" actions
                CustomPanel sendRequestPanel = createSendRequestPanel(suggested);
                panel1.add(sendRequestPanel);
            }
        }

        // Refresh the UI after adding all panels
        refreshUI();
    }

    private CustomPanel createSendRequestPanel(User suggested) {
        CustomPanel customPanel = new CustomPanel(suggested, "Send Request", "Ignore");

        // Send Request Action
        customPanel.button1.addActionListener(e -> {
            user.getManager().sendRequest(suggested);
            panel1.remove(customPanel);

            CustomPanel pendingPanel = new CustomPanel(suggested, "Pending");
            pendingPanel.button1.addActionListener(_ -> {
                user.getManager().getRequestbySender(user, suggested).setState("new");
                panel1.remove(pendingPanel);
                panel1.add(customPanel);
                refreshUI();
            });

            panel1.add(pendingPanel);
            refreshUI();
        });

        // Ignore Action
        customPanel.button2.addActionListener(e -> {
            user.getSuggestions().remove(suggested);
            panel1.remove(customPanel);
            refreshUI();
        });

        return customPanel;
    }

    private void refreshUI() {
        panel1.revalidate(); // Recalculate layout
        panel1.repaint();   // Redraw components
    }
}
