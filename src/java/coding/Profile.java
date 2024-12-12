package coding;

import javax.swing.*;
import java.awt.*;

public class Profile extends JFrame {
    private User user;

    Profile(User user) {
        this.user = user;
        setTitle(user.getUserName());
        setSize(500, 600);  // Increased size to accommodate the additional titles and images
        setLayout(new BorderLayout());

        // Create a panel to hold user info and images
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Arrange components vertically
        panel.setBackground(new Color(240, 240, 240)); // Set a light background color for the panel
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around the panel

        // Add user information to the panel with some styling
        JLabel nameLabel = new JLabel("Name: " + user.getUserName());
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        nameLabel.setForeground(new Color(50, 50, 50)); // Dark gray color for text
        panel.add(nameLabel);

        JLabel dobLabel = new JLabel("Date Of Birth: " + user.getDateOfBirth());
        dobLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        dobLabel.setForeground(new Color(50, 50, 50));
        panel.add(dobLabel);

        JLabel emailLabel = new JLabel("Email: " + user.getEmail());
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        emailLabel.setForeground(new Color(50, 50, 50));
        panel.add(emailLabel);

        if (user.getBio() != null) {
            JLabel bioLabel = new JLabel("Bio: " + user.getBio());
            bioLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            bioLabel.setForeground(new Color(50, 50, 50));
            panel.add(bioLabel);
        }

        // Add a title for Profile Picture
        JLabel profileTitle = new JLabel("Profile Picture");
        profileTitle.setFont(new Font("Arial", Font.BOLD, 12));
        profileTitle.setForeground(new Color(0, 102, 204)); // Blue color for the title
        profileTitle.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the title text
        panel.add(profileTitle);

        // Add Profile Picture
        ImageIcon profileImageIcon = new ImageIcon(new ImageIcon(user.getProfilePath()).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
        JLabel profilePic = new JLabel(profileImageIcon);
        profilePic.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
        panel.add(profilePic);


        // Add a title for Cover Picture
        JLabel coverTitle = new JLabel("Cover Picture");
        coverTitle.setFont(new Font("Arial", Font.BOLD, 12));
        coverTitle.setForeground(new Color(0, 102, 204)); // Blue color for the title
        coverTitle.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the title text
        panel.add(coverTitle);

        // Add Cover Picture
        ImageIcon coverImageIcon = new ImageIcon(new ImageIcon(user.getCoverPath()).getImage().getScaledInstance(400, 200, Image.SCALE_SMOOTH));
        JLabel coverPic = new JLabel(coverImageIcon);
        coverPic.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
        panel.add(coverPic);

        JButton viewPosts = new JButton("View post");
        viewPosts.setPreferredSize(new Dimension(200, 30));
        viewPosts.setMaximumSize(new Dimension(200, 30));
        viewPosts.setBackground(Color.BLACK);
        viewPosts.setForeground(Color.WHITE);
        panel.add(viewPosts);

        JButton viewStories = new JButton("View stories");
        viewStories.setPreferredSize(new Dimension(200, 30));
        viewStories.setMaximumSize(new Dimension(200, 30));
        viewStories.setBackground(Color.BLACK);
        viewStories.setForeground(Color.WHITE);
        panel.add(viewStories);

        // View Post Button
        viewPosts.addActionListener(e -> {
            new ViewPost(user);
                });
        //View Stories button
        viewStories.addActionListener(e -> {
            new ViewStories(user);
        });


        // Add the panel to the frame
        add(panel, BorderLayout.CENTER);

        // Set the window to be centered and visible
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

}
