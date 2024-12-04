package coding;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Storiesview extends JFrame {
    JPanel panel = new JPanel();
    User user;

    Storiesview(User user,int i) {
        ArrayList<Stories> stories = user.getHandler().getStories();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        this.user = user;
        setBounds(100, 100, 300, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        if(stories.isEmpty())
        {
            JOptionPane.showMessageDialog(null,"no stories to view !");
            return;
        }
        if(i == stories.size())
        {
            JOptionPane.showMessageDialog(null,"no more stories to view !");
            setVisible(false);
            return;
        }
        viewStory(stories.get(i));
        // Add MouseListener to the JPanel
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON3 || e.getButton() == MouseEvent.BUTTON2) {
                    setVisible(false);
                        new Storiesview(user,i+1);
                }
            }
        });
        add(panel);
        setVisible(true);
    }
    public void viewStory(Stories story)
    {
            String content = story.getContent();
            String[] contentDelim = content.split("@");
            String text = contentDelim[0];

            JLabel textLabel = new JLabel(text);
            textLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Add padding
            panel.add(textLabel);

            try {
                String imagePath = contentDelim[1];
                if (!imagePath.isEmpty()) {
                    ImageIcon imageIcon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH)); // Scale image
                    JLabel imageLabel = new JLabel(imageIcon);
                    imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
                    panel.add(imageLabel);
                    System.out.println("Image path: " + imagePath);
                }
            }
            catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("No image found for this post.");
            }
        }
    }



