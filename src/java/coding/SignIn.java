package coding;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class SignIn extends JFrame{
    private JTextField passwordTextField;
    private JTextField emailTextField;
    private JTextField textField3;
    private JButton signInButton;
    private JButton backButton;
    private JPanel panel;
    private JPasswordField passwordField1;
    private UserService userService;

    SignIn(UserService userService)
    {
        setTitle("sign in menu ");
        this.userService = userService;
        emailTextField.setBorder(new LineBorder(Color.BLACK));
        passwordTextField.setBorder(new LineBorder(Color.BLACK));

        setContentPane(panel);

        backButton.setFocusable(false);
        signInButton.setFocusable(false);

        setBounds(100,100,500,300);
        setLocationRelativeTo(null);
        setVisible(true);

        signInButton.addActionListener(e -> {
            String email = textField3.getText().trim();
            String password = String.valueOf(passwordField1.getPassword());
           User user =  userService.login(email,password);
           if(user != null) {
               setVisible(false);

               int count = 1;
/////////////////////////////////////////////
               System.out.println("Suggested: ");
               for (User suggested : user.getSuggestions()){
                   System.out.print(count++ + "_");
                   System.out.println(suggested);

                   user.getManager().getFriends().add(suggested);
                   //user.getManager().sendRequest(suggested);
               }

               System.out.println("Requests: ");
               for (FriendRequest suggested : user.getRequests()){
                   System.out.print(count++ + "_");
                   System.out.println(suggested);
               }

               if (user.getManager().getFriends().isEmpty()){
                   System.out.println("No friends yet");
               }else{
                   count = 1;
                   for (User suggested : user.getManager().getFriends()){
                       System.out.print(count++ + "_");
                       System.out.println(suggested);
                   }
               }

           /////////////////////////////////////////////////////
               new Homepage(userService,user);
//               new Feedpage(user,userService);
               user.getHandler().loadPosts();
               user.getHandler().loadStories();
               }
           else
               JOptionPane.showMessageDialog(null, "User not found");
        });

        backButton.addActionListener(e -> {
           setVisible(false);
           new Window1(userService);
        });
    }
}
