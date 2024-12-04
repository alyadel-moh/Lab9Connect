package coding.testtt.src;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

 class GuiMain{

    public static void main(String[] args){

        JFrame frame = new JFrame("Circle Button Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel circleLabel = new JLabel("Exciting circle button:");

        CircleButton circleButton = new CircleButton("Click me!");
        circleButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println("Clicked!");
            }
        });

        JLabel normalLabel = new JLabel("Boring square button:");
        JButton normalButton = new JButton("Okay");

        JPanel panel = new JPanel();
        panel.add(circleLabel);
        panel.add(circleButton);
        panel.add(normalLabel);
        panel.add(normalButton);
        frame.add(panel);

        frame.setSize(300, 150);
        frame.setVisible(true);
    }
}