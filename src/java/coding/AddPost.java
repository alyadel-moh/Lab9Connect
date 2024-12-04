package coding;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.time.LocalDateTime;

public class AddPost extends JFrame{
    private JTextField textOfContent;
    private JPanel panel;
    private JButton addButton;
    private JButton backButton;
    private JButton chooseAnImageButton;
    private JTextField writeAPostTextField;
    private JTextField addAnImageTextField;
    private User user;
    private String imagepath= "";

    AddPost(User user){
        addButton.setFocusable(false);
        backButton.setFocusable(false);
        chooseAnImageButton.setFocusable(false);
        writeAPostTextField.setBorder(new LineBorder(Color.black));
        addAnImageTextField.setBorder(new LineBorder(Color.BLACK));
        setTitle("Add Post");
        setVisible(true);
        setSize(new Dimension(500,350));
        setContentPane(panel);
        setLocationRelativeTo(null);
        this.user=user;
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textOfContent.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please Enter Content!", "Message", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    LocalDateTime currentTime= LocalDateTime.now();
                    String content = textOfContent.getText() + "@" + imagepath;
                    String postId="Post "+(user.getHandler().getPosts().size()+1);//Creates id for the content
                    Posts post=new Posts(postId,user.getUserId(),content,currentTime);
                    user.getHandler().addPost(post);
                    JOptionPane.showMessageDialog(null,"Post Added Successfully");
                    setVisible(false);
                    new ContentCreation(user);
                }
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new ContentCreation(user);
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
    public static void main
            (String[] args) {
        new AddPost(null);}
}
