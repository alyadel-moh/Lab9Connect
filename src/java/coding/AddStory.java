package coding;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDateTime;

public class AddStory extends JFrame {
    private User user;
    private String imagepath = "";
    private JPanel panel;
    private JButton chooseAnImageButton;
    private JTextField textOfContent;
    private JButton addButton;
    private JButton backButton;
    private JTextField chooseAnImageTextField;
    private JTextField enterPostTextTextField;

    AddStory(User user) {
         addButton.setFocusable(false);
         backButton.setFocusable(false);
         chooseAnImageButton.setFocusable(false);
         chooseAnImageTextField.setBorder(new LineBorder(Color.black));
         enterPostTextTextField.setBorder(new LineBorder(Color.black));
        setTitle("Add Story");
        setVisible(true);
        setSize(new Dimension(550, 350));
        setContentPane(panel);
        setLocationRelativeTo(null);
        this.user = user;
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textOfContent.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please Enter Content!", "Message", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    LocalDateTime currentTime= LocalDateTime.now();
                    String content=textOfContent.getText()+"@"+imagepath;
                    String storyId="Story "+user.getHandler().getStories().size()+1;//Creates id for the content
                    Stories story=new Stories(storyId,user.getUserId(),content,currentTime);
                    user.getHandler().addStory(story);
                    JOptionPane.showMessageDialog(null,"Story added Successfully");
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
                    if(selectedFile.exists()) {
                        imagepath = selectedFile.getAbsolutePath();
                        System.out.println(imagepath);
                        JOptionPane.showMessageDialog(null, "Image Chosen successfully");
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
        new AddStory(null);}

}
