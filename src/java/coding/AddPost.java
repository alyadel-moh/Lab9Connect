package coding;

import javax.swing.*;
<<<<<<< HEAD
import javax.swing.border.LineBorder;
=======
>>>>>>> a006e0e22f5af3a61c1f30640212e0ad10a9df9f
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.time.LocalDateTime;

public class AddPost extends JFrame{
    private JTextField contentIdPlaceholder;
    private JTextField authorIdPlaceholder;
    private JTextField textOfContent;
    private JPanel panel;
    private JButton addButton;
    private JButton backButton;
    private JButton chooseAnImageButton;
<<<<<<< HEAD
    private JTextField writePostTextTextField;
    private JTextField addAnImageJpgTextField;
=======
>>>>>>> a006e0e22f5af3a61c1f30640212e0ad10a9df9f
    private User user;
    private String imageUrl = "";

    AddPost(User user){
<<<<<<< HEAD
        addButton.setFocusable(false);
        backButton.setFocusable(false);
        chooseAnImageButton.setFocusable(false);
        writePostTextTextField.setBorder(new LineBorder(Color.black));
        addAnImageJpgTextField.setBorder(new LineBorder(Color.black));
        setTitle("Content Creation Window ");
        setVisible(true);
        setSize(new Dimension(500,400));
=======
        setTitle("Content Creation Window ");
        setVisible(true);
        setSize(new Dimension(500,500));
>>>>>>> a006e0e22f5af3a61c1f30640212e0ad10a9df9f
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
                    String content=textOfContent.getText()+"-"+imageUrl;
                    String postId="Post "+user.getHandler().getPosts().size()+1;//Creates id for the content

                    Posts post = (Posts) ContentFactory.createContent("post", postId,user.getUserId(),content,currentTime);
                    user.getHandler().addPost(post);

                    setVisible(false);
                    new ContentCreation(user);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        chooseAnImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();//Create the JfileChooser to show the save dialog
                fileChooser.setDialogTitle("Choose an Image");
                int userChoice = fileChooser.showSaveDialog(null);//shows the save dialog//null is to be centered to the screen//returns 0 if the user clicked save//returns 1 then the user canceled//-1 error occured
                if (userChoice == -1) {
<<<<<<< HEAD
                    JOptionPane.showMessageDialog(null, "An error has occurred !");
                } else if (userChoice == 1) {
                    JOptionPane.showMessageDialog(null, "operation Cancelled ! ");
=======
                    JOptionPane.showMessageDialog(null, "An error has occurred");
                } else if (userChoice == 1) {
                    JOptionPane.showMessageDialog(null, "The user Cancelled");
>>>>>>> a006e0e22f5af3a61c1f30640212e0ad10a9df9f
                } else {
                    File selectedFile = fileChooser.getSelectedFile();
                    if(selectedFile.getName().endsWith(".jpg") || selectedFile.getName().endsWith(".png")){
                        // Convert file to URL
                        try {
                            imageUrl = selectedFile.toURI().toURL().toString();
<<<<<<< HEAD
                            JOptionPane.showMessageDialog(null,"Image Chosen successfully !");
=======
                            JOptionPane.showMessageDialog(null,"Image Chosen successfully");
>>>>>>> a006e0e22f5af3a61c1f30640212e0ad10a9df9f
                        } catch (MalformedURLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Please Choose an Image!", "Message", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }
<<<<<<< HEAD
    public static void main
            (String[] args) {
        new AddPost(null);}
=======
>>>>>>> a006e0e22f5af3a61c1f30640212e0ad10a9df9f
}
