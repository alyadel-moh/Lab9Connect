package coding;

import javax.swing.*;
import java.awt.*;

public class Groupprofile extends JFrame{
    private Group group;

    Groupprofile(Group group) {
        this.group = group;
        setTitle(group.getName());
        setSize(500, 600);  // Increased size to accommodate the additional titles and images
        setLayout(new BorderLayout());

        // Create a panel to hold user info and images
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Arrange components vertically
        panel.setBackground(new Color(240, 240, 240)); // Set a light background color for the panel
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around the panel

        // Add user information to the panel with some styling
        JLabel nameLabel = new JLabel("Group Name: " + group.getName());
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        nameLabel.setForeground(new Color(50, 50, 50)); // Dark gray color for text
        panel.add(nameLabel);

        JLabel dobLabel = new JLabel("Group description: " + group.getDescription());
        dobLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        dobLabel.setForeground(new Color(50, 50, 50));
        panel.add(dobLabel);

        // Add a title for Profile Picture
        JLabel profileTitle = new JLabel("Group profile");
        profileTitle.setFont(new Font("Arial", Font.BOLD, 12));
        profileTitle.setForeground(new Color(0, 102, 204)); // Blue color for the title
        profileTitle.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the title text
        panel.add(profileTitle);

        // Add Profile Picture
        ImageIcon profileImageIcon = new ImageIcon(new ImageIcon(group.getProfilepath()).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
        JLabel profilePic = new JLabel(profileImageIcon);
        profilePic.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
        panel.add(profilePic);


        // Add the panel to the frame
        add(panel, BorderLayout.CENTER);

        // Set the window to be centered and visible
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

}
