package coding;

import javax.swing.*;

public class Primaryadmin extends JFrame{
    private JButton promoteOrDemoteOtherButton;
    private JButton deleteGroupButton;
    private JButton viewPostsButton;
    private JButton viewMembersButton;
    private JPanel panel;
    private User user;
    private Group group;
    Primaryadmin(User user,Group group)
    {
            setTitle("primary Admin");
            this.group = group;
            this.user = user;
            setContentPane(panel);

            promoteOrDemoteOtherButton.setFocusable(false);
           deleteGroupButton.setFocusable(false);
            viewMembersButton.setFocusable(false);
            viewPostsButton.setFocusable(false);

            setBounds(100,100,350,550);
            setLocationRelativeTo(null);
            setVisible(true);
            promoteOrDemoteOtherButton.addActionListener(e -> {
            });
          deleteGroupButton.addActionListener(e -> {
            });
         viewMembersButton.addActionListener(e -> {
            });
            viewPostsButton.addActionListener(e -> {
            });
        }
    public static void main(String[] args) {
        new Primaryadmin(null,null);
    }
    }