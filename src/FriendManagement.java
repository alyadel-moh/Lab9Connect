import javax.swing.*;

public class FriendManagement extends JFrame{
    private JButton viewFriendRequestsButton;
    private JButton blockOrRemoveFriendsButton;
    private JButton viewFriendSuggestionsButton;
    private JButton viewFriendsListButton;
    private User user;
    private UserService userService;
    private JPanel panel;

    FriendManagement(User user,UserService userService)
        {
            viewFriendRequestsButton.setFocusable(false);
            blockOrRemoveFriendsButton.setFocusable(false);
            viewFriendsListButton.setFocusable(false);
            viewFriendSuggestionsButton.setFocusable(false);
            this.user = user;
            this.userService = userService;
            setContentPane(panel);
            setResizable(false);
            setBounds(100,100,300,400);
            setLocationRelativeTo(null);
            setVisible(true);
        }
}
