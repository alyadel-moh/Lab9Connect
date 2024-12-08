package coding;

import javax.swing.*;

public class Otheradmin extends JFrame{
    private JButton viewMembershipRequestsButton;
    private JButton viewPostsButton;
    private JButton viewMembersButton;
    private JPanel panel;
    private User user;
    private Group group;
    Otheradmin(User user,Group group)
    {
        setTitle("Other Admin");
        this.group = group;
        this.user = user;
        setContentPane(panel);

        viewMembersButton.setFocusable(false);
        viewMembershipRequestsButton.setFocusable(false);
       viewPostsButton.setFocusable(false);

        setBounds(100,100,350,400);
        setLocationRelativeTo(null);
        setVisible(true);
        viewMembershipRequestsButton.addActionListener(e -> {
        });
       viewMembersButton.addActionListener(e -> {

        });
        viewPostsButton.addActionListener(e -> {
        });
    }
    public static void main(String[] args) {
        new Otheradmin(null,null);
    }
}
