package coding;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ProfileManagement extends JFrame{
    private JButton updateProfilePhotoButton;
    private JButton updatePasswordButton;
    private JButton updateBioButton;
    private JButton updateCoverPhotoButton, backButton;
    private JPanel panel;
    private User user;
    private UserService userService;

    public ProfileManagement(User user , UserService userService)
    {
        setTitle("profile management menu");
        this.userService = userService;
        this.user = user;
        setContentPane(panel);
        updateProfilePhotoButton.setFocusable(false);
        updatePasswordButton.setFocusable(false);
        updateBioButton.setFocusable(false);
        updateCoverPhotoButton.setFocusable(false);
        backButton.setFocusable(false);
        setBounds(100,100,400,600);
        setLocationRelativeTo(null);
        setVisible(true);
        updateProfilePhotoButton.addActionListener(e -> {
        });
        updateCoverPhotoButton.addActionListener(e -> {
        });
        updateBioButton.addActionListener(e -> {
           new Updatebio(user);
        });
        updatePasswordButton.addActionListener(e -> {
            new Updatepass(user,userService);
        });
        backButton.addActionListener(e -> {
            setVisible(false);
            new Homepage(userService, user);
        });
    }
}


