package coding;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public class Custompanel3 extends JPanel{
    private Group group;
    private int buttonCount;
    protected JButton button1;
    protected JButton button2;
    public Custompanel3(Group group, String text) {
        this(group, text, null); // Call the other constructor with null for text2
    }

    public Custompanel3(Group group, String text, String text2) {
        super();
        this.group = group;
        this.buttonCount = (text2 == null) ? 1 : 2;
        this.button1 = createbutton(text);

        if (text2 != null) {
            this.button2 = createbutton(text2);
        }

        setupLayout();
    }

    private void setupLayout() {
        setLayout(new GridLayout(1,2));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1,0));

        if (buttonCount >= 1) {
            buttonPanel.add(button1);
        }
        if (buttonCount == 2) {
            buttonPanel.add(button2);
        }

        add(buttonPanel);

        setMaximumSize(new Dimension(300,20));
        //setMinimumSize(new Dimension(100,30));
        setPreferredSize(new Dimension(100,30));
    }

    private JButton createbutton(String text)
    {
        JButton button = new JButton();
        button.setBackground(Color.black);
        button.setForeground(Color.white);
        button.setFocusable(false);
        button.setText(text);
        return button;
    }
}
