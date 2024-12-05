package coding;

import javax.swing.*;
import java.awt.*;

public class Notifications extends JFrame{
        private User user;
        private UserService service;
        private JPanel panel1;

        Notifications(User user, UserService service){
            this.user = user;
            this.service = service;

            panel1 = new JPanel();
            panel1.setLayout(new GridLayout(user.getHandler().getNotifications().size(),1));

            for (CustomPanel customPanel : user.getHandler().getNotifications()){
                //Loop through each Request
                //CustomPanel customPanel = new CustomPanel(request.getSender(), "View", "Ignore");

                // accept Request
                customPanel.button1.addActionListener(e -> {
                    user.getHandler().getNotifications().remove(customPanel);
                    repaint();
                });

                // Decline Request
                customPanel.button2.addActionListener(e -> {
                    user.getHandler().getNotifications().remove(customPanel);
                    repaint();
                });

                panel1.add(customPanel);
            }


            setContentPane(panel1);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setTitle("Notifications");
            setResizable(false);
            setBounds(100, 100, 300, 400);
            setLocationRelativeTo(null);
            setVisible(true);
        }
}
