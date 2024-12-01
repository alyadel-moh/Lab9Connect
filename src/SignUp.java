import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import com.toedter.calendar.JDateChooser;

public class SignUp extends JFrame{
    private JTextField userNameTextField;
    private JTextField dateOfBirthTextField;
    private JTextField passwordTextField;
    private JTextField emailTextField;
    private JTextField textField5;
    private JTextField textField6;
    private JPasswordField passwordField1;
    private JPanel textField7;
    private JButton SignUpButton;
    private JButton backButton;
    private JPanel panel;
    private UserService userService;
    SignUp(UserService userService)
    {
        Calendar old = Calendar.getInstance();
        JDateChooser dateChooser = new JDateChooser(old.getTime());

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

        ///Calendar
        dateChooser.setDateFormatString("dd/MM/yyyy");
        //textField7.setPreferredSize(new Dimension(200, 60));
        textField7.add(dateChooser);

        SignUpButton.addActionListener(e -> {
            String username = textField5.getText().trim();
            String email = textField6.getText().trim();
            String password = String.valueOf(passwordField1.getPassword());

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || dateChooser.getDate() == null) {
                JOptionPane.showMessageDialog(null, "All fields are required!");
                return;
            }

            LocalDate selectedDate = dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            userService.signUp(email, username, password, selectedDate);

        });
        backButton.addActionListener(e -> {
            setVisible(false);
            new Window1(userService);
        });
    }
}

