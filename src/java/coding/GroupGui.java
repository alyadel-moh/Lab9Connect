package coding;

import javax.swing.*;
import java.awt.*;

public class GroupGui extends JFrame{
    private JButton createGroupButton;
    private JButton normalUserButton;
    private JPanel panel;
    private JButton primaryAdminButton;
    private JButton otherAdminButton;

    GroupGui(User user)
    {
        setTitle("Group managment");
        setContentPane(panel);
        setBounds(100,100,400,600);
        setResizable(false);
        setLocationRelativeTo(null);
        createGroupButton.setFocusable(false);
       normalUserButton.setFocusable(false);
       primaryAdminButton.setFocusable(false);
       otherAdminButton.setFocusable(false);
        createGroupButton.setFont(new Font("Arial", Font.BOLD,15));
      normalUserButton.setFont(new Font("Arial", Font.BOLD,15));
        otherAdminButton.setFont(new Font("Arial", Font.BOLD,15));
        primaryAdminButton.setFont(new Font("Arial", Font.BOLD,15));
        primaryAdminButton.addActionListener(e -> {
             new Primaryadmin(user);
            setVisible(false);
        });
      otherAdminButton.addActionListener(e -> {
           new View_Groups_List2(user,user.getGroupManager().getGroups());
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




