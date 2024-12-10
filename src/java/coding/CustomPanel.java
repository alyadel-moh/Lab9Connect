package coding;

import coding.testtt.CircleButton;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public class CustomPanel extends JPanel {
    private User user;
    private int buttonCount;
    protected JButton button1;
    protected JButton button2;

    public CustomPanel(User user, String text) {
        this(user, text, null); // Call the other constructor with null for text2
    }

    public CustomPanel(User user, String text, String text2) {
        super();
        this.user = user;
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

        //CircleButton profileView = new CircleButton(n);
        //JLabel profileLabel = createCircularLabel(new ImageIcon(user.getProfilepath()));

        JButton profileView = createImageButton();
        JLabel label2 = new JLabel(user.getUserName());

        profilePanel.add(profileView);
        profilePanel.add(label2);
        profilePanel.add(new JLabel());

        // label2.setMaximumSize(new Dimension(20,30));

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

    public void add(String text){
        JLabel label = new JLabel(text);
        add(label, 2);
    }

    private JLabel createCircularLabel(ImageIcon imageIcon) {
        int size = 100; // Desired size for profile picture
        Image scaledImage = imageIcon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
        BufferedImage circularImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = circularImage.createGraphics();
        g2.setClip(new Ellipse2D.Double(0, 0, size, size));
        g2.drawImage(scaledImage, 0, 0, null);
        g2.dispose();

        return new JLabel(new ImageIcon(circularImage));
    }

    private JButton createImageButton(){
        JButton button = createbutton(null);
        button.setBackground(Color.white);
        button.setBorder(BorderFactory.createLineBorder(Color.black));
        button.setFocusPainted(false);
        Image image = new ImageIcon(user.getProfilepath()).getImage();
        Image scaled = image.getScaledInstance(70,70,Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(scaled));
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
