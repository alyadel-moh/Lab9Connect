package coding;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import coding.ENUMS.NOTIFICATIONS.CONTENT;

import static coding.ENUMS.CONTENT_TYPE.POST;
import static coding.ENUMS.CONTENT_TYPE.STORY;

public class ContentCreation extends JFrame{
    private JPanel Container1;
    private JButton addPost;
    private JButton addStory;
    private JButton savePostButton;
    private JButton viewPostButton;
    private JButton saveStoryButton;
    private JButton viewStoryButton;
    private User user;

    public ContentCreation(User user){
        addPost.setFocusable(false);
        addStory.setFocusable(false);
        viewStoryButton.setFocusable(false);
        viewPostButton.setFocusable(false);
        savePostButton.setFocusable(false);
        saveStoryButton.setFocusable(false);

        setTitle("Content Creation Window ");
        setVisible(true);
        setBounds(100,100,350,600);
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

        addStory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddStory(user);
                setVisible(false);
            }
        });
        savePostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(user.getHandler().getPosts().isEmpty()){
                    JOptionPane.showMessageDialog(null, "No Posts has been added for you to save", "Message", JOptionPane.ERROR_MESSAGE);
                }
                else{
                user.getHandler().savePosts();
                user.getNotifier().notifyObservers(user, POST, null);
                JOptionPane.showMessageDialog(null,"Posts has been added to the file Successfully");
            }}
        });
        saveStoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(user.getHandler().getStories().isEmpty()){
                    JOptionPane.showMessageDialog(null, "No Stories has been added for you to save", "Message", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    user.getHandler().saveStories();
                    user.getNotifier().notifyObservers(user, STORY, null);
                    JOptionPane.showMessageDialog(null,"Stories has been added to the file Successfully");
                }
            }
        });
        viewPostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewPost(user);
                setVisible(false);
            }
        });
        viewStoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewStories(user);
                setVisible(false);
            }
        });
    }
    public static void main
            (String[] args) {
        new ContentCreation(null);}
}
