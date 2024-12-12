package coding;

import coding.Observer.NotificationObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Window1 extends JFrame  {
    private JButton signInButton;
    private JButton signUpButton;
    private JPanel panel;
    private UserService userService;
    Window1(UserService userService)
        {
            setTitle("Main menu");
            this.userService = userService;
            setContentPane(panel);
            setBounds(100,100,400,350);
            setResizable(false);
            setLocationRelativeTo(null);
            signUpButton.setFocusable(false);
            signInButton.setFocusable(false);
            signUpButton.setFont(new Font("Arial", Font.BOLD,15));
            signInButton.setFont(new Font("Arial", Font.BOLD,15));
            signUpButton.addActionListener(e -> {
                setVisible(false);
                new SignUp(userService);
            });
            signInButton.addActionListener(e -> {
                setVisible(false);
                new SignIn(userService);
            });
            setVisible(true);
        }
    public static void main(String[] args) {
            Database database = Database.getInstance();
            int count = 1;


        ////////////////////////////////////////////
        System.out.println("Users: ");
        for (User user : database.getUsers()) {
            System.out.println(user.getUserName() + " Observers: " + user.getNotifier().getObservers().size());

            for (NotificationObserver observer : user.getNotifier().getObservers()) {
                System.out.print(count++);
                System.out.println(observer);
            }
        }

        ////////////////////////////////////


            UserService userService = new UserService(database);
            new Window1(userService);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    userService.logout();
                    System.out.println("Data saved successfully.");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Failed to save data on shutdown.");
                }
            }));
        }
}
