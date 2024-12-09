package coding;

import javax.swing.*;

public class PrimaryAdminManagment extends JFrame {
    private JButton viewPostsButton;
    private JButton viewMembersButton;
    private JPanel panel;

    PrimaryAdminManagment(User user,Group group)
    {
        setTitle("Primary Admin Role");
        setContentPane(panel);
        setBounds(100,100,400,350);
        setResizable(false);
        setLocationRelativeTo(null);
        viewMembersButton.setFocusable(false);
        viewPostsButton.setFocusable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        viewMembersButton.addActionListener(e -> {
            setVisible(false);
            new View_Members_List(group);
        });
    }
}
