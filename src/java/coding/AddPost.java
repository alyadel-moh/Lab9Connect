package coding;

import javax.swing.*;
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
    private User user;
    private String imageUrl = "";

    AddPost(User user){
        setTitle("Content Creation Window ");
        setVisible(true);
        setSize(new Dimension(500,500));
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
                    Posts post=new Posts(postId,user.getUserId(),content,currentTime);
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
                    JOptionPane.showMessageDialog(null, "An error has occurred");
                } else if (userChoice == 1) {
                    JOptionPane.showMessageDialog(null, "The user Cancelled");
                } else {
                    File selectedFile = fileChooser.getSelectedFile();
                    if(selectedFile.getName().endsWith(".jpg")){
                        // Convert file to URL
                        try {
                            imageUrl = selectedFile.toURI().toURL().toString();
                            JOptionPane.showMessageDialog(null,"Image Chosen successfully");
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
}
