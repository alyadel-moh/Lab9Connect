package coding;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ViewPost extends JFrame {
    private User user;
    private JPanel panel;

    public ViewPost(User user) {
        this.user = user;
        setTitle(user.getUserName() + "'s Posts");
        setSize(new Dimension(1000, 1000));
        setLocationRelativeTo(null);


        // Initialize the panel and set a layout
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Use BoxLayout for stacking

        ArrayList<Posts> posts = user.getHandler().getPosts();
        for (int i = 0; i < posts.size(); i++) {
            String content = posts.get(i).getContent();
            String[] contentDelim = content.split("@");
            String text = contentDelim[0];
            JLabel label = new JLabel(text);
            System.out.println(text);
            panel.add(label);
            try {
                String imagePath = contentDelim[1];
                if (!imagePath.isEmpty()) {
                    ImageIcon imageIcon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH)); // Scale image
                    JLabel pic = new JLabel(imageIcon);
                    pic.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
                    panel.add(pic);
                    System.out.println(imagePath);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("No image");
            }

        }

        // Add panel to a scroll pane for scrolling
        JScrollPane scrollPane = new JScrollPane(panel);
        setContentPane(scrollPane);


        setVisible(true);

    }
}
