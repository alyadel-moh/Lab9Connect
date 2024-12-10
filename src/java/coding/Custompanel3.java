package coding;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public class Custompanel3 extends JPanel {
    private Group group;
    private int buttonCount;
    protected JButton button1;
    protected JButton button2;
    public Custompanel3(Group group, String text) {
        this(group, text, null); // Call the other constructor with null for text2
    }

    public Custompanel3(Group group, String text, String text2) {
        super();
        this.group = group;
        this.buttonCount = (text2 == null) ? 1 : 2;
        this.button1 = createbutton(text);

        if (text2 != null) {
            this.button2 = createbutton(text2);
        }

        setupLayout();
    }

    private void setupLayout() {
        setLayout(new GridLayout(1,2));
        JPanel profilePanel = new JPanel(new GridLayout(1,3));
        JPanel buttonPanel = new JPanel();
        for (int i = 0; i < group.getPosts().size(); i++) {
            String content = group.getPosts().get(i).getContent();
            String[] contentDelim = content.split("@");
           String text = contentDelim[0];
            JLabel label = new JLabel(text);
            System.out.println(text);
            profilePanel.add(label);
            try {
                String imagePath = contentDelim[1];
                if (!imagePath.isEmpty()) {
                    ImageIcon imageIcon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH)); // Scale image
                    JLabel pic = new JLabel(imageIcon);
                    pic.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
                    profilePanel.add(pic);
                    System.out.println(imagePath);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("No image");
            }

        }
        add(profilePanel);

        buttonPanel.setLayout(new GridLayout(1,0));

        if (buttonCount >= 1) {
            buttonPanel.add(button1);
        }
        if (buttonCount == 2) {
            buttonPanel.add(button2);
        }

        add(buttonPanel);

        setMaximumSize(new Dimension(600,20));
        //setMinimumSize(new Dimension(100,30));
        setPreferredSize(new Dimension(100,30));
    }

    private JButton createbutton(String text)
    {
        JButton button = new JButton();
        button.setBackground(Color.black);
        button.setForeground(Color.white);
        button.setFocusable(false);
        button.setText(text);
        return button;
    }

    public static void main(String[] args) {
        // Create a sample User object
        User testUser = new User.UserBuilder()
                .setUserName("JohnDoe123")
                .build();

        testUser.setProfile();
        //System.out.println(testUser.getProfile());

        // Create a JFrame for testing
        JFrame frame = new JFrame("Custom Panel Test");
        frame.setLayout(new GridLayout(1,1));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        // Test with one button
        CustomPanel panel1 = new CustomPanel(testUser, "Accept");
        frame.add(panel1);

        //Uncomment the following lines to test with two buttons:
        frame.getContentPane().removeAll(); // Clear previous content
        CustomPanel panel2 = new CustomPanel(testUser, "Accept", "Decline");
        frame.add(panel2);

        frame.setVisible(true);
    }
}