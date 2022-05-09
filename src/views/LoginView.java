package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView {

    Container container;
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
    }

    public void setLocationAndSize() {
        userLabel.setBounds(475, 250, 100, 30);
        passwordLabel.setBounds(475, 320, 100, 30);
        userTextField.setBounds(575, 250, 150, 30);
        passwordField.setBounds(575, 320, 150, 30);
        showPassword.setBounds(575, 350, 150, 30);
        loginButton.setBounds(450, 400, 100, 30);
        resetButton.setBounds(625, 400, 100, 30);
    }


    public void addComponentsToContainer() {
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