package coding;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class AddPost extends JFrame{
    private JTextField contentIdPlaceholder;
    private JTextField authorIdPlaceholder;
    private JTextField textOfContent;
    private JTextField ImgOfContent;
    private JPanel panel;
    private JButton addButton;
    private JButton backButton;
    private User user;
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
                    String content=textOfContent.getText()+"-"+ImgOfContent.getText();
                    String contentId="Post "+user.getContentList().size()+1;//Creates id for the content
                    Posts post=new Posts(contentId,user.getUserId(),content,currentTime);
                    user.getHandler().addContent(post);
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
    }
}
