package coding;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Requests_Management extends JFrame {
    private User user;
    private JPanel panel1;

    Requests_Management(User user) {
        this.user = user;

        panel1 = new JPanel();
        panel1.setLayout(new GridLayout(0, 1));

        updateRequestsUI();

        setContentPane(new JScrollPane(panel1)); // Add scroll functionality
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setBounds(100, 100, 700, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateRequestsUI() {
        panel1.removeAll(); // Clear existing components

        List<FriendRequest> requests = user.getRequests();
        if (requests.isEmpty()) {
            panel1.add(new JLabel("No Requests to View!"));
        } else {
            for (FriendRequest request : requests) {
                CustomPanel customPanel = new CustomPanel(request.getSender(), "Accept", "Decline");
                customPanel.setPreferredSize(new Dimension(700, 30));

                // Accept Request
                customPanel.button1.addActionListener(e -> handleRequest(request, customPanel, true));

                // Decline Request
                customPanel.button2.addActionListener(e -> handleRequest(request, customPanel, false));

                panel1.add(customPanel);
            }
        }

        refreshUI();
    }

    private void handleRequest(FriendRequest request, CustomPanel customPanel, boolean isAccepted) {
        if (isAccepted) {
            user.getManager().accept(request);
        } else {
            user.getManager().decline(request);
        }

        user.getRequests().remove(request);
        panel1.remove(customPanel);

        // Update UI dynamically
        updateRequestsUI();
    }

    private void refreshUI() {
        panel1.revalidate();
        panel1.repaint();
    }
}
