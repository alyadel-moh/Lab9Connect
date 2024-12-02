package coding;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class FriendManagerUI {
    private final JPanel activePanel;

    public FriendManagerUI() {
        activePanel = new JPanel();
        activePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        activePanel.setBackground(Color.WHITE); // Optional: Customize background color
    }

    public void displayStatus(ArrayList<User> friends) {
        activePanel.removeAll(); // Clear previous items

        for (User friend : friends) {
            if ("online".equalsIgnoreCase(friend.getStatus())) {
                // Get friend's profile picture
                ImageIcon profilePic = friend.getProfile();
                if (profilePic != null) {
                    JLabel profileLabel = createCircularLabel(profilePic);
                    activePanel.add(profileLabel);
                }
            }
        }

        activePanel.revalidate();
        activePanel.repaint();
    }

    private JLabel createCircularLabel(ImageIcon imageIcon) {
        int size = 50; // Desired size for profile picture
        Image scaledImage = imageIcon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
        BufferedImage circularImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = circularImage.createGraphics();
        g2.setClip(new Ellipse2D.Double(0, 0, size, size));
        g2.drawImage(scaledImage, 0, 0, null);
        g2.dispose();

        return new JLabel(new ImageIcon(circularImage));
    }

    public JPanel getActivePanel() {
        return activePanel;
    }
}
