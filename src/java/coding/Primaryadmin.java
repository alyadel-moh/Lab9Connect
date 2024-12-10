package coding;

import javax.swing.*;

public class Primaryadmin extends JFrame{
    private JButton promoteOrDemoteOtherButton;
    private JButton viewCreatedGroupsButton;
    private JButton viewPostsButton;
    private JButton viewMembersButton;
    private JPanel panel;
    private User primaryadmin;
    Primaryadmin(User primaryadmin)
    {
            setTitle("primary Admin");
            this.primaryadmin = primaryadmin;
            setContentPane(panel);
            promoteOrDemoteOtherButton.setFocusable(false);
           viewCreatedGroupsButton.setFocusable(false);
            setBounds(100,100,400,350);
            setLocationRelativeTo(null);
            setVisible(true);
            promoteOrDemoteOtherButton.addActionListener(e -> {
            });
          viewCreatedGroupsButton.addActionListener(e -> {
              new View_Groups_List(primaryadmin,primaryadmin.getGroupManager().getGroups());
              setVisible(false);
            });
        }
    }