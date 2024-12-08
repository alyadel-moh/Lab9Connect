package coding;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.File;

public class CreateGroupGui extends JFrame {
    private JButton addOtherAdminsButton;
    private JPanel panel;
    private JTextField setGroupNameTextField;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField setGroupDescriptionTextField;
    private JButton addProfileButton;
    private JButton addFriendsButton;
    private JButton createGroupButton;
    private String profilepath;

    CreateGroupGui(User user)
    {
        setTitle("Create Group");
        setContentPane(panel);
        setBounds(100,100,570,500);
        setResizable(false);
        setLocationRelativeTo(null);
        setGroupNameTextField.setBorder(new LineBorder(Color.black));
        setGroupDescriptionTextField.setBorder(new LineBorder(Color.black));
        addFriendsButton.setFocusable(false);
        addOtherAdminsButton.setFocusable(false);
        addProfileButton.setFocusable(false);
       addProfileButton.addActionListener(e -> {
           JFileChooser fileChooser = new JFileChooser();//Create the JfileChooser to show the save dialog
           fileChooser.setDialogTitle("Choose an Image");
           int userChoice = fileChooser.showSaveDialog(null);//shows the save dialog//null is to be centered to the screen//returns 0 if the user clicked save//returns 1 then the user canceled//-1 error occured
           if (userChoice == -1) {
               JOptionPane.showMessageDialog(null, "An error has occurred");
           } else if (userChoice == 1) {
               JOptionPane.showMessageDialog(null, "The user Cancelled");
           } else {
               File selectedFile = fileChooser.getSelectedFile();
               if(selectedFile.exists()){

                 profilepath = selectedFile.getAbsolutePath();
                   JOptionPane.showMessageDialog(null,"Image Chosen successfully");
               }
               else{
                   JOptionPane.showMessageDialog(null, "Please Choose an Image!", "Message", JOptionPane.ERROR_MESSAGE);
               }
           }
            setVisible(false);
        });
       addOtherAdminsButton.addActionListener(e -> {
            setVisible(false);
        });
       addFriendsButton.addActionListener(e -> {
            setVisible(false);
        });
        createGroupButton.addActionListener(e -> {
            Group group = new Group(user,null,profilepath,textField2.getText(),textField1.getText(),null);
            setVisible(false);
        });
        setVisible(true);
    }
    public static void main(String[] args) {
        new CreateGroupGui(null);
    }
    }

