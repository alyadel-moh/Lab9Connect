package coding;

import coding.ENUMS.Mapper;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.swing.*;
import java.awt.*;

public class Notifications_Panel extends CustomPanel{
    private Enum code;

    public Notifications_Panel(Object user, Enum code, String text) {
        super(user, text);
        this.code = code;
    }

    public Notifications_Panel(Object user, Enum code, String text, String text2) {
        super(user, text, text2);
        this.code = code;
    }


    @Override
    public void setupLayout(){
        setLayout(new GridLayout(1,3));

        ////// Profile Part //////////////
        JPanel profilePanel = new JPanel(new GridLayout(1,2));
        JPanel buttonPanel = new JPanel();

        JButton profileView = createImageButton();
        JLabel label2;

        if (user instanceof User) {
            label2 = new JLabel(((User) user).getUserName());
        }else{
            label2 = new JLabel(((Group) user).getName());
        }

        profilePanel.add(profileView);
        profilePanel.add(label2);
        //profilePanel.add(new JLabel());

        add(profilePanel);

        //////// Message Part //////////////
        //// add Message
        //add(new JLabel(Mapper.getMessage(code)));
        //add(new JLabel(String.valueOf(code)));
        add(new JLabel());



        ////// Buttons Part //////////////
        buttonPanel.setLayout(new GridLayout(1,0));

        if (buttonCount >= 1) {
            buttonPanel.add(button1);
        }
        if (buttonCount == 2) {
            buttonPanel.add(button2);
        }

        add(buttonPanel);

        setMaximumSize(new Dimension(900,40));
        //setMinimumSize(new Dimension(100,30));
        setPreferredSize(new Dimension(100,40));
    }

    public Enum getCode() {
        return code;
    }

    public void setCode(Enum code) {
        this.code = code;
    }
}
