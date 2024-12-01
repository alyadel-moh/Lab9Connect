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
               new Feedpage(user,userService);           }
           else
               JOptionPane.showMessageDialog(null, "User not found");
        });
        backButton.addActionListener(e -> {
           setVisible(false);
           new Window1(userService);
        });
    }
}
