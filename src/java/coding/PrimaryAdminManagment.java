package coding;

import javax.swing.*;

public class PrimaryAdminManagment extends JFrame {
    private JButton viewPostsButton;
    private JButton viewMembersButton;
    private JPanel panel;
    private JButton addPostButton;
    private JButton groupProfileButton;
    private JButton promoteButton;
    private JButton demoteButton;

    PrimaryAdminManagment(User primaryadmin,Group group)
    {
        setTitle("Primary Admin Role");
        setContentPane(panel);
        setBounds(100,100,400,600);
        setResizable(false);
        setLocationRelativeTo(null);
        addPostButton.setFocusable(false);
        viewMembersButton.setFocusable(false);
        viewPostsButton.setFocusable(false);
        groupProfileButton.setFocusable(false);
        promoteButton.setFocusable(false);
        demoteButton.setFocusable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        promoteButton.addActionListener(e -> {
            setVisible(false);
            new PromoteUi(group);
        });
        demoteButton.addActionListener(e -> {
            setVisible(false);
            new DemoteUi(group);
        });

        viewMembersButton.addActionListener(e -> {
            setVisible(false);
            new View_Members_List(group);
        });
        addPostButton.addActionListener(e -> {
            setVisible(false);
            new AddGroupPost(group,primaryadmin);
        });
        viewPostsButton.addActionListener(e -> {
            setVisible(false);
            new ViewPost2(group);
        });
       groupProfileButton.addActionListener(e -> {
            setVisible(false);
            new Groupprofile(group);
        });

    }
}
