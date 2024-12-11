package coding;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ViewPost2 extends JFrame {
    private Group group;
    private JPanel panel;
    public ViewPost2(Group group) {
        this.group = group;
        setTitle("View Posts");
        setSize(new Dimension(1000, 1000));
        setLocationRelativeTo(null);


        // Initialize the panel and set a layout
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Use BoxLayout for stacking

        ArrayList<Posts> posts = group.getPosts();
        for (Posts post : posts) {
            Custompanel3 custompanel3 = new Custompanel3(group,"edit","remove");
            panel.add(custompanel3);
            String content = post.getContent();
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