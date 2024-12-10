package coding;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileManagement extends JFrame{
    private JButton updateProfilePhotoButton;
    private JButton updatePasswordButton;
    private JButton updateBioButton;
    private JButton updateCoverPhotoButton, backButton;
    private JPanel panel;
    private JButton ViewProfile;
    private User user;
    private UserService userService;

    public ProfileManagement(User user , UserService userService)
    {
        setTitle("profile management menu");
        this.userService = userService;
        this.user = user;

        setContentPane(panel);

        backButton = new JButton();

        updateProfilePhotoButton.setFocusable(false);
        updatePasswordButton.setFocusable(false);
        updateBioButton.setFocusable(false);
        updateCoverPhotoButton.setFocusable(false);
        backButton.setFocusable(false);

        setBounds(100,100,400,600);
        setLocationRelativeTo(null);
        setVisible(true);
        updateProfilePhotoButton.addActionListener(e -> {
            setVisible(false);
            user.setProfile();
            System.out.println(user.getProfilepath());
        });
        updateCoverPhotoButton.addActionListener(e -> {
            setVisible(false);
            user.setCover();
            System.out.println(user.getCoverpath());
        });
        updateBioButton.addActionListener(e -> {
            setVisible(false);
           new Updatebio(user);
        });
        updatePasswordButton.addActionListener(e -> {
            setVisible(false);
            new Updatepass(user,userService);
        });
        backButton.addActionListener(e -> {
            setVisible(false);
            new Homepage(userService, user);
        });
        ViewProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new Profile(user);
            }
        });
    }
}


