package coding;

import javax.swing.*;
import java.awt.*;

public class Feedpage extends JFrame{
    private JButton profileManagmentButton;
    private JButton contentCreationButton;
    private JButton friendManagmentButton;
    private JPanel panel;
    private JPanel activePanel;
    public JPanel contentPanel;
    private UserService userService;
    private User user;

    Feedpage(User user , UserService userService)
    {
        profileManagmentButton.setFocusable(false);
        contentCreationButton.setFocusable(false);
        friendManagmentButton.setFocusable(false);

        activePanel = new JPanel();
        add(new JScrollPane(activePanel), BorderLayout.NORTH);

        this.user = user;
        this.userService = userService;

        setBounds(100,100,1400,900);
        setLocationRelativeTo(null);
        setResizable(false);
        setContentPane(panel);
        setVisible(true);

        profileManagmentButton.addActionListener(e -> {
            new ProfileManagement(user,userService);
        });

        contentCreationButton.addActionListener(e -> {
        });

        friendManagmentButton.addActionListener(e -> {
            new FriendManagement(user,userService);
        });
    }

    public void setActivePanel(JPanel newPanel) {
        remove(activePanel);
        activePanel = newPanel;
        add(new JScrollPane(activePanel), BorderLayout.NORTH);
        revalidate();
        repaint();
    }

    public static void main
            (String[] args) {
        Database database = new Database();
        UserService userService = new UserService(database);
        new Feedpage(null,null);}

}
