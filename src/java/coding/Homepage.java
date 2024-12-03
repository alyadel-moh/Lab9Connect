package coding;

import javax.swing.*;
import java.awt.*;

public class Homepage extends JFrame {
    private JPanel mainPanel;
    private JPanel postsPanel;
    private JPanel storiesPanel;
    private JPanel friendsPanel;
    private JPanel friendSuggestionsPanel;
    private JTextArea contentCreationArea;
    private JButton postButton,refreshButton;

    public Homepage() {
        setTitle("Homepage");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

    }

    private void createHeader(){
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel logo = new JLabel("Connect Hub");
        logo.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(logo);

        JTextField searchField = new JTextField(20);
        searchField.setText("Search");
        headerPanel.add(searchField);

        JButton homeButton = new JButton("Home");
        JButton notificationButton = new JButton("Notifications");
        JButton profileButton = new JButton("Profile");
        headerPanel.add(homeButton);
        headerPanel.add(notificationButton);
        headerPanel.add(profileButton);

        headerPanel.setBackground(Color.LIGHT_GRAY);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        setVisible(true);
    }
    public static void main
            (String[] args) {
        new Homepage();}
}
