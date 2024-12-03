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
    }
}
