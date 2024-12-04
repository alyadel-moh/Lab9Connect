package coding;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ViewStories extends JFrame{
    private User user;
    private JPanel panel;

    public ViewStories(User user) {
        this.user = user;
        setTitle("View My Stories");
        setSize(new Dimension(1000, 1000));
        setLocationRelativeTo(null);


        // Initialize the panel and set a layout
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // To arrange them in Boxes arranged Vertically

        ArrayList<Stories> stories = user.getHandler().getStories();
        for (int i = 0; i < stories.size(); i++) {
            String content = stories.get(i).getContent();
            String[] contentDelim = content.split("@");//since content is text@image we have to delimite them extract text and image path alone
            String text = contentDelim[0];
            JLabel label = new JLabel(text);
//            System.out.println(text);
            panel.add(label);
            try {
                String imagePath = contentDelim[1];
                if (!imagePath.isEmpty()) {
                    ImageIcon imageIcon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)); // Scale image
                    JLabel pic = new JLabel(imageIcon);
                    pic.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding //padding adds space within label of image to be away from text
                    panel.add(pic);
//                    System.out.println(imagePath);
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
