package coding;

import javax.swing.*;
import java.awt.*;

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
        setLayout(new GridLayout(1,4));

        JLabel label1 = new JLabel(user.getProfilepath());
        JLabel label2 = new JLabel(user.getUserName());

        add(label1);
        add(label2);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        if (buttonCount >= 1) {
            buttonPanel.add(button1);
        }
        if (buttonCount == 2) {
            buttonPanel.add(new JLabel());
            buttonPanel.add(button2);
        }

        add(new JLabel());
        add(buttonPanel);
    }

    public JButton createbutton(String text)
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
