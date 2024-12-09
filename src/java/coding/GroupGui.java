package coding;

import javax.swing.*;
import java.awt.*;

public class GroupGui extends JFrame{
    private JButton createGroupButton;
    private JButton otherAdminButton;
    private JPanel panel;
    private JButton primaryAdminButton;
    private JButton normalUserButton;

    GroupGui(User user)
    {
        setTitle("Group mangment");
        setContentPane(panel);
        setBounds(100,100,400,500);
        setResizable(false);
        setLocationRelativeTo(null);
        createGroupButton.setFocusable(false);
       otherAdminButton.setFocusable(false);
       primaryAdminButton.setFocusable(false);
       normalUserButton.setFocusable(false);
        createGroupButton.setFont(new Font("Arial", Font.BOLD,15));
      otherAdminButton.setFont(new Font("Arial", Font.BOLD,15));
        normalUserButton.setFont(new Font("Arial", Font.BOLD,15));
        primaryAdminButton.setFont(new Font("Arial", Font.BOLD,15));
        primaryAdminButton.addActionListener(e -> {
             new View_Groups_List(user,user.getGroupmanager().getGroups());
            setVisible(false);
        });
       otherAdminButton.addActionListener(e -> {
           new View_Groups_List2(user,user.getGroupmanager().getGroups());
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




