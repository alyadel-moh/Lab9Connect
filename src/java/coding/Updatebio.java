package coding;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Updatebio extends JFrame{
    private JTextField enterNewBioTextField;
    private JTextField textField2;
    private JPanel panel;
    private JButton updateBioButton;
    private UserService userService;

    Updatebio(User user){
        setContentPane(panel);
        updateBioButton.setFocusable(false);
        enterNewBioTextField.setBorder(new LineBorder(Color.BLACK));
        setBounds(100,100,600,200);
        setLocationRelativeTo(null);
        setVisible(true);
        updateBioButton.addActionListener(e -> {
            user.setBio(textField2.getText().trim());
            Database database = Database.getInstance();
            database.saveUsers();
            JOptionPane.showMessageDialog(null,"Bio updated successfully!");
            setVisible(false);
        });
    }
    public static void main
            (String[] args) {
        new Updatebio(null);

    }
}
