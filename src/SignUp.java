import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SignUp extends JFrame{
    private JTextField userNameTextField;
    private JTextField dateOfBirthTextField;
    private JTextField passwordTextField;
    private JTextField emailTextField;
    private JTextField textField5;
    private JTextField textField6;
    private JPasswordField passwordField1;
    private JTextField textField7;
    private JButton SignUpButton;
    private JButton backButton;
    private JPanel panel;
    private UserService userService;
    SignUp(UserService userService)
    {
        setTitle("sign up menu");
        userNameTextField.setBorder(new LineBorder(Color.BLACK));
        emailTextField.setBorder(new LineBorder(Color.BLACK));
        passwordTextField.setBorder(new LineBorder(Color.BLACK));
        dateOfBirthTextField.setBorder(new LineBorder(Color.BLACK));

        setContentPane(panel);
        SignUpButton.setFocusable(false);
        backButton.setFocusable(false);
        setBounds(100,100,500,500);
        setLocationRelativeTo(null);
        setVisible(true);

        SignUpButton.addActionListener(e -> {
            String username = textField5.getText().trim();
            String email = textField6.getText().trim();
            String password = String.valueOf(passwordField1.getPassword());
            String dob = textField7.getText().trim();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try{
                Date date = sdf.parse(dob);
                userService.signUp(email,username,password,date);
            }catch (ParseException ex){JOptionPane.showMessageDialog(null,"Incorrect date format");}

        });
        backButton.addActionListener(e -> {
            setVisible(false);
            new Window1(userService);
        });
    }
}

