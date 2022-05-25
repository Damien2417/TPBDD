package views;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LoginView {

    Container container;
    BufferedImage myPicture;

    {
        try {
            myPicture = ImageIO.read(new File("./src/views/resources/logo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    JLabel titleLabel = new JLabel(new ImageIcon(myPicture.getScaledInstance(320, 150, Image.SCALE_FAST)));

    public JLabel userLabel = new JLabel("Login");

    public JLabel passwordLabel = new JLabel("Mot de passe");
    public JTextField userTextField = new JTextField();
    public JPasswordField passwordField = new JPasswordField();
    public JButton loginButton = new JButton("Login");
    public JButton resetButton = new JButton("Reset");
    public JCheckBox showPassword = new JCheckBox("Voir mot de passe");
    public LoginView(Container container) {
        this.container = container;
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        //addActionEvent();
    }

    public void setLayoutManager() {
        container.setLayout(null);
        container.setBackground(new Color(0,151,228));
    }

    public void setLocationAndSize() {
        titleLabel.setBounds(450, 50, 320, 150);
        userLabel.setBounds(475, 250, 100, 30);
        userLabel.setForeground(Color.white);
        passwordLabel.setBounds(475, 320, 100, 30);
        passwordLabel.setForeground(Color.white);
        userTextField.setBounds(575, 250, 150, 30);
        passwordField.setBounds(575, 320, 150, 30);
        showPassword.setBounds(575, 350, 150, 30);
        showPassword.setForeground(Color.white);
        showPassword.setOpaque(false);
        loginButton.setBounds(475, 400, 100, 30);
        resetButton.setBounds(650, 400, 100, 30);
    }


    public void addComponentsToContainer() {
        container.add(titleLabel);
        container.add(userLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(showPassword);
        container.add(loginButton);
        container.add(resetButton);
    }

    public void addLoginListener(ActionListener al){
        loginButton.addActionListener(al);
    }

    public void addResetPasswordListener(ActionListener al){
        resetButton.addActionListener(al);
    }

    public void addShowPasswordListener(ActionListener al){
        showPassword.addActionListener(al);
    }

}