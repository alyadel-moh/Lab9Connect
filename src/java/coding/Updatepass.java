package coding;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Updatepass extends JFrame {
    private JTextField enterNewPasswordTextField;
    private JButton updatePasswordButton;
    private JPanel panel;
    private JPasswordField passwordField1;
    private User user;
    private UserService userService;
    Updatepass(User user,UserService userService){
        this.user = user;
        this.userService = userService;
        setTitle("update password menu");
        setContentPane(panel);
        updatePasswordButton.setFocusable(false);
        enterNewPasswordTextField.setBorder(new LineBorder(Color.BLACK));
        setBounds(100,100,600,200);
        setLocationRelativeTo(null);
        setVisible(true);
        updatePasswordButton.addActionListener(e -> {
            boolean exists = false;
            String password = String.valueOf(passwordField1.getPassword());
            String hashedNewPassword = Validator.hashPassword(password);
            for(User user1 : userService.getDatabase().getUsers())
                if(user1.getPassword().equals(hashedNewPassword)) {
                    JOptionPane.showMessageDialog(null, "password already exists ! ");
                    exists = true;
                    break;
                }
            if(exists)
            {
                setVisible(false);
                new ProfileManagement(user,userService);
                return;
            }
            user.setPassword(hashedNewPassword);
            JOptionPane.showMessageDialog(null, "password updated successfully !");
            setVisible(false);
        });
    }
}
