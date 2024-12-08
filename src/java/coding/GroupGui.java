package coding;

import javax.swing.*;
import java.awt.*;

public class GroupGui extends JFrame{
    private JButton createGroupButton;
    private JButton manageYourGroupButton;
    private JPanel panel;
    GroupGui(User user)
    {
        setTitle("Group mangment");
        setContentPane(panel);
        setBounds(100,100,400,350);
        setResizable(false);
        setLocationRelativeTo(null);
        createGroupButton.setFocusable(false);
       manageYourGroupButton.setFocusable(false);
        createGroupButton.setFont(new Font("Arial", Font.BOLD,15));
      manageYourGroupButton.setFont(new Font("Arial", Font.BOLD,15));
       manageYourGroupButton.addActionListener(e -> {
           new Managegroup(user);
            setVisible(false);
        });
        createGroupButton.addActionListener(e -> {
            new CreateGroupGui(user);
            setVisible(false);
        });
        setVisible(true);
    }
    public static void main(String[] args) {
        new GroupGui(null);
    }
}




