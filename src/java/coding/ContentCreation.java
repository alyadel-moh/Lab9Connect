package coding;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContentCreation extends JFrame{
    private JPanel Container1;
    private JButton addPost;
    private JButton addStory;
    private JButton savePostButton;
    private JButton viewButton;
    private JButton saveStoryButton;
    private User user;
    private ContentHandler handler;

    public ContentCreation(User user){
        setTitle("Content Creation Window ");
        setVisible(true);
        setSize(new Dimension(200,200));
        setContentPane(Container1);
        setLocationRelativeTo(null);
        this.user=user;

        addPost.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddPost(user);
                setVisible(false);
            }
        });
    }

}
