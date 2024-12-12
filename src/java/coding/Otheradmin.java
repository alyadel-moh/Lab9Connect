package coding;

import javax.swing.*;

public class Otheradmin extends JFrame{
    private JButton viewMembershipRequestsButton;
    private JButton viewPostsButton;
    private JButton viewMembersButton;
    private JPanel panel;
    private JButton addPostButton;
    private JButton viewGroupProfileButton;
    private User otheradmin;
    private Group group;
    Otheradmin(User otheradmin,Group group)
    {
        setTitle("Other Admin");
        this.otheradmin = otheradmin;
        setContentPane(panel);

        addPostButton.setFocusable(false);
        viewMembersButton.setFocusable(false);
        viewMembershipRequestsButton.setFocusable(false);
       viewPostsButton.setFocusable(false);
       viewGroupProfileButton.setFocusable(false);
        setBounds(100,100,400,500);
        setLocationRelativeTo(null);
        setVisible(true);
        viewMembershipRequestsButton.addActionListener(e -> {
            new View_Friends_List4(group);
        });
       viewMembersButton.addActionListener(e -> {
           setVisible(false);
           new View_Members_List(group);
        });
        viewPostsButton.addActionListener(e -> {
            setVisible(false);
            new ViewPost2(group);
        });
        addPostButton.addActionListener(e -> {
            new AddGroupPost(group,otheradmin);
            setVisible(false);
        });
        viewGroupProfileButton.addActionListener(e -> {
            new Groupprofile(group);
            setVisible(false);
        });

    }
}
