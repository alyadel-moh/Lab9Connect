package coding;

import javax.swing.*;
import java.awt.*;

public class Managegroup extends JFrame {
    private JButton primaryAdminButton;
    private JButton otherAdminButton;
    private JPanel panel;

    Managegroup(User user) {
        setTitle("Manage Group");
        setContentPane(panel);
        setBounds(100, 100, 400, 350);
        setResizable(false);
        setLocationRelativeTo(null);
        primaryAdminButton.setFocusable(false);
        otherAdminButton.setFocusable(false);
       primaryAdminButton.setFont(new Font("Arial", Font.BOLD, 15));
        otherAdminButton.setFont(new Font("Arial", Font.BOLD, 15));
        primaryAdminButton.addActionListener(e -> {
            new Primaryadmin(null,null);
            setVisible(false);
        });
       otherAdminButton.addActionListener(e -> {
           new Otheradmin(null,null);
           setVisible(false);
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        new Managegroup(null);
    }
}
