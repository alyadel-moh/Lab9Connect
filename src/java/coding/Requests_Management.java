package coding;

import javax.swing.*;
import java.awt.*;

public class Requests_Management extends JFrame{
    private User user;
    private UserService service;
    private JPanel panel1;

    Requests_Management(User user, UserService service){
        this.user = user;
        this.service = service;

        panel1 = new JPanel();
        panel1.setLayout(new GridLayout(user.getRequests().size(),1));

        for (FriendRequest request : user.getRequests()){
            //Loop through each Request
            CustomPanel customPanel = new CustomPanel(request.getSender(), "Accept", "Decline");

            // accept Request
            customPanel.button1.addActionListener(e -> {
                user.getManager().accept(request);
                user.getRequests().remove(request);
                panel1.remove(customPanel);
                refreshUI();
            });

            // Decline Request
            customPanel.button2.addActionListener(e -> {
                user.getManager().decline(request);
                user.getRequests().remove(request);
                panel1.remove(customPanel);
                refreshUI();
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

    private void refreshUI() {
        panel1.revalidate(); // Recalculate layout
        panel1.repaint();   // Redraw components
    }
}

