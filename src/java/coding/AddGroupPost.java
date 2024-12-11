package coding;

import coding.ENUMS.CONTENT_TYPE;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDateTime;

public class AddGroupPost extends JFrame{
    private JTextField addAnImageTextField;
    private JTextField textOfContent;
    private JTextField writeAPostTextField;
    private JButton chooseAnImageButton;
    private JButton addButton;
    private JPanel panel;
    private Group group;
    private String imagepath= "";
    AddGroupPost(Group group,User user)
    {
        addButton.setFocusable(false);
        chooseAnImageButton.setFocusable(false);
        writeAPostTextField.setBorder(new LineBorder(Color.black));
        addAnImageTextField.setBorder(new LineBorder(Color.BLACK));
        setTitle("Add Post");
        setVisible(true);
        setSize(new Dimension(550,350));
        setContentPane(panel);
        setLocationRelativeTo(null);
        this.group = group;
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textOfContent.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please Enter Content!", "Message", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    LocalDateTime currentTime= LocalDateTime.now();
                    String content = textOfContent.getText() + "@" + imagepath;
                    String postId = "Post "+(group.getPosts().size()+1);//Creates id for the content
                    Posts post = (Posts) ContentFactory.createContent(CONTENT_TYPE.POST, postId, user.getUserId(), content, currentTime);
                    group.addPost(post);
                    JOptionPane.showMessageDialog(null,"Post Added Successfully");
                    setVisible(false);
                }
            }
        });
        chooseAnImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

                        imagepath = selectedFile.getAbsolutePath();
                        JOptionPane.showMessageDialog(null,"Image Chosen successfully");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Please Choose an Image!", "Message", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }
}
