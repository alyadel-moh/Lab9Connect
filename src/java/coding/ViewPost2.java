package coding;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class ViewPost2 extends JFrame {
    private Group group;
    private JPanel panel;
    JLabel pic;
    String imagePath;
    String text;
    public ViewPost2(Group group) {
        this.group = group;
        setTitle("View Posts");
        setSize(new Dimension(1000, 1000));
        setLocationRelativeTo(null);


        // Initialize the panel and set a layout
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Use BoxLayout for stacking

        ArrayList<Posts> posts = group.getPosts();
        for (Posts post : posts) {
            Custompanel3 custompanel3 = new Custompanel3(group,"edit","remove");
            panel.add(custompanel3);
            String content = post.getContent();
            String[] contentDelim = content.split("@");
            String text = contentDelim[0];
            JLabel label = new JLabel(text);
            System.out.println(text);
            panel.add(label);
            try {
                 imagePath = contentDelim[1];
                if (!imagePath.isEmpty()) {
                    ImageIcon imageIcon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH)); // Scale image
                    pic = new JLabel(imageIcon);
                    pic.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
                    panel.add(pic);
                    System.out.println(imagePath);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("No image");
            }
            custompanel3.button1.addActionListener(_ -> {
               int Choice = JOptionPane.showConfirmDialog(null,"Do you want to change text ?","Change Text",JOptionPane.YES_OPTION,JOptionPane.QUESTION_MESSAGE);
               if(Choice == JOptionPane.YES_OPTION) {
                   post.setContent(JOptionPane.showInputDialog(null, "Enter new Post text ") + "@" + imagePath);
                   refreshUI();
               }
               else
                   System.out.println("User closed the dialog or clicked cancel.");
                Choice = JOptionPane.showConfirmDialog(null,"Do you want to change photo ?","Change photo",JOptionPane.YES_OPTION,JOptionPane.QUESTION_MESSAGE);
                if(Choice == JOptionPane.YES_OPTION)
                {
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
                            post.setContent(text+"@"+selectedFile.getAbsolutePath());
                            JOptionPane.showMessageDialog(null,"Image Chosen successfully");
                            refreshUI();
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Please Choose an Image!", "Message", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                else
                    System.out.println("User closed the dialog or clicked cancel.");

                refreshUI();
            });
            custompanel3.button2.addActionListener(_ -> {
                group.getPosts().remove(post);
                panel.remove(custompanel3);
                panel.remove(pic);
                panel.remove(label);
                refreshUI();
            });
            refreshUI();
        }

        // Add panel to a scroll pane for scrolling
        JScrollPane scrollPane = new JScrollPane(panel);
        setContentPane(scrollPane);


        setVisible(true);

    }
    private void refreshUI() {
        panel.revalidate(); // Recalculate layout
        panel.repaint(); // Redraw components
    }
}