package coding;

import javax.swing.*;
import java.awt.*;

public class Suggestions_Management extends JFrame {
    private User user;
    private UserService service;
    private JPanel panel1;

    Suggestions_Management(User user, UserService service){
        this.user = user;
        this.service = service;

        panel1 = new JPanel();
        panel1.setLayout(new GridLayout(user.getSuggestions().size(),1));

        for (User suggested : user.getSuggestions()){
            //Loop through each Request
            CustomPanel customPanel = new CustomPanel(suggested, "Send Request", "Ignore");

            // Send Request
            customPanel.button1.addActionListener(e -> {
                user.getManager().sendRequest(suggested);
                user.getSuggestions().remove(suggested);
                repaint();
            });

            // Ignore Request
            customPanel.button2.addActionListener(e -> {
                user.getSuggestions().remove(suggested);
                repaint();
            });

            panel1.add(customPanel);
        }


        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setBounds(100, 100, 300, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }
        }

